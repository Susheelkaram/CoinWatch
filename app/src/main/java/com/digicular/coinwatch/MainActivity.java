package com.digicular.coinwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.digicular.coinwatch.activities.PickCryptoActivity;
import com.digicular.coinwatch.adapters.CoinsListAdapter;
import com.digicular.coinwatch.alerts.PriceAlertWorker;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.database.AlertsViewModel;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.fragments.AlertsListFragment;
import com.digicular.coinwatch.fragments.HomeFragment;
import com.digicular.coinwatch.fragments.PortfolioDetailFragment;
import com.digicular.coinwatch.fragments.SettingsFragment;
import com.digicular.coinwatch.model.CoinInfo;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.InitialSetup;
import com.digicular.coinwatch.utils.PreferenceManager;
import com.digicular.coinwatch.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */

public class MainActivity extends AppCompatActivity {
    Toolbar appBar;
    @BindView(R.id.recyclerView_CoinsInfoList)
    RecyclerView rv_CoinsInfoList;
    @BindView(R.id.swipeRefreshLayout_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar_Loading)
    ProgressBar pbLoading;
    @BindView(R.id.ll_EmptyView)
    LinearLayout layout_EmptyView;
    @BindView(R.id.button_EmptyViewRetry)
    Button btnEmptyViewRetry;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    @BindView(R.id.bottomNavigationView_AppNavigation)
    BottomNavigationView bnvNavigation;


    private CoinsListAdapter coinsListAdapter;
    private ArrayList<CoinInfo> coinInfoList;
    private String coinId;
    private Context mContext = MainActivity.this;

    private PreferenceManager preferenceManager;

    protected String CURRENCY;
    protected String COIN_IDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        appBar = (Toolbar) findViewById(R.id.AppBar);

        setSupportActionBar(appBar);

        InitialSetup firstLaunchSetup = new InitialSetup(this);
        firstLaunchSetup.start();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, PickCryptoActivity.class);
                startActivity(i);
            }
        });

        // Getting Instances of all fragments for Bottom Navigation
        HomeFragment homeFragment = new HomeFragment(this);
        AlertsListFragment alertsFragment = new AlertsListFragment(this);
        PortfolioDetailFragment portfolioDetailFragment = new PortfolioDetailFragment(this);
        SettingsFragment settingsFragment = new SettingsFragment(this);

        loadFragment(homeFragment);

        // Bottom Navigation Listener
        BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment activeFragment;
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_Home:
                                activeFragment = homeFragment;
                                loadFragment(activeFragment);
                                return true;
                            case R.id.navigation_Portfolio:
                                activeFragment = portfolioDetailFragment;
                                loadFragment(activeFragment);
                                return true;
                            case R.id.navigation_Alerts:
                                activeFragment = alertsFragment;
                                loadFragment(activeFragment);
                                return true;
                            case R.id.navigation_Settings:
                                activeFragment = settingsFragment;
                                loadFragment(activeFragment);
                                return true;
                        }
                        return false;
                    }
                };

        bnvNavigation.setOnNavigationItemSelectedListener(bottomNavListener);



        /**********************************************
         * Price Alerts: Creating first OneTimeRequest
         * to track prices in Background
         **********************************************/
        WorkManager workManager = WorkManager.getInstance();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(PriceAlertWorker.class)
                .setInitialDelay(Contract.WORKREQUEST_DELAY_MINS, TimeUnit.MINUTES)
                .addTag(Contract.WORKREQUEST_TAG)
                .build();

        // Duplicate WorkRequest check
        if (!Utils.isWorkerRunning(Contract.WORKREQUEST_TAG)) {
            workManager.enqueue(oneTimeWorkRequest);
        }


        /******* FRAGMENTED_START
         *
         *
         preferenceManager = PreferenceManager.getInstance(getApplicationContext(), Contract.PREF_SETTINGS);
         CURRENCY = preferenceManager.getString(Contract.PREFO_CURRENCY);
         COIN_IDS = TextUtils.join(",", preferenceManager.getArrayList(Contract.PREFO_COINSTOWATCH));

         rv_CoinsInfoList.setHasFixedSize(true);
         rv_CoinsInfoList.setLayoutManager(new LinearLayoutManager(this));

         getCoinsInfo(CURRENCY, COIN_IDS);

         btnEmptyViewRetry.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        layout_EmptyView.setVisibility(View.GONE);
        refreshCoinsInfo();
        }
        });

         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override public void onRefresh() {
        refreshCoinsInfo();
        }
        });
         *
         *
         * ***** FRAGMENTED_END ****/

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_ContentFrag, fragment)
                .commit();
    }


    /******* FRAGMENTED_START *******


     private void getCoinsInfo(String currency, String coinIds){

     Retrofit retrofit = Utils.getRetrofitWithCache(this, Contract.BASE_URL);

     CoinApi coinApi = retrofit.create(CoinApi.class);

     Call<ArrayList<CoinInfo>> coinsInfoCall = coinApi.getListCoinsInfo(currency, coinIds);

     coinsInfoCall.enqueue(new Callback<ArrayList<CoinInfo>>() {
    @Override public void onResponse(Call<ArrayList<CoinInfo>> call, Response<ArrayList<CoinInfo>> response) {
    if(!response.isSuccessful()){
    Toast.makeText(getApplicationContext(), "Request cannot be processed", Toast.LENGTH_SHORT).show();
    layout_EmptyView.setVisibility(View.VISIBLE);
    }
    else {
    coinInfoList= response.body();
    layout_EmptyView.setVisibility(View.GONE);
    coinsListAdapter = new CoinsListAdapter(MainActivity.this, coinInfoList);
    rv_CoinsInfoList.setAdapter(coinsListAdapter);
    }
    swipeRefreshLayout.setRefreshing(false);
    pbLoading.setVisibility(View.GONE);
    }

    @Override public void onFailure(Call<ArrayList<CoinInfo>> call, Throwable t) {
    Toast.makeText(getApplicationContext(), "Request Failed" , Toast.LENGTH_SHORT).show();
    Log.d("FAIL", t.getMessage() + "-----" + t.getCause());
    swipeRefreshLayout.setRefreshing(false);
    layout_EmptyView.setVisibility(View.VISIBLE);
    pbLoading.setVisibility(View.GONE);
    }
    });
     }

     private void refreshCoinsInfo(){
     pbLoading.setVisibility(View.VISIBLE);
     getCoinsInfo(CURRENCY, COIN_IDS);
     }
     *
     * ********FRAGMENTED_END**********/
}

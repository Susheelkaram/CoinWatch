package com.digicular.coinwatch.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.digicular.coinwatch.MainActivity;
import com.digicular.coinwatch.R;
import com.digicular.coinwatch.adapters.CoinsListAdapter;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.model.CoinInfo;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.PreferenceManager;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;

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

public class HomeFragment extends Fragment {
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


    private CoinsListAdapter coinsListAdapter;
    private ArrayList<CoinInfo> coinInfoList;
    private String coinId;
    private Context mContext;

    private PreferenceManager preferenceManager;

    protected String CURRENCY;
    protected String COIN_IDS;

    public HomeFragment(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ButterKnife.bind(this, view);

        preferenceManager = PreferenceManager.getInstance(mContext.getApplicationContext(), Contract.PREF_SETTINGS);
        CURRENCY = preferenceManager.getString(Contract.PREFO_CURRENCY);
        COIN_IDS = TextUtils.join(",", preferenceManager.getArrayList(Contract.PREFO_COINSTOWATCH));

        rv_CoinsInfoList.setHasFixedSize(true);
        rv_CoinsInfoList.setLayoutManager(new LinearLayoutManager(mContext));

        getCoinsInfo(CURRENCY, COIN_IDS);

        btnEmptyViewRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_EmptyView.setVisibility(View.GONE);
                refreshCoinsInfo();
            }
        });


        // Swipe-down to Refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCoinsInfo();
            }
        });


    }


    // Fetches CoinsInfo fron REST Api and Binds data to RecyclerView adapter
    private void getCoinsInfo(String currency, String coinIds){

        Retrofit retrofit = Utils.getRetrofitWithCache(mContext, Contract.BASE_URL);

        CoinApi coinApi = retrofit.create(CoinApi.class);

        Call<ArrayList<CoinInfo>> coinsInfoCall = coinApi.getListCoinsInfo(currency, coinIds);

        coinsInfoCall.enqueue(new Callback<ArrayList<CoinInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<CoinInfo>> call, Response<ArrayList<CoinInfo>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(mContext.getApplicationContext(), "Request cannot be processed", Toast.LENGTH_SHORT).show();
                    layout_EmptyView.setVisibility(View.VISIBLE);
                }
                else {
                    coinInfoList= response.body();
                    layout_EmptyView.setVisibility(View.GONE);
                    coinsListAdapter = new CoinsListAdapter(mContext, coinInfoList);
                    rv_CoinsInfoList.setAdapter(coinsListAdapter);
                }
                swipeRefreshLayout.setRefreshing(false);
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<CoinInfo>> call, Throwable t) {
                Toast.makeText(mContext.getApplicationContext(), "Request Failed" , Toast.LENGTH_SHORT).show();
                Log.d("FAIL", t.getMessage() + "-----" + t.getCause());
                swipeRefreshLayout.setRefreshing(false);
                layout_EmptyView.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            }
        });
    }


    // Re-fetches JSON and refreshes data
    private void refreshCoinsInfo(){
        pbLoading.setVisibility(View.VISIBLE);
        getCoinsInfo(CURRENCY, COIN_IDS);
    }
}

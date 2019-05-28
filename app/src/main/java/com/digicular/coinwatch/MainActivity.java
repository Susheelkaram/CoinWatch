package com.digicular.coinwatch;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.digicular.coinwatch.adapters.CoinsListAdapter;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.model.CoinInfo;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar appBar;
    RecyclerView rv_CoinsInfoList;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar pbLoading;

    CoinsListAdapter coinsListAdapter;
    ArrayList<CoinInfo> coinInfoList;
    String coinId;

    protected final String CURRENCY = "usd";
    protected final String COIN_IDS = "bitcoin,ethereum,ripple,eos,bitcoin-cash,litecoin,binancecoin,cardano,stellar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBar = (Toolbar) findViewById(R.id.AppBar);
        rv_CoinsInfoList = (RecyclerView) findViewById(R.id.recyclerView_CoinsInfoList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_refresh);
        pbLoading = (ProgressBar) findViewById(R.id.progressBar_Loading);

        setSupportActionBar(appBar);

        rv_CoinsInfoList.setHasFixedSize(true);
        rv_CoinsInfoList.setLayoutManager(new LinearLayoutManager(this));

        getCoinsInfo(CURRENCY, COIN_IDS);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCoinsInfo(CURRENCY, COIN_IDS);
            }
        });
    }

    public void getCoinsInfo(String currency, String coinIds){

//        int cacheSize = 10 * 1024 * 1024;
//
//        Cache cache = new Cache(getCacheDir(), cacheSize);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Contract.BASE_URL)
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        Retrofit retrofit = Utils.getRetrofitWithCache(this, Contract.BASE_URL);

        CoinApi coinApi = retrofit.create(CoinApi.class);

        Call<ArrayList<CoinInfo>> coinsInfoCall = coinApi.getListCoinsInfo(currency, coinIds);

        coinsInfoCall.enqueue(new Callback<ArrayList<CoinInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<CoinInfo>> call, Response<ArrayList<CoinInfo>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Request cannot be processed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("SUCCESS", response.raw().request().url().toString());
                    coinInfoList= response.body();
                    Toast.makeText(getApplicationContext(), "Success " + coinInfoList.get(0).getCurrentPrice(), Toast.LENGTH_SHORT).show();
                    coinsListAdapter = new CoinsListAdapter(MainActivity.this, coinInfoList);
                    rv_CoinsInfoList.setAdapter(coinsListAdapter);
                }
                swipeRefreshLayout.setRefreshing(false);
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<CoinInfo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Request Failed" , Toast.LENGTH_SHORT).show();
                Log.d("FAIL", t.getMessage() + "-----" + t.getCause());
                swipeRefreshLayout.setRefreshing(false);
                pbLoading.setVisibility(View.GONE);
            }
        });
    }


}
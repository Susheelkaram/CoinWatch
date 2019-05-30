package com.digicular.coinwatch.activities;

import android.content.Intent;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;
import android.widget.Toast;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.adapters.CoinInfoPagerAdapter;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.model.CoinInfoDetailed;
import com.digicular.coinwatch.model.Links;
import com.digicular.coinwatch.model.MarketData;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CoinDetailActivity extends AppCompatActivity {
    Toolbar appBar;
    TabLayout tabLayoutCoinDetail;
    ViewPager viewPagerCoinDetail;

    TextView tvCoinName;
    TextView tvCoinRank;
    TextView tvCoinMarketCap;
    TextView tvCoinCurrentPrice;
    TextView tvChangePercent24H;
    TextView tvLow24H;
    TextView tvHigh24H;

    String coinId = "ethereum";
    String currency = "usd";
    String currencySymbol = "$";
    List<Bundle> fragBundles = new ArrayList<Bundle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);

        appBar = (Toolbar) findViewById(R.id.AppBar);
        tabLayoutCoinDetail = (TabLayout) findViewById(R.id.tabLayout_CoinDetail);
        viewPagerCoinDetail = (ViewPager) findViewById(R.id.viewPager_CoinDetail);

        tvCoinName = (TextView) findViewById(R.id.textView_CoinName);
        tvCoinRank = (TextView) findViewById(R.id.textView_CoinRank);
        tvCoinMarketCap = (TextView) findViewById(R.id.textView_CoinMarketCap);
        tvCoinCurrentPrice = (TextView) findViewById(R.id.textView_CoinCurrentPrice);
        tvChangePercent24H = (TextView) findViewById(R.id.textView_Coin24HChange);
        tvLow24H = (TextView) findViewById(R.id.textView_24HrLow);
        tvHigh24H = (TextView) findViewById(R.id.textView_24HrHigh);

        setSupportActionBar(appBar);

        Intent intent = getIntent();
        coinId = intent.getStringExtra("coinId");

        getDetailsCoin(coinId);
    }



    public void getDetailsCoin(final String coinId){
        Retrofit retrofit = Utils.getRetrofitWithCache(this, Contract.BASE_URL);

        CoinApi coinApi = retrofit.create(CoinApi.class);

        Call<CoinInfoDetailed> coinInfoDetailedCall = coinApi.getDetailedCoinInfo(coinId);

        coinInfoDetailedCall.enqueue(new Callback<CoinInfoDetailed>() {
            @Override
            public void onResponse(Call<CoinInfoDetailed> call, Response<CoinInfoDetailed> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Request cannot be processed", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Request Success", Toast.LENGTH_SHORT).show();

                    CoinInfoDetailed coinDetails = response.body();
                    bindData(coinDetails);
                }
            }

            @Override
            public void onFailure(Call<CoinInfoDetailed> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bindData(CoinInfoDetailed coinDetails){
        MarketData marketData = coinDetails.getMarketData();

        String coinName = coinDetails.getName();
        String coinSymbol = coinDetails.getSymbol();
        String currentPrice = Double.toString(marketData.getCurrentPrice(currency));
        String changePercent24H = Double.toString(marketData.getChangePercentage24H());
        String rank = Integer.toString(coinDetails.getMarketCapRank());
        String marketCap = (marketData.getMarketCap(currency)).toString();
        String high24H = currencySymbol + Double.toString(marketData.getHigh24H(currency));
        String low24H = currencySymbol + Double.toString(marketData.getLow24H(currency));

        String coinDescription = coinDetails.getDescription();
        Links links = coinDetails.getLinks();

        tvCoinName.setText(coinName + " (" + coinSymbol.toUpperCase() + ")");
        tvCoinRank.setText(rank);
        tvCoinCurrentPrice.setText(currencySymbol + currentPrice);
        tvCoinMarketCap.setText(marketCap);
        tvChangePercent24H.setText(changePercent24H);
        tvLow24H.setText(low24H);
        tvHigh24H.setText(high24H);

        // Fragments
        Bundle bundleChart = new Bundle();
        bundleChart.putString(Contract.CHART_COINID, coinId);

        Bundle bundleMoreInfo = new Bundle();
        bundleMoreInfo.putString(Contract.MOREINFO_DESCRIPTION, coinDescription);
        bundleMoreInfo.putParcelable(Contract.MOREINFO_LINKS, links);
        fragBundles.add(bundleChart);
        fragBundles.add(bundleMoreInfo);

        CoinInfoPagerAdapter coinInfoPagerAdapter = new CoinInfoPagerAdapter(this, fragBundles, getSupportFragmentManager());

        viewPagerCoinDetail.setAdapter(coinInfoPagerAdapter);
        tabLayoutCoinDetail.setupWithViewPager(viewPagerCoinDetail);
    }

}

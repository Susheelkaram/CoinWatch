package com.digicular.coinwatch.controller;

import com.digicular.coinwatch.model.CoinInfo;
import com.digicular.coinwatch.model.CoinInfoDetailed;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public interface CoinApi {

    // Bulk Coin prices
    @GET("coins/markets")
    Call<ArrayList<CoinInfo>> getListCoinsInfo(@Query("vs_currency") String currency,
                                @Query("ids") String coinIds);

    // Individual Coin detailed info
    @GET("coins/{id}?localization=false&tickers=false&community_data=false&developer_data=false")
    Call<CoinInfoDetailed> getDetailedCoinInfo(@Path("id") String coinId);
}

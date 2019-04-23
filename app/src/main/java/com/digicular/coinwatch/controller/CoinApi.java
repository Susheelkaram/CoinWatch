package com.digicular.coinwatch.controller;

import com.digicular.coinwatch.model.CoinInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public interface CoinApi {
    @GET("coins/markets")
    Call<ArrayList<CoinInfo>> getListCoinsInfo(@Query("vs_currency") String currency,
                                @Query("ids") String coinIds);
}

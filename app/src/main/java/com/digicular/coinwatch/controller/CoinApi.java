package com.digicular.coinwatch.controller;

import com.digicular.coinwatch.model.CoinInfo;
import com.digicular.coinwatch.model.CoinInfoDetailed;
import com.digicular.coinwatch.model.CryptoCurrency;
import com.digicular.coinwatch.model.MarketChart;
import com.digicular.coinwatch.model.SimplePrice;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    // Simple Price
    @GET("simple/price")
    Call<HashMap<String, SimplePrice>> getSimplePrices(@Query("ids") String coinIds,
                                                       @Query("vs_currencies") String currencies);

    // Individual Coin detailed info
    @GET("coins/{id}?localization=false&tickers=false&community_data=false&developer_data=false")
    Call<CoinInfoDetailed> getDetailedCoinInfo(@Path("id") String coinId);

    // Coin Market Chart
    @GET("coins/{id}/market_chart")
    Call<MarketChart> getMarketChart(@Path("id") String coinId,
                                     @Query("days") int days,
                                     @Query("vs_currency") String currency);

    // All available Coins list
    @GET("coins/list")
    Call<ArrayList<CryptoCurrency>> getAvailableCurrencies();
}

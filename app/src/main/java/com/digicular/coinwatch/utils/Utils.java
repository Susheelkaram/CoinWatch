package com.digicular.coinwatch.utils;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class Utils {

    public static Retrofit getRetrofitWithCache(Context context, String baseUrl){
        long cacheSize = 10 * 1024 * 1024; // 10MB

        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static Double formatNumber(Double num){
        return 0.0;
    }
}

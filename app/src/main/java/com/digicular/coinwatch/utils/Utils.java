package com.digicular.coinwatch.utils;

import android.content.Context;
import android.widget.Spinner;

import com.digicular.coinwatch.model.Condition;

import java.util.Random;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class Utils {

    // Fetches Retrofit instance with a Cache Support of upto 20MB
    public static Retrofit getRetrofitWithCache(Context context, String baseUrl){
        long cacheSize = 20 * 1024 * 1024;

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

    public static String capitalizeWord(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int getItemIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++){
            Condition condition = (Condition) spinner.getItemAtPosition(i);
            if (condition.getCondition().equals(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}

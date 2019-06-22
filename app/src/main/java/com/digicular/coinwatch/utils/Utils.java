package com.digicular.coinwatch.utils;

import android.content.Context;
import android.widget.Spinner;

import com.digicular.coinwatch.model.Condition;

import java.io.IOException;
import java.util.Random;

import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class Utils {

    // Fetches Retrofit instance with a Cache Support of upto 20MB
    public static Retrofit getRetrofitWithCache(Context context, String baseUrl){
        long cacheSize = Contract.cacheSize;

        OkHttpClient client = getCustomOkHttpClient(context, cacheSize, 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static Retrofit getRetrofitWithCustomCache(Context context, String baseUrl, int maxAgeMins){
        long cacheSize = Contract.cacheSize;

        OkHttpClient client = getCustomOkHttpClient(context, cacheSize, maxAgeMins);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static OkHttpClient getCustomOkHttpClient(Context context, long cacheSize, int maxAgeMins){
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if(maxAgeMins != 0){
            String newCacheHeader =  "max-age=" + Integer.toString(maxAgeMins * 60) + ", public, must-revalidate";
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    Response newResponse;

                    newResponse = originalResponse.newBuilder()
                            .header("Cache-Control",newCacheHeader)
                            .build();

                    return newResponse;
                }
            };
            okHttpBuilder.addNetworkInterceptor(interceptor);
        }

        OkHttpClient client = okHttpBuilder
                .cache(cache)
                .build();


        return client;
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

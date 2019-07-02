package com.digicular.coinwatch.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Spinner;

import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.digicular.coinwatch.model.Condition;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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

    // Fetches Retrofit instance with a Cache Support of upto 20MB
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

    // Returns custom OkHttp Client instance with custom Cache size, custom Cache expiration time
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

    // Capitalizes first letter in a word
    public static String capitalizeWord(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    // Returns random number in a given range
    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    // Gets index of an Spinner item by matching String
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

    // Simple helper to Quickly Explicitly launch an activity without any Extras/Args
    public static void launchActivity(Context context, Class<?> activityClass){
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    // Checks for existing Work Request
    public static boolean isWorkerRunning(String tag){
        List<WorkInfo> status = null;
        try {
            status = WorkManager.getInstance().getWorkInfosByTag(tag).get();
            boolean running = false;
            for (WorkInfo workStatus : status) {
                if (workStatus.getState() == WorkInfo.State.RUNNING
                        || workStatus.getState() == WorkInfo.State.ENQUEUED) {
                    return true;
                }
            }
            return false;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Large number formatter
    public static String formatNumber(double number){
        String formattedNumber = "";
        DecimalFormat df = new DecimalFormat("###,###.##");


        long thousand = 1000;
        long tenThousand = 10000;
        long twentyThousand = 20000;
        long million = 1000000;
        long billion = 1000000000;
        if(number < thousand){
            formattedNumber =  df.format(number);
        }
        else if(number > thousand && number < twentyThousand){
            df = new DecimalFormat("###,###");
            formattedNumber =  df.format(number);
        }
        else if(number > twentyThousand && number < million){
            number = number/thousand;
            formattedNumber =  df.format(number) + "K";
        }

        else if(number > million && number < billion){
            number = number/million;
            formattedNumber =  df.format(number) + "M";
        }

        else if(number > billion){
            number = number/billion;
            formattedNumber =  df.format(number) + "B";
        }
        return formattedNumber;
    }

    public static String formatNumber(BigInteger number){
        long million = 1000000;
        long billion = 1000000000;
        String res = "";
        if ((number.compareTo(BigInteger.valueOf(million)) == 1)
                && (number.compareTo(BigInteger.valueOf(billion)) == -1)){

            res = number.divide(BigInteger.valueOf(million)).toString() + "M";
        }
        else if (number.compareTo(BigInteger.valueOf(billion)) == 1){
            res = number.divide(BigInteger.valueOf(billion)).toString() + "B";
        }
        return res;
    }

}

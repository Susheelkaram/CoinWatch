package com.digicular.coinwatch.alerts;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.model.CoinInfo;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class PriceAlertWorker extends Worker {

    public PriceAlertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Retrofit retrofit = Utils.getRetrofitWithCache(getApplicationContext(), Contract.BASE_URL);

        CoinApi coinApi = retrofit.create(CoinApi.class);

        Call<ArrayList<CoinInfo>> coinsInfoCall = coinApi.getListCoinsInfo(Contract.CURRENCY, "bitcoin");

        coinsInfoCall.enqueue(new Callback<ArrayList<CoinInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<CoinInfo>> call, Response<ArrayList<CoinInfo>> response) {
                if(!response.isSuccessful()){
                    Log.d("Coin Info call", "Cannot process request");
                }
                else {
                    ArrayList<CoinInfo> coinInfo = response.body();
                    String coinName = coinInfo.get(0).getName();
                    Double price = coinInfo.get(0).getCurrentPrice();

                    SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
                    String time = sdf.format(System.currentTimeMillis());

                    showNotification(Utils.capitalizeWord(coinName) + "Price alert", "Price of " + coinName + " is " + price + " at " + time);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CoinInfo>> call, Throwable t) {
                Log.d("Coin Info call", "Request Failed");
            }
        });



        // New Work Request
        WorkManager workManager = WorkManager.getInstance();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(PriceAlertWorker.class)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .build();
        workManager.enqueue(oneTimeWorkRequest);

        return Result.success();
    }

    public void showNotification(String title, String content){
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "Coin Watch Channel";
        String channelName = "Price Alert";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder  builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(Utils.getRandomNumberInRange(1,1000), builder.build());
    }
}





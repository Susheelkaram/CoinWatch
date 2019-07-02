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
import com.digicular.coinwatch.utils.PriceAlertsManager;
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
    private Context mContext;

    public PriceAlertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("WORK_MANAGER_WORKING", "Checking for Price changes...");

        PriceAlertsManager priceAlertsManager = new PriceAlertsManager(mContext);

        // Checking Alerts
        priceAlertsManager.check();


        // Creating another Work Request
        WorkManager workManager = WorkManager.getInstance();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(PriceAlertWorker.class)
                .setInitialDelay(Contract.WORKREQUEST_DELAY_MINS, TimeUnit.MINUTES)
                .build();
        workManager.enqueue(oneTimeWorkRequest);

        return Result.success();
    }

}





package com.digicular.coinwatch.alerts;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.digicular.coinwatch.utils.Contract;

import java.util.concurrent.TimeUnit;

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
                .addTag(Contract.WORKREQUEST_TAG)
                .build();

        workManager.enqueue(oneTimeWorkRequest);

        return Result.success();
    }

}





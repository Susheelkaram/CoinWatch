package com.digicular.coinwatch.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.database.PriceAlertDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class PriceAlertRepository {
    private PriceAlertDatabase alertsDb;
    private Context mContext;

    public PriceAlertRepository(Context context){
        mContext = context;

        if(alertsDb == null){
            alertsDb = Room.databaseBuilder(mContext.getApplicationContext(),
                    PriceAlertDatabase.class, DBContract.ALERTS_TABLE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
    }


    public void insertAlert(PriceAlert priceAlert) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                alertsDb.priceAlertDoa().insertAll(priceAlert);
                return null;
            }
        }.execute();
    }

    public List<PriceAlert> getAllAlerts(){
        return alertsDb.priceAlertDoa().getAll();
    }
    public List<PriceAlert> getEnabledAlerts(){
        return alertsDb.priceAlertDoa().getEnabledAlerts();
    }

    public void deleteAlert(PriceAlert priceAlert){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                alertsDb.priceAlertDoa().deleteAll(priceAlert);
                return null;
            }
        }.execute();
    }

    public void updateAlert(PriceAlert priceAlert){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                alertsDb.priceAlertDoa().updateAll(priceAlert);
                return null;
            }
        }.execute();
    }
}

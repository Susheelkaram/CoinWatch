package com.digicular.coinwatch.database;

import android.content.Context;
import android.os.AsyncTask;

import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.database.PriceAlertDatabase;

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
        alertsDb = PriceAlertDatabase.getInstance(mContext);
    }


    public void insertAlert(PriceAlert priceAlert) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                alertsDb.priceAlertDoa().insertAll(priceAlert);
            }
        }.execute();
    }

    public List<PriceAlert> getAllAlerts(){
        return alertsDb.priceAlertDoa().getAll();
    }

    public void deleteAlert(PriceAlert priceAlert){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                alertsDb.priceAlertDoa().deleteAll(priceAlert);
            }
        }.execute();
    }

    public void updateAlert(PriceAlert priceAlert){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                alertsDb.priceAlertDoa().updateAll(priceAlert);
            }
        }.execute();
    }
}

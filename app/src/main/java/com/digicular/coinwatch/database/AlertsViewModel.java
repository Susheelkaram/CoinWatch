package com.digicular.coinwatch.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class AlertsViewModel extends AndroidViewModel {
    private PriceAlertRepository mAlertRepository;
    private List<PriceAlert> mAlertList;

    public AlertsViewModel(@NonNull Application application) {
        super(application);

        mAlertRepository = new PriceAlertRepository(application);

        mAlertList = mAlertRepository.getAllAlerts();
    }

    public List<PriceAlert> getAllAlerts(){
        return mAlertList;
    }
}

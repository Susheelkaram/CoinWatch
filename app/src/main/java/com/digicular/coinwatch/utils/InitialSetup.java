package com.digicular.coinwatch.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 * <p>
 * Stuff to be done when use opens app for the first time.
 */
public class InitialSetup {
    private Context mContext;
    private PreferenceManager preferenceManager;
    private ArrayList<String> defaultCoinsToWatch;

    public InitialSetup(Context context) {
        mContext = context;
        preferenceManager = PreferenceManager.getInstance(mContext, Contract.PREF_SETTINGS);
        defaultCoinsToWatch = new ArrayList<String>();
    }

    public void start() {
        Collections.addAll(defaultCoinsToWatch, Contract.DEFAULT_COINWATCHLIST);

        boolean isFirstLaunch = preferenceManager.getBoolean(Contract.PREFO_ISFIRSTLAUNCH);

        if (isFirstLaunch){
            preferenceManager.putArrayList(Contract.PREFO_COINSTOWATCH, defaultCoinsToWatch);
            preferenceManager.putString(Contract.PREFO_CURRENCY, "usd");
            preferenceManager.putBoolean(Contract.PREFO_ISFIRSTLAUNCH, false);
        }
    }
}

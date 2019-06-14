package com.digicular.coinwatch.utils;

import android.content.Context;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 * <p>
 * Stuff to be done when use opens app for the first time.
 */
public class InitialSetup {
    private Context mContext;
    private PreferenceManager preferenceManager;

    private InitialSetup() {

    }

    public void start(Context context) {
        mContext = context;
        preferenceManager = PreferenceManager.getInstance(mContext, Contract.PREF_SETTINGS);

        boolean isFirstLaunch = preferenceManager.getBoolean(Contract.PREFO_ISFIRSTLAUNCH);

        if (isFirstLaunch){

        }
    }
}

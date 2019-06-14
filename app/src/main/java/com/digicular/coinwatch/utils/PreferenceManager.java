package com.digicular.coinwatch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 * <p>
 * <p>
 * Centralised Singleton to Manage Shared Preferences at one place
 */
public class PreferenceManager {
    private Context mContext;
    private SharedPreferences mPreferences;
    private static PreferenceManager preferenceManager;
    private Editor editor;

    private PreferenceManager(Context context, String preferencesFileName) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
        editor = mPreferences.edit();
    }

    public static PreferenceManager getInstance(Context context, String preferencesFileName) {
        if (preferenceManager == null) {
            preferenceManager = new PreferenceManager(context, preferencesFileName);
        }
        return preferenceManager;
    }

    public void putString(String key, String value) {
        if (editor == null) {
            editor = mPreferences.edit();
        }
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        if (editor == null) {
            editor = mPreferences.edit();
        }
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        if (editor == null) {
            editor = mPreferences.edit();
        }

        editor.putInt(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, true);
    }

    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    public int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public void putArrayList(String key, ArrayList<String> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String jsonList = gson.toJson(list);

        if (editor == null) {
            editor = mPreferences.edit();
        }
        editor.putString(key, jsonList);
        editor.apply();
    }

    public ArrayList<String> getArrayList(String key) {
        ArrayList<String> list = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String jsonList = mPreferences.getString(key, null);

        if (jsonList != null) {
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            list = gson.fromJson(jsonList, listType);
        }
        return list;
    }

}

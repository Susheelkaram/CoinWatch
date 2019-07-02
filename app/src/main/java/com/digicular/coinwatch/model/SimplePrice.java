package com.digicular.coinwatch.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class SimplePrice extends HashMap<String, Double>{

    public Double getSimplePrice(){
        return values().iterator().next();
    }
    public Double getSimplePrice(String currency){
        return get(currency);
    }

    @NonNull
    @Override
    public String toString() {
        return "The current price is: " + getSimplePrice() + " units.";
    }
}

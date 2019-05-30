package com.digicular.coinwatch.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class MarketChart {
    @SerializedName("prices")
    private ArrayList<ArrayList<BigDecimal>> prices;
    @SerializedName("market_caps")
    private ArrayList<ArrayList<BigDecimal>> marketCaps;
    @SerializedName("total_volumes")
    private ArrayList<ArrayList<BigDecimal>> totalVolumes;

    public ArrayList<ArrayList<BigDecimal>> getPrices() {
        return prices;
    }
}



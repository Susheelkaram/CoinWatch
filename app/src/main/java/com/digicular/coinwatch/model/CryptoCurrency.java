package com.digicular.coinwatch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class CryptoCurrency {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("symbol")
    private String symbol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

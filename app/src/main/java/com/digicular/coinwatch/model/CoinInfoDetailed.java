package com.digicular.coinwatch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class CoinInfoDetailed {
    @SerializedName("id")
    private String id;

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("name")
    private String name;

    @SerializedName("market_cap_rank")
    private int marketCapRank;

    @SerializedName("market_data")
    private MarketData marketData;

    // Getter-Setter Methods
    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getMarketCapRank() {
        return marketCapRank;
    }

    public MarketData getMarketData(){
        return marketData;
    }
}

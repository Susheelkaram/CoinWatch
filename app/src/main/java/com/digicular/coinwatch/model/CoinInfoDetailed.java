package com.digicular.coinwatch.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

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

    @SerializedName("description")
    private Map<String, String> description;

    @SerializedName("market_cap_rank")
    private int marketCapRank;

    @SerializedName("market_data")
    private MarketData marketData;

    @SerializedName("links")
    private Links links;

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

    public String getDescription(){
        return description.get("en");
    }

    public Links getLinks() {
        return links;
    }
}


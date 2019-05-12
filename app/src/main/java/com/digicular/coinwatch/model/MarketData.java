package com.digicular.coinwatch.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.Map;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class MarketData {
    @SerializedName("current_price")
    Map<String, Double> currentPrice;

    @SerializedName("market_cap")
    Map<String, BigInteger> marketCap;

    @SerializedName("high_24h")
    Map<String, Double> high24H;

    @SerializedName("low_24h")
    Map<String, Double> low24H;

    @SerializedName("price_change_percentage_24h")
    Double changePercentage24H;

    public Double getCurrentPrice(String currency) {
        return currentPrice.get(currency);
    }

    public BigInteger getMarketCap(String currency) {
        return marketCap.get(currency);
    }

    public Double getHigh24H(String currency) {
        return high24H.get(currency);
    }

    public Double getLow24H(String currency) {
        return low24H.get(currency);
    }

    public Double getChangePercentage24H() {
        return changePercentage24H;
    }
}

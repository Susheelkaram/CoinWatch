package com.digicular.coinwatch.model;

import androidx.annotation.NonNull;

import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class PriceChange {
    private double targetPrice;
    private double newPrice;
    private double priceDifference;
    private String coinId;
    private String coinSymbol;

    public PriceChange(double targetPrice, double newPrice, String coinId, String coinSymbol) {
        this.targetPrice = targetPrice;
        this.newPrice = newPrice;
        this.coinId = coinId;
        this.coinSymbol = coinSymbol;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public double getPriceDifference() {
        return priceDifference;
    }

    public String getCoinId() {
        return coinId;
    }

    @NonNull
    @Override
    public String toString() {
        String changeType = priceDifference < 0 ? "dropped" : "increased";
        String changePreposition = priceDifference < 0 ? "below" : "above";
        String SPACE = " ";

        // Ex: "Bitcoin (BTC) price dropped below 11000.01 (current price is 11,120)"
        String toStringText = Utils.capitalizeWord(coinId) + " (" + coinSymbol.toUpperCase() + ")"
                + " price " + changeType + SPACE + changePreposition + SPACE
                + Contract.DOLLAR_SYMBOL + targetPrice
                + " (current price is " + Contract.DOLLAR_SYMBOL + newPrice + ")";

        return toStringText;
    }

}

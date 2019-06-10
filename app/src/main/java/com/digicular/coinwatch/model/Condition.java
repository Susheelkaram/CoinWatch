package com.digicular.coinwatch.model;

import androidx.annotation.NonNull;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 *
 * Defines Condition for a PriceAlert
 * @Param displayCondition -> To Display in UI (Spinner)
 * @Param storeableCondition -> To Store in database
 */
public class Condition {
    private String displayCondition;
    private String storeableCondition;

    public Condition(String displayCondition, String storeableCondition) {
        this.displayCondition = displayCondition;
        this.storeableCondition = storeableCondition;
    }

    @NonNull
    @Override
    public String toString() {
        return displayCondition;
    }

    public String getDisplayCondition() {
        return displayCondition;
    }

    public String getCondition() {
        return storeableCondition;
    }
}

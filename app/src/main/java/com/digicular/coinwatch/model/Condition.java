package com.digicular.coinwatch.model;

import androidx.annotation.NonNull;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
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

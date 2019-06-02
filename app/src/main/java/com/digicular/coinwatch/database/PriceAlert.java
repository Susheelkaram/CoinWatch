package com.digicular.coinwatch.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Entity(tableName = DBContract.ALERTS_TABLE_NAME)
public class PriceAlert {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = DBContract.ALERTS_COL_COINID)
    private String coinId;

    @ColumnInfo(name = DBContract.ALERTS_COL_CONDITION)
    private String condition;

    @ColumnInfo(name = DBContract.ALERTS_COL_VALUE)
    private double targetValue;

    @ColumnInfo(name = DBContract.ALERTS_COL_REPEAT)
    private boolean isRepeatEnabled;

    @ColumnInfo(name = DBContract.ALERTS_COL_ISENABLED)
    private boolean isEnabled;




    // Getter-setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    public boolean isRepeatEnabled() {
        return isRepeatEnabled;
    }

    public void setRepeatEnabled(boolean repeatEnabled) {
        isRepeatEnabled = repeatEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}

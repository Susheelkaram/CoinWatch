package com.digicular.coinwatch.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Entity(tableName = DBContract.ALERTS_TABLE_NAME)
public class PriceAlert implements Parcelable {
    public PriceAlert(){}

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = DBContract.ALERTS_COL_COINID)
    private String coinId;

    @ColumnInfo(name = DBContract.ALERTS_COL_COINSYMBOL)
    private String coinSymbol;

    @ColumnInfo(name = DBContract.ALERTS_COL_CONDITION)
    private String condition;

    @ColumnInfo(name = DBContract.ALERTS_COL_VALUE)
    private double targetValue;

    @ColumnInfo(name = DBContract.ALERTS_COL_REPEAT)
    private boolean isRepeatEnabled;

    @ColumnInfo(name = DBContract.ALERTS_COL_ISENABLED)
    private boolean isEnabled;

    @ColumnInfo(name = DBContract.ALERTS_COL_TIMEMILLIS)
    //Epoch time in Milli seconds
    private Long timeStamp;


    /************************
    * Parceling data
    *************************/
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public PriceAlert createFromParcel(Parcel source) {
            return new PriceAlert(source);
        }

        @Override
        public PriceAlert[] newArray(int size) {
            return new PriceAlert[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(coinId);
        out.writeString(coinSymbol);
        out.writeString(condition);
        out.writeDouble(targetValue);
        out.writeInt(isRepeatEnabled ? 1 : 0);
        out.writeInt(isEnabled ? 1 : 0);
        out.writeLong(timeStamp);
    }

    @SuppressWarnings("unchecked")
    private PriceAlert(Parcel parcel){
        id = parcel.readInt();
        coinId = parcel.readString();
        coinSymbol = parcel.readString();
        condition = parcel.readString();
        targetValue = parcel.readDouble();
        isRepeatEnabled = (parcel.readInt() != 0);
        isEnabled = (parcel.readInt() != 0);
        timeStamp = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /************************
    * Getter-setter
    *************************/
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

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    @NonNull
    @Override
    public String toString() {
        return coinId + id;
    }
}

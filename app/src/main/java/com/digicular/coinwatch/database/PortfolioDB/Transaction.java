package com.digicular.coinwatch.database.PortfolioDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.digicular.coinwatch.database.DBContract;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */

@Entity(tableName = DBContract.TRANSACTION_TABLE_NAME)
public class Transaction {
    public Transaction() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = DBContract.TRANSACTION_COL_COINID)
    private String coinId;

    @ColumnInfo(name = DBContract.TRANSACTION_COL_SYMBOL)
    private String coinSymbol;

    @ColumnInfo(name = DBContract.TRANSACTION_COL_TYPE)
    private int transactionType; // 0 for SELL & 1 for BUY

    @ColumnInfo(name = DBContract.TRANSACTION_COL_QUANTITY)
    private double quantity;

    @ColumnInfo(name = DBContract.TRANSACTION_COL_RATE)
    private double exchangeRate;

    @ColumnInfo(name = DBContract.TRANSACTION_COL_VALUE)
    private double value;

    @ColumnInfo(name = DBContract.TRANSACTION_COL_NOTE)
    private String note;

    @ColumnInfo(name = DBContract.TRANSACTION_COL_TIMESTAMP)
    private long timestamp;


    /*****************************
     *     Getters & Setters
     *****************************/

    public int getId() {
        return id;
    }

    public String getCoinId() {
        return coinId;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public double getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

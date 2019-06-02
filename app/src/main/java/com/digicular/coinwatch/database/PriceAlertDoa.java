package com.digicular.coinwatch.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Dao
public interface PriceAlertDoa {

    String tableName = DBContract.ALERTS_TABLE_NAME;

    @Query("SELECT * FROM " + tableName)
    List<PriceAlert> getAll();

    @Query("SELECT * FROM " + tableName + " WHERE " + DBContract.ALERTS_COL_COINID + " LIKE :coinId")
    List<PriceAlert> getByCoinId(String coinId);

    @Query("SELECT COUNT(*) FROM " + DBContract.ALERTS_TABLE_NAME)
    int countUsers();

    @Insert
    void insertAll(PriceAlert... priceAlerts);

    @Delete
    void delete(PriceAlert priceAlert);
}

package com.digicular.coinwatch.database.AlertsDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digicular.coinwatch.database.DBContract;

import java.util.List;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Dao
public interface PriceAlertDoa {

    public String tableName = DBContract.ALERTS_TABLE_NAME;

    @Query("SELECT * FROM " + tableName)
    public List<PriceAlert> getAll();

    @Query("SELECT * FROM " + tableName + " WHERE " + DBContract.ALERTS_COL_ISENABLED + " = 1")
    public List<PriceAlert> getEnabledAlerts();

    @Query("SELECT * FROM " + tableName + " WHERE " + DBContract.ALERTS_COL_COINID + " LIKE :coinId")
    public List<PriceAlert> getByCoinId(String coinId);

    @Query("SELECT COUNT(*) FROM " + tableName)
    public int countUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(PriceAlert... priceAlerts);

    @Delete
    public void deleteAll(PriceAlert... priceAlert);

    @Update
    public void updateAll(PriceAlert... priceAlert);

}

package com.digicular.coinwatch.database.PortfolioDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digicular.coinwatch.database.DBContract;

import java.util.List;

import retrofit2.http.DELETE;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Dao
public interface TransactionDoa {
    String tableName = DBContract.TRANSACTION_TABLE_NAME;

    @Query("SELECT * FROM " + tableName)
    public List<Transaction> getAllTransactions();

    @Query("SELECT COUNT(*) FROM " + tableName)
    public int getCount();

    @Query("SELECT * FROM " + tableName + " WHERE " + DBContract.TRANSACTION_COL_SYMBOL + " = :symbol")
    public List<Transaction> getTransactionsBySymbol(String symbol);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(Transaction... transactions);

    @Delete
    public void deleteAll(Transaction... transactions);

    @Update
    public void updateAll(Transaction... transactions);
}

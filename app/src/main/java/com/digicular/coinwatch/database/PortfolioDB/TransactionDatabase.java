package com.digicular.coinwatch.database.PortfolioDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Database(entities = {Transaction.class}, version = 1)
public abstract class TransactionDatabase extends RoomDatabase {

    public abstract TransactionDoa transactionDoa();
}

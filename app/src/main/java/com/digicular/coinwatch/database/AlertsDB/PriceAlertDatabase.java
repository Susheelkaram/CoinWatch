package com.digicular.coinwatch.database.AlertsDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */

@Database(entities = {PriceAlert.class}, version = 1)
public abstract class PriceAlertDatabase extends RoomDatabase {
//    private static PriceAlertDatabase priceAlertDatabase;
//    private static Context mContext;

    public abstract PriceAlertDoa priceAlertDoa();

//    public static PriceAlertDatabase getInstance(){
//        if(priceAlertDatabase == null) {
//            priceAlertDatabase = Room.databaseBuilder(mContext.getApplicationContext(), PriceAlertDatabase.class, DBContract.ALERTS_TABLE_NAME)
//                    .build();
//        }
//        return priceAlertDatabase;
//    }
}

package com.digicular.coinwatch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */

@Database(entities = {PriceAlert.class}, version = 1)
public abstract class PriceAlertDatabase extends RoomDatabase {

    public abstract PriceAlertDoa priceAlertDoa();

    public static PriceAlertDatabase getInstance(Context context){
        PriceAlertDatabase priceAlertDatabase = Room.databaseBuilder(context.getApplicationContext(), PriceAlertDatabase.class, DBContract.ALERTS_TABLE_NAME)
                .allowMainThreadQueries()
                .build();
        return priceAlertDatabase;
    }
}

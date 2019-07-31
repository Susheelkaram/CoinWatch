package com.digicular.coinwatch.database.PortfolioDB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.digicular.coinwatch.database.DBContract;

import java.util.List;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class TransactionRepository {
    private TransactionDatabase transactionDatabase;
    private Context mContext;

    public TransactionRepository(Context context){
        mContext = context;

        if(transactionDatabase != null){
            transactionDatabase = Room.databaseBuilder(mContext.getApplicationContext(),
                     TransactionDatabase.class, DBContract.TRANSACTION_TABLE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
    }

    public List<Transaction> getAllTransactions(){
        return transactionDatabase.transactionDoa().getAllTransactions();
    }

    public void addTransaction(Transaction transaction){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                transactionDatabase.transactionDoa().insertAll(transaction);
                return null;
            }
        }.execute();
    }
    public void deleteTransaction(Transaction transaction){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                transactionDatabase.transactionDoa().deleteAll(transaction);
                return null;
            }
        }.execute();
    }

    public void updateTransaction(Transaction transaction){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                transactionDatabase.transactionDoa().updateAll(transaction);
                return null;
            }
        }.execute();
    }

}

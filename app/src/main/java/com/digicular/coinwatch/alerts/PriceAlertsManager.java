package com.digicular.coinwatch.alerts;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.database.AlertsDB.PriceAlert;
import com.digicular.coinwatch.database.AlertsDB.PriceAlertRepository;
import com.digicular.coinwatch.model.PriceChange;
import com.digicular.coinwatch.model.SimplePrice;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class PriceAlertsManager {
    private Context mContext;
    private List<PriceAlert> alertList;
    private PriceAlertRepository alertRepository;

    public PriceAlertsManager(Context context) {
        mContext = context;
        alertList = new ArrayList<PriceAlert>();
        alertRepository = new PriceAlertRepository(mContext);
    }

    public void check() {
        alertList = alertRepository.getEnabledAlerts();
        if (alertList.size() > 0) {
            String coinIdsToCheck = getCoinListAsString(alertList);
            getPrices(coinIdsToCheck);
        }
    }

    // STEP 1: Fetches current prices by making an Api call
    private void getPrices(String coinsToCheck) {
        Retrofit retrofit = Utils.getRetrofitWithCache(mContext, Contract.BASE_URL);

        CoinApi coinApi = retrofit.create(CoinApi.class);

        Call<HashMap<String, SimplePrice>> simplePriceCall = coinApi.getSimplePrices(coinsToCheck, Contract.CURRENCY);

        simplePriceCall.enqueue(new Callback<HashMap<String, SimplePrice>>() {
            @Override
            public void onResponse(Call<HashMap<String, SimplePrice>> call, Response<HashMap<String, SimplePrice>> response) {
                if (response.isSuccessful()) {
                    checkRates(response.body());
                } else {
                    Log.d("SIMPLE_PRICE", "Request cannot be processed.");
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, SimplePrice>> call, Throwable t) {
                Log.d("SIMPLE_PRICE", "Request failed");
            }
        });
    }

    // STEP 2 : Uses fetched prices and checks against condition in Alerts set by user
    private void checkRates(HashMap<String, SimplePrice> rates) {
        if (rates == null) {
            return;
        }

        ArrayList<PriceChange> priceChangeList = new ArrayList<PriceChange>();

        String condition;
        double targetValue;
        String coinId;
        String coinSymbol;
        double currentPrice;

        // Iterating through all the alerts set by user
        for (PriceAlert alert : alertList) {
            condition = alert.getCondition();
            targetValue = alert.getTargetValue();
            coinId = alert.getCoinId();

            coinSymbol = alert.getCoinSymbol();

            currentPrice = rates.get(coinId).getSimplePrice();

            // Less than condition (DROP condition)
            if (condition.equals(Contract.ALERT_VALLOWER)) {
                if (currentPrice <= targetValue) {
                    priceChangeList.add(new PriceChange(targetValue, currentPrice, coinId, coinSymbol));
                    turnAlertOff(alert, false);
                }
            }
            // Greater than condition (RISE condition)
            else if (condition.equals(Contract.ALERT_VALHIGHER)) {
                if (currentPrice >= targetValue) {
                    priceChangeList.add(new PriceChange(targetValue, currentPrice, coinId, coinSymbol));
                    turnAlertOff(alert, false);
                }
            }
        }

        // Send notification(s) to user for Price changes
        notifyUser(priceChangeList);
    }

    // STEP 3: Pushes Price changes (if any) and Sends a notification for each Price change
    private void notifyUser(List<PriceChange> priceChanges) {
        if (priceChanges.size() > 0) {
            for (PriceChange priceChange : priceChanges) {
                String notificationTitle = Utils.capitalizeWord(priceChange.getCoinId()) + Contract.NOTIF_TITLESTATIC;
                showNotification(mContext, notificationTitle, priceChange.toString());
            }
        }
    }

    // Helper: Scans all alerts set by User and returns a String of Coin Ids to check
    private String getCoinListAsString(List<PriceAlert> alertList) {
        Set<String> coinsListToCheck = new HashSet<String>();
        String coinsToCheck = "";

        for (PriceAlert alert : alertList) {
            coinsListToCheck.add(alert.getCoinId());
        }
        coinsToCheck = TextUtils.join(",", coinsListToCheck);
        return coinsToCheck;
    }

    // Helper: Creates and pushes notification
    private void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "Coin Watch Channel";
        String channelName = "Price Alert";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(Utils.getRandomNumberInRange(1, 1000), builder.build());
    }

    public void turnAlertOff(PriceAlert priceAlert, boolean isOn){
        priceAlert.setEnabled(isOn);
        alertRepository.updateAlert(priceAlert);
    }
}

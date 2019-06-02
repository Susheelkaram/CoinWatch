package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.adapters.AlertsListAdapter;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.database.PriceAlertDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PriceAlertListActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView_Alerts)
    RecyclerView rvAlerts;
    @BindView(R.id.button_AddAlert)
    Button btnAddAlert;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_alert_list);

        ButterKnife.bind(this);

        rvAlerts.setHasFixedSize(true);
        rvAlerts.setLayoutManager(new LinearLayoutManager(this));



        // Adding Sample Alert to DB
        PriceAlertDatabase pdb = PriceAlertDatabase.getInstance(mContext);
        PriceAlert priceAlert = new PriceAlert();
        priceAlert.setCoinId("bitcoin");
        priceAlert.setCondition("greater");
        priceAlert.setRepeatEnabled(false);
        priceAlert.setTargetValue(5742);
        priceAlert.setEnabled(true);
        pdb.priceAlertDoa().insertAll(priceAlert);

        List<PriceAlert> alerts = pdb.priceAlertDoa().getAll();

        AlertsListAdapter adapter = new AlertsListAdapter(mContext, alerts);
        rvAlerts.setAdapter(adapter);
    }
}

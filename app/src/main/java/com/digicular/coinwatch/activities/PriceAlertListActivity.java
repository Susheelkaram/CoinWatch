package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.adapters.AlertsListAdapter;
import com.digicular.coinwatch.database.PriceAlertRepository;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.fragments.AlertsListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PriceAlertListActivity extends AppCompatActivity {
//    @BindView(R.id.recyclerView_Alerts)
//    RecyclerView rvAlerts;
//    @BindView(R.id.button_AddAlert)
//    Button btnAddAlert;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_alert_list);
//
//        ButterKnife.bind(this);
//
//        rvAlerts.setHasFixedSize(true);
//        rvAlerts.setLayoutManager(new LinearLayoutManager(this));
//
//
//        btnAddAlert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.launchActivity(mContext, AddAlertActivity.class);
//            }
//        });
//
//
//
//
//        PriceAlertRepository priceAlertRepository = new PriceAlertRepository(this);
//
//        List<PriceAlert> alerts = priceAlertRepository.getAllAlerts();
//
//        AlertsListAdapter adapter = new AlertsListAdapter(mContext, alerts);
//        rvAlerts.setAdapter(adapter);

        AlertsListFragment alertsListFragment = new AlertsListFragment(this);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.container_AlertFrag, alertsListFragment)
                .commit();
    }


}

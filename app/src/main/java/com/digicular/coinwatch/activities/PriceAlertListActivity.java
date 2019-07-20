package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.adapters.AlertsListAdapter;
import com.digicular.coinwatch.database.AlertsViewModel;
import com.digicular.coinwatch.database.PriceAlertRepository;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.fragments.AlertsListFragment;
import com.digicular.coinwatch.utils.Utils;

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
//        AlertsViewModel alertsViewModel = ViewModelProviders.of(this).get(AlertsViewModel.class);
//
//
//
//        AlertsListAdapter adapter = new AlertsListAdapter(mContext);
//        rvAlerts.setAdapter(adapter);
//
//        alertsViewModel.getAllAlerts().observe(this, new Observer<List<PriceAlert>>() {
//            @Override
//            public void onChanged(List<PriceAlert> priceAlerts) {
//                Log.d("LIVE DATA", "Table updated: " + priceAlerts.size());
//                adapter.updateData(priceAlerts);
//            }
//        });
//






        AlertsListFragment alertsListFragment = new AlertsListFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.container_AlertFrag, alertsListFragment)
                .commit();
    }


}

package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.fragments.AlertsListFragment;

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

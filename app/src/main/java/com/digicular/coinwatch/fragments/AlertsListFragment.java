package com.digicular.coinwatch.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.activities.AddAlertActivity;
import com.digicular.coinwatch.adapters.AlertsListAdapter;
import com.digicular.coinwatch.database.AlertsViewModel;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.database.PriceAlertRepository;
import com.digicular.coinwatch.model.Condition;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertsListFragment extends Fragment {
    @BindView(R.id.recyclerView_Alerts)
    RecyclerView rvAlerts;
    @BindView(R.id.button_AddAlert)
    Button btnAddAlert;

    private Context mContext;
    private AlertsViewModel alertsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alerts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        rvAlerts.setHasFixedSize(true);
        rvAlerts.setLayoutManager(new LinearLayoutManager(mContext));

        alertsViewModel = ViewModelProviders.of(this).get(AlertsViewModel.class);

        btnAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.launchActivity(mContext, AddAlertActivity.class);
            }
        });

        AlertsListAdapter adapter = new AlertsListAdapter(mContext);
        rvAlerts.setAdapter(adapter);
        adapter.updateData(alertsViewModel.getAllAlerts());


//        alertsViewModel.getAllAlerts().observe(this, new Observer<List<PriceAlert>>() {
//            @Override
//            public void onChanged(List<PriceAlert> priceAlerts) {
//                Log.d("LIVE DATA", "Table updated: " + priceAlerts.size());
//                adapter.updateData(priceAlerts);
//            }
//        });
    }


}

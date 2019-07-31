package com.digicular.coinwatch.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.activities.AddAlertActivity;
import com.digicular.coinwatch.activities.PickCryptoActivity;
import com.digicular.coinwatch.adapters.AlertsListAdapter;
import com.digicular.coinwatch.database.AlertsViewModel;
import com.digicular.coinwatch.database.AlertsDB.PriceAlert;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    @BindView(R.id.emptyView_Alerts)
    LinearLayout layoutEmptyView;

    private Context mContext;
    private String fragmentTitle = "Alerts";
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

        // Setting title
        TextView titleView  = (TextView) getActivity().findViewById(R.id.text_ToolbarTitle);
        if(titleView != null){
            titleView.setText(fragmentTitle);
        }

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
        List<PriceAlert> alertsList = alertsViewModel.getAllAlerts();
        adapter.updateData(alertsList);

        // Showing Empty view when there are No alerts set
        if(alertsList.isEmpty()){
            layoutEmptyView.setVisibility(View.VISIBLE);
        }

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, PickCryptoActivity.class);
                i.putExtra(Contract.PICKER_MODE, Contract.PICKER_MODE_ALERT);
                startActivity(i);
            }
        });
//        alertsViewModel.getAllAlerts().observe(this, new Observer<List<PriceAlert>>() {
//            @Override
//            public void onChanged(List<PriceAlert> priceAlerts) {
//                Log.d("LIVE DATA", "Table updated: " + priceAlerts.size());
//                adapter.updateData(priceAlerts);
//            }
//        });
    }


}

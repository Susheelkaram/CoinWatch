package com.digicular.coinwatch.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.activities.AddAlertActivity;
import com.digicular.coinwatch.adapters.AlertsListAdapter;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.database.PriceAlertRepository;
import com.digicular.coinwatch.model.Condition;
import com.digicular.coinwatch.utils.Utils;

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

    public AlertsListFragment(Context context) {
        mContext = context;
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


        btnAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.launchActivity(mContext, AddAlertActivity.class);
            }
        });




        PriceAlertRepository priceAlertRepository = new PriceAlertRepository(mContext);

        List<PriceAlert> alerts = priceAlertRepository.getAllAlerts();

        AlertsListAdapter adapter = new AlertsListAdapter(mContext, alerts);
        rvAlerts.setAdapter(adapter);
    }
}

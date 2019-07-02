package com.digicular.coinwatch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.activities.AddAlertActivity;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.database.PriceAlertRepository;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.dom.DOMLocator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class AlertsListAdapter extends RecyclerView.Adapter<AlertsListAdapter.AlertViewHolder> {

    private Context mContext;
    private List<PriceAlert> mPriceAlertList = Collections.emptyList();
    private PriceAlertRepository mPriceAlertRepository;
    private View itemView;

    public AlertsListAdapter(Context context) {
        mContext = context;
//        mPriceAlertList = priceAlertList;
        mPriceAlertRepository = new PriceAlertRepository(mContext);
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.alert_item, parent, false);
        itemView = v;
        return new AlertViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        PriceAlert mAlert = mPriceAlertList.get(position);

        // Tap on an Alert Launches Alert Editor
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, AddAlertActivity.class);
                i.putExtra(Contract.ALERT_EXTRA, mAlert);
                i.putExtra(Contract.EXTRAS_TAG, Contract.EXTRA_TAG_EDITALERT);
                mContext.startActivity(i);
            }
        });

        String coinId = Utils.capitalizeWord(mAlert.getCoinId());
        String coinSymbol = "(" + mAlert.getCoinSymbol() + ")";
        String condition = mAlert.getCondition();
        String targetValue = Double.toString(mAlert.getTargetValue());
        boolean isRepeatEnabled = mAlert.isRepeatEnabled();
        boolean isEnabled = mAlert.isEnabled();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");
        String dateCreated = "Created on " + sdf.format(mAlert.getTimeStamp());

        holder.textAlertCoinId.setText(coinId);
        holder.textAlertCoinIdSymbol.setText(coinSymbol);
        holder.textAlertCondition.setText(condition);
        holder.textTargetValue.setText(targetValue);
        holder.textAlertRepeat.setText(isRepeatEnabled ? "Every time" : "Only once");
        holder.switchIsAlertEnabled.setChecked(isEnabled);
        holder.textDateCreated.setText(dateCreated);

        // Turning Alert ON/OFF
        holder.switchIsAlertEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                turnAlertOnOff(mAlert, isChecked);
            }
        });
    }


    public class AlertViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_AlertsCoinName) TextView textAlertCoinId;
        @BindView(R.id.text_AlertsCoinSymbol) TextView textAlertCoinIdSymbol;
        @BindView(R.id.text_AlertsCondition) TextView textAlertCondition;
        @BindView(R.id.text_AlertsRepeat) TextView textAlertRepeat;
        @BindView(R.id.text_DateCreated) TextView textDateCreated;
        @BindView(R.id.text_TargetValue) TextView textTargetValue;
        @BindView(R.id.switch_IsAlertEnabled) SwitchCompat switchIsAlertEnabled;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mPriceAlertList.size();
    }

    public void updateData(List<PriceAlert> alerts){
        mPriceAlertList = alerts;
        notifyDataSetChanged();
    }

    public void turnAlertOnOff(PriceAlert priceAlert, boolean isOn){
        priceAlert.setEnabled(isOn);
        mPriceAlertRepository.updateAlert(priceAlert);
    }


}

package com.digicular.coinwatch.adapters;

import android.content.Context;
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
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.database.PriceAlertRepository;

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
    private List<PriceAlert> mPriceAlertList;
    private PriceAlertRepository mPriceAlertRepository;

    public AlertsListAdapter(Context context, List<PriceAlert> priceAlertList) {
        mContext = context;
        mPriceAlertList = priceAlertList;
        mPriceAlertRepository = new PriceAlertRepository(mContext);
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.alert_item, parent, false);
        return new AlertViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        PriceAlert mAlert = mPriceAlertList.get(position);

        String coinId = mAlert.getCoinId();
        String condition = mAlert.getCondition();
        Double targetValue = mAlert.getTargetValue();
        boolean isRepeatEnabled = mAlert.isRepeatEnabled();
        boolean isEnabled = mAlert.isEnabled();

        holder.textAlertCoinId.setText(coinId);
        holder.textAlertCondition.setText(condition + " " + targetValue);
        holder.textAlertRepeat.setText(isRepeatEnabled ? "Every time" : "Only once");
        holder.switchIsAlertEnabled.setChecked(isEnabled);

        // Turning Alert
        holder.switchIsAlertEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                turnAlertOnOff(mAlert, isChecked);
            }
        });
    }


    public class AlertViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_AlertsCoinName) TextView textAlertCoinId;
        @BindView(R.id.text_AlertsCondition) TextView textAlertCondition;
        @BindView(R.id.text_AlertsRepeat) TextView textAlertRepeat;
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


    public void turnAlertOnOff(PriceAlert priceAlert, boolean isOn){
        priceAlert.setEnabled(isOn);
        mPriceAlertRepository.updateAlert(priceAlert);
    }


}

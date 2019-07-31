package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.database.AlertsDB.PriceAlertRepository;
import com.digicular.coinwatch.database.AlertsDB.PriceAlert;
import com.digicular.coinwatch.model.Condition;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAlertActivity extends AppCompatActivity {
    @BindView(R.id.text_AlertCoinId)
    TextView text_AlertCoinId;
    @BindView(R.id.text_AlertCoinSymbol)
    TextView text_AlertCoinSymbol;
    @BindView(R.id.spinner_AlertCondition)
    Spinner spinner_AlertCondition;
    @BindView(R.id.input_AlertTargetValue)
    EditText input_AlertTargetValue;
    @BindView(R.id.switch_IsAlertEnabled)
    SwitchCompat switch_IsAlertEnabled;
    @BindView(R.id.switch_AlertRepeat)
    SwitchCompat switch_AlertRepeat;

    @BindView(R.id.text_AlertCreatedTime)
    TextView text_AlertCreatedTime;
    @BindView(R.id.button_ConfirmAddAlert)
    Button button_ConfirmAddAlert;
    @BindView(R.id.button_DeleteAlert)
    Button button_DeleteAlert;
    @BindView(R.id.button_SaveAlert)
    Button button_SaveAlert;
    @BindView(R.id.ll_ButtonsEdit)
    LinearLayout layout_ButtonsEdit;

    private PriceAlertRepository priceAlertRepository;
    private ArrayList<Condition> conditionList = new ArrayList<Condition>();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);

        priceAlertRepository = new PriceAlertRepository(this);

        // View Binding
        ButterKnife.bind(this);


        // Creating and setting ArrayAdapter for Condition Spinner
        conditionList.add(new Condition(Contract.ALERT_LOWER, Contract.ALERT_VALLOWER));
        conditionList.add(new Condition(Contract.ALERT_HIGHER, Contract.ALERT_VALHIGHER));

        ArrayAdapter<Condition> arrayAdapter = new ArrayAdapter<Condition>(this, R.layout.spinner_item, conditionList);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_AlertCondition.setAdapter(arrayAdapter);

        // Adding New Alert
        button_ConfirmAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceAlert alert = getAlertDataFromViews();
                priceAlertRepository.insertAlert(alert);
                Toast.makeText(mContext, R.string.t_AlertAdded, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Getting extras from Pick coin activity

        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            String tag = extras.getString(Contract.EXTRAS_TAG);

            /**********************
             * NEW Alert mode
             **********************/

            if (tag.equals(Contract.EXTRA_TAG_NEWALERT)) {
                String coinId = extras.getString(Contract.PICKER_DATA_COINID);
                String coinSymbol = extras.getString(Contract.PICKER_DATA_COINSYMBOL);

                text_AlertCoinId.setText(coinId);
                text_AlertCoinSymbol.setText(coinSymbol);
                switch_IsAlertEnabled.setChecked(true);
            }


            /****************************
             * Alert EDITING Mode
             ****************************/

            else if (tag.equals(Contract.EXTRA_TAG_EDITALERT)) {
                PriceAlert alert = extras.getParcelable(Contract.ALERT_EXTRA);
                button_ConfirmAddAlert.setVisibility(View.GONE);
                layout_ButtonsEdit.setVisibility(View.VISIBLE);
                setDataToViews(alert);

                switch_IsAlertEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        turnAlertOnOff(alert, isChecked);
                    }
                });

                button_DeleteAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        priceAlertRepository.deleteAlert(alert);
                        Toast.makeText(mContext, R.string.t_AlertDeleted, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                button_SaveAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PriceAlert newAlert = getAlertDataFromViews();
                        newAlert.setId(alert.getId());
                        priceAlertRepository.insertAlert(newAlert);
                        Toast.makeText(mContext, R.string.t_AlertSaved, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }


    }

    // Fetches Data from Views and returns 'PriceAlert' object
    public PriceAlert getAlertDataFromViews() {
        String coinId = text_AlertCoinId.getText().toString().toLowerCase();
        String coinSymbol = text_AlertCoinSymbol.getText().toString().toLowerCase();
        Condition condition = (Condition) spinner_AlertCondition.getSelectedItem();
        String conditionValue = condition.getCondition();
        double targetValue = Double.parseDouble(input_AlertTargetValue.getText().toString());

        // TODO: Optimise PriceAlertsManager to Support Repeated alerts
        //  and change below isRepeatOn statement accordingly
        // boolean isRepeatOn = switch_AlertRepeat.isChecked();
        boolean isRepeatOn = false;

        boolean isEnabled = true;
        Long timeStamp = System.currentTimeMillis();

        PriceAlert alert = new PriceAlert();
        alert.setCoinId(coinId);
        alert.setCoinSymbol(coinSymbol);
        alert.setCondition(conditionValue);
        alert.setTargetValue(targetValue);
        alert.setRepeatEnabled(isRepeatOn);
        alert.setEnabled(isEnabled);
        alert.setTimeStamp(timeStamp);

        return alert;
    }

    // Alert EDITING Mode: Sets Data to Views
    public void setDataToViews(PriceAlert alert) {
        text_AlertCoinId.setText(alert.getCoinId());
        text_AlertCoinSymbol.setText(alert.getCoinSymbol());
        input_AlertTargetValue.setText(Double.toString(alert.getTargetValue()));
        switch_AlertRepeat.setChecked(alert.isRepeatEnabled());
        switch_IsAlertEnabled.setChecked(alert.isEnabled());

        SimpleDateFormat df = new SimpleDateFormat("MMM d, ''yy 'at' h:mm a");
        String timeStamp = "Created on " + df.format(alert.getTimeStamp());
        text_AlertCreatedTime.setText(timeStamp);

        String condition = alert.getCondition();
        int index = Utils.getItemIndex(spinner_AlertCondition, condition);
        spinner_AlertCondition.setSelection(index);
    }

    public void turnAlertOnOff(PriceAlert priceAlert, boolean isOn){
        priceAlert.setEnabled(isOn);
        priceAlertRepository.updateAlert(priceAlert);
    }
}

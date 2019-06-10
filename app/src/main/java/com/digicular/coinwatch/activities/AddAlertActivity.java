package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.database.PriceAlertRepository;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.model.Condition;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAlertActivity extends AppCompatActivity {
    @BindView(R.id.input_AlertCoinId)
    EditText input_AlertCoinId;
    @BindView(R.id.spinner_AlertCondition)
    Spinner spinner_AlertCondition;
    @BindView(R.id.input_AlertTargetValue)
    EditText input_AlertTargetValue;
    @BindView(R.id.switch_AlertRepeat)
    SwitchCompat switch_AlertRepeat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);

        priceAlertRepository = new PriceAlertRepository(this);

        // View Binding
        ButterKnife.bind(this);


        /**********************
         * NEW Alert mode
         **********************/

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
                finish();
            }
        });


        /****************************
         * Alert EDITING Mode
         ****************************/
        /
        Intent inIntent = getIntent();
        PriceAlert alert = inIntent.getParcelableExtra(Contract.ALERT_EXTRA);

        if (alert != null) {
            button_ConfirmAddAlert.setVisibility(View.GONE);
            layout_ButtonsEdit.setVisibility(View.VISIBLE);
            setDataToViews(alert);
        }

        button_DeleteAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceAlertRepository.deleteAlert(alert);
                finish();
            }
        });

        button_SaveAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceAlert newAlert = getAlertDataFromViews();
                newAlert.setId(alert.getId());
                priceAlertRepository.insertAlert(newAlert);
                finish();
            }
        });
    }

    // Fetches Data from Views and returns 'PriceAlert' object
    public PriceAlert getAlertDataFromViews() {
        String cryptoName = input_AlertCoinId.getText().toString();
        Condition condition = (Condition) spinner_AlertCondition.getSelectedItem();
        String conditionValue = condition.getCondition();
        double targetValue = Double.parseDouble(input_AlertTargetValue.getText().toString());
        boolean isRepeatOn = switch_AlertRepeat.isChecked();
        boolean isEnabled = true;
        Long timeStamp = System.currentTimeMillis();

        PriceAlert alert = new PriceAlert();
        alert.setCoinId(cryptoName);
        alert.setCondition(conditionValue);
        alert.setTargetValue(targetValue);
        alert.setRepeatEnabled(isRepeatOn);
        alert.setEnabled(isEnabled);
        alert.setTimeStamp(timeStamp);

        return alert;
    }

    // Alert EDITING Mode: Sets Data to Views
    public void setDataToViews(PriceAlert alert){
        input_AlertCoinId.setText(alert.getCoinId());
        input_AlertTargetValue.setText(Double.toString(alert.getTargetValue()));
        switch_AlertRepeat.setChecked(alert.isRepeatEnabled());
        String condition = alert.getCondition();
        spinner_AlertCondition.setSelection(Utils.getItemIndex(spinner_AlertCondition, condition));
    }
}

package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.database.PriceAlertRepository;
import com.digicular.coinwatch.database.PriceAlert;
import com.digicular.coinwatch.model.Condition;
import com.digicular.coinwatch.utils.Contract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    private PriceAlertRepository priceAlertRepository;
    private ArrayList<Condition> conditionList= new ArrayList<Condition>();

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


        // Creating Alert Entry in Database
        button_ConfirmAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceAlert alert = getAlertDataFromViews();
                priceAlertRepository.insertAlert(alert);
                finish();
            }
        });

    }


    public PriceAlert getAlertDataFromViews(){
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
}

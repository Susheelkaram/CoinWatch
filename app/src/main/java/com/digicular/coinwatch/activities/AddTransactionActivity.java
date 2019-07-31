package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.database.DBContract;
import com.digicular.coinwatch.database.PortfolioDB.Transaction;
import com.digicular.coinwatch.utils.Contract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * With this Activity, you can add a Buy/Sell Transaction
 * which is then saved to Transactions database
 */


public class AddTransactionActivity extends BaseCompatActivity {
    @BindView(R.id.text_CoinIdSymbol) TextView textCoinId;
    @BindView(R.id.radioGroup_BuySell) RadioGroup radioBuySell;

    @BindView(R.id.input_TransactionExchangeRate) EditText inputExchangeRate;
    @BindView(R.id.input_TransactionQuantity) EditText inputQuantity;
    @BindView(R.id.input_TransactionTotalValue) EditText inputTotalValue;
    @BindView(R.id.input_TransactionNote) EditText inputNote;


    @BindView(R.id.button_EditRate) Button btnEnableEditRate;
    @BindView(R.id.button_EditTotalValue) Button btnEnableEditTotalValue;

    @BindView(R.id.button_SaveTransaction) Button btnSaveTransaction;

    @OnClick({R.id.button_EditRate, R.id.button_EditTotalValue})
    public void enableInput(View v){
        EditText editText = inputExchangeRate;
        switch (v.getId()){
            case R.id.button_EditRate:
                editText = inputExchangeRate;
                break;
            case R.id.button_EditTotalValue:
                editText = inputTotalValue;
                break;
        }
        editText.setEnabled(true);
        editText.requestFocus();
        InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    private String coinId;
    private String coinSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        ButterKnife.bind(this);

        // TODO: Data Validation

        // TODO: Data Insertion

        // TODO: Set TextWatcher for ALl 3 input Fields with Null checks
        inputExchangeRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double rate;
                if(s.toString().equals("")) {
                    rate = 0;
                }
                else {
                    rate = Double.parseDouble(s.toString());
                }
                double quantity = Double.parseDouble(inputQuantity.getText().toString());
                double totalValue = rate * quantity;
                inputTotalValue.setText(String.valueOf(totalValue));
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    public Transaction getTransactionFromViews(){
        Transaction transaction = new Transaction();
        int transactionType = DBContract.TRANSACTION_TYPE_BUY;
        if(radioBuySell.getCheckedRadioButtonId() == R.id.radio_Sell) {
            transactionType = DBContract.TRANSACTION_TYPE_SELL;
        }
        double rate = Double.parseDouble(inputExchangeRate.getText().toString());
        double quantity =  Double.parseDouble(inputQuantity.getText().toString());
        double totalValue =  Double.parseDouble(inputTotalValue.getText().toString());
        String note = inputNote.getText().toString();
        long timeStamp = System.currentTimeMillis();

        transaction.setCoinId(coinId);
        transaction.setCoinSymbol(coinSymbol);
        transaction.setTransactionType(transactionType);
        transaction.setExchangeRate(rate);
        transaction.setQuantity(quantity);
        transaction.setValue(totalValue);
        transaction.setNote(note);
        transaction.setTimestamp(timeStamp);

        return transaction;
    }

    public void updateValues(){

    }
}

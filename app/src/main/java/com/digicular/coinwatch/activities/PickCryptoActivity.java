package com.digicular.coinwatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.adapters.CryptoSelectListAdapter;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.model.CryptoCurrency;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PickCryptoActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView_CryptoList)
    RecyclerView rvCryptoList;
    @BindView(R.id.button_RefreshCryptoList)
    Button btnRefreshCryptoList;

    private ArrayList<CryptoCurrency> availableCryptoList;
    private Context mContext = this;
    private CryptoSelectListAdapter cryptoSelectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_crypto);

        ButterKnife.bind(this);
        rvCryptoList.setLayoutManager(new LinearLayoutManager(this));
        getAvailableCryptoList();
    }

    private void getAvailableCryptoList() {
        Retrofit retrofit = Utils.getRetrofitWithCache(mContext, Contract.BASE_URL);

        CoinApi coinApi = retrofit.create(CoinApi.class);

        Call<ArrayList<CryptoCurrency>> call = coinApi.getAvailableCurrencies();

        call.enqueue(new Callback<ArrayList<CryptoCurrency>>() {
            @Override
            public void onResponse(Call<ArrayList<CryptoCurrency>> call, Response<ArrayList<CryptoCurrency>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Request cannot be processed", Toast.LENGTH_SHORT).show();
                }
                else {
                    availableCryptoList = response.body();
                    Log.d("JSON response", response.body().toString());
                    cryptoSelectListAdapter = new CryptoSelectListAdapter(mContext, availableCryptoList);
                    rvCryptoList.setAdapter(cryptoSelectListAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CryptoCurrency>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.digicular.coinwatch.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.controller.CoinApi;
import com.digicular.coinwatch.model.MarketChart;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;
import com.digicular.coinwatch.utils.XDateValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PriceChartFragment extends Fragment {

    @BindView(R.id.lineChart_Price)
    LineChart lineChart;

    private Context mContext;
    private String coinId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        coinId = getArguments().getString(Contract.CHART_COINID);

        return inflater.inflate(R.layout.fragment_price_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        // Chart Styling and Interaction
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setBorderWidth(2);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.animateX(500);
        lineChart.setHighlightPerTapEnabled(true);

        // Fetching Market Chart
        getMarketChart(coinId, 7);
    }

    // Fetches Market data and Build a chart
    public void getMarketChart(String coinId, int days) {
        Retrofit retrofit = Utils.getRetrofitWithCache(mContext, Contract.BASE_URL);
        CoinApi coinApi = retrofit.create(CoinApi.class);
        Call<MarketChart> marketChartCall = coinApi.getMarketChart(coinId, days, Contract.CURRENCY);

        marketChartCall.enqueue(new Callback<MarketChart>() {
            @Override
            public void onResponse(Call<MarketChart> call, Response<MarketChart> response) {
                Log.d("JSON_URL", response.raw().request().url().toString());

                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Request cannot be processed", Toast.LENGTH_SHORT).show();
                } else {
                    MarketChart marketChart = response.body();
                    makeChart(marketChart, days);
                }
            }


            @Override
            public void onFailure(Call<MarketChart> call, Throwable t) {
                Log.d("JSON_URL", "Message" + t.getMessage());
                Toast.makeText(mContext, "Market chart - Request Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Generates LineDataSet to be fed to Chart
    private LineDataSet generateLineDataSet(ArrayList<ArrayList<BigDecimal>> values, String label) {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (ArrayList<BigDecimal> value : values) {
            entries.add(new Entry(value.get(0).floatValue(), value.get(1).floatValue()));
        }

        return new LineDataSet(entries, label);
    }


    // Builds a Line Chart with input MarketChart data
    private void makeChart(MarketChart marketChart, int days) {
        ArrayList<ArrayList<BigDecimal>> prices = marketChart.getPrices();

        lineChart.setNoDataText("Loading data...");
        lineChart.setNoDataTextColor(mContext.getResources().getColor(R.color.colorPrimary));

        // Setting Date/Time format on X-Axis
        if (days == 1) {
            // Format - 21:04
            lineChart.getXAxis().setValueFormatter(new XDateValueFormatter("hrs"));
        } else if (days <= 180) {
            // Format - 24 Nov
            lineChart.getXAxis().setValueFormatter(new XDateValueFormatter("days"));
        } else if (days > 180) {
            // Format - Nov '19
            lineChart.getXAxis().setValueFormatter(new XDateValueFormatter("months"));
        }

        // Generating Feedable DataSet from values
        LineDataSet lineDataSet = generateLineDataSet(prices, "Price");

        // Styling DataSet
        lineDataSet.setColor(mContext.getResources().getColor(R.color.colorSecondary));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setCircleRadius(1f);
        lineDataSet.setLineWidth(2);

        LineData lineData = new LineData(lineDataSet);

        // Feeding Data to Chart
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}

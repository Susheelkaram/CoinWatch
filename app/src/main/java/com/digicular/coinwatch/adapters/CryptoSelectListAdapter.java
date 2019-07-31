package com.digicular.coinwatch.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.activities.AddAlertActivity;
import com.digicular.coinwatch.activities.CoinDetailActivity;
import com.digicular.coinwatch.model.CryptoCurrency;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class CryptoSelectListAdapter extends RecyclerView.Adapter<CryptoSelectListAdapter.CryptoViewHolder>
        implements Filterable {
    private String mode;
    private Context mContext;
    private ArrayList<CryptoCurrency> mAvailableCryptoList;
    private ArrayList<CryptoCurrency> mFullAvailableCryptoList;


    public CryptoSelectListAdapter(Context context, String pickerMode, ArrayList<CryptoCurrency> availableCryptoList) {
        mContext = context;
        mode = pickerMode;
        mAvailableCryptoList = availableCryptoList;
        mFullAvailableCryptoList = new ArrayList<CryptoCurrency>(availableCryptoList);
        Log.d("JSON response", String.valueOf(mAvailableCryptoList.size()));
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.crypto_item, parent, false);
        return new CryptoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        CryptoCurrency currency = mAvailableCryptoList.get(position);

        String coinName = Utils.capitalizeWord(currency.getName());
        holder.textCoinName.setText(coinName);
        holder.textCoinSymbol.setText(currency.getSymbol());


        Bundle coinPickedBundle = new Bundle();
        coinPickedBundle.putString(Contract.PICKER_DATA_COINID, currency.getId());
        coinPickedBundle.putString(Contract.PICKER_DATA_COINNAME, currency.getName());
        coinPickedBundle.putString(Contract.PICKER_DATA_COINSYMBOL, currency.getSymbol());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode){
                    case Contract.PICKER_MODE_ALERT:
                        launchNewAlertEditor(coinPickedBundle);
                        ((Activity) mContext).finish();
                        break;
                    case Contract.PICKER_MODE_VIEW:
                        launchDetailedCoinInfo(currency.getId());
                        ((Activity) mContext).finish();
                        break;
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return mAvailableCryptoList.size();
    }

    public class CryptoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_ListCoinLogo)
        ImageView imageCoinLogo;
        @BindView(R.id.text_ListCoinName)
        TextView textCoinName;
        @BindView(R.id.text_ListCoinSymbol)
        TextView textCoinSymbol;

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }


    /**********************
    * Search Filter Logic
    ***********************/
    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String matchString = constraint.toString().toLowerCase();

            // If Query is empty
            if (matchString.isEmpty()) {
                mAvailableCryptoList = mFullAvailableCryptoList;
            }
            else {
                ArrayList<CryptoCurrency> filteredList = new ArrayList<>();
                CryptoCurrency exactMatch = null;
                ArrayList<CryptoCurrency> nearMatches = new ArrayList<>();

                for (CryptoCurrency currency : mFullAvailableCryptoList) {
                    String coinName = currency.getName().toLowerCase();
                    String coinSymbol = currency.getSymbol().toLowerCase();

                    if (coinName.contains(matchString) || coinSymbol.contains(matchString)) {
                        // Exact match
                        if (coinName.equals(matchString) || coinSymbol.equals(matchString)) {
                            exactMatch = currency;
                        }

                        // Near matches - Matches that start with Query
                        else if(coinName.startsWith(matchString) || coinSymbol.startsWith(matchString)) {
                            nearMatches.add(currency);
                        }

                        else {
                            filteredList.add(currency);
                        }
                    }
                }
                // If exact match found Add it to top of the Near matches list
                if(exactMatch != null) {
                    nearMatches.add(0, exactMatch);
                }

                // Add Matches that start with Search Query to Top of List
                filteredList.addAll(0,nearMatches);
                mAvailableCryptoList = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = mAvailableCryptoList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAvailableCryptoList = (ArrayList<CryptoCurrency>) results.values;
            notifyDataSetChanged();
        }
    };


    // Helper Methods
    private void launchNewAlertEditor(Bundle coinPickedBundle){
        Intent i = new Intent(mContext, AddAlertActivity.class);
        coinPickedBundle.putString(Contract.EXTRAS_TAG, Contract.EXTRA_TAG_NEWALERT);
        i.putExtras(coinPickedBundle);
        mContext.startActivity(i);
    }
    private void launchDetailedCoinInfo(String coinId) {
        Intent i = new Intent(mContext, CoinDetailActivity.class);
        i.putExtra(Contract.COIN_ID, coinId);
        mContext.startActivity(i);
    }


    private void launchAddTransactionActivity(Bundle coinPickedBundle) {
        Intent i = new Intent(mContext, AddAlertActivity.class);
        i.putExtras(coinPickedBundle);
        mContext.startActivity(i);
    }
}

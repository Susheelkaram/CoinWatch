package com.digicular.coinwatch.adapters;

import android.content.Context;
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
import com.digicular.coinwatch.model.CryptoCurrency;
import com.digicular.coinwatch.utils.Contract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class CryptoSelectListAdapter extends RecyclerView.Adapter<CryptoSelectListAdapter.CryptoViewHolder>
      implements Filterable{

    private Context mContext;
    private ArrayList<CryptoCurrency> mAvailableCryptoList;
    private ArrayList<CryptoCurrency> mFullAvailableCryptoList;


    public CryptoSelectListAdapter(Context context, ArrayList<CryptoCurrency> availableCryptoList) {
        mContext = context;
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
//
//        holder.textCoinName.setText("Bitcoin");
//        holder.textCoinSymbol.setText("BTC");

        holder.textListCoinName.setText(currency.getName());
        holder.textListCoinSymbol.setText(currency.getSymbol());
    }

    @Override
    public int getItemCount() {
        return mAvailableCryptoList.size();
    }

    public class CryptoViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.imageView_ListCoinLogo)
//        ImageView imageCoinLogo;
//        @BindView(R.id.text_ListCoinName)
//        TextView textCoinName;
//        @BindView(R.id.text_ListCoinSymbol)
//        TextView textCoinSymbol;

        private ImageView imageViewListCoinLogo;
        private TextView textListCoinName;
        private TextView textListCoinSymbol;



        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
//            ButterKnife.bind(mContext, itemView);

            imageViewListCoinLogo = (ImageView) itemView.findViewById( R.id.imageView_ListCoinLogo );
            textListCoinName = (TextView) itemView.findViewById( R.id.text_ListCoinName );
            textListCoinSymbol = (TextView) itemView.findViewById( R.id.text_ListCoinSymbol );
        }
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String matchString = constraint.toString().toLowerCase();
            if(matchString.isEmpty()){
                mAvailableCryptoList = mFullAvailableCryptoList;
            }
            else {
                ArrayList<CryptoCurrency> filteredList = new ArrayList<>();

                for(CryptoCurrency currency : mFullAvailableCryptoList){
                    String coinName = currency.getName().toLowerCase();
                    String coinSymbol = currency.getSymbol().toLowerCase();

                    if(coinName.contains(matchString) || coinSymbol.contains(matchString)){
                        filteredList.add(currency);
                    }
                }
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
}

package com.digicular.coinwatch.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.activities.AddAlertActivity;
import com.digicular.coinwatch.activities.AddTransactionActivity;
import com.digicular.coinwatch.activities.CoinDetailActivity;
import com.digicular.coinwatch.activities.PickCryptoActivity;
import com.digicular.coinwatch.model.CoinInfo;
import com.digicular.coinwatch.utils.Contract;
import com.digicular.coinwatch.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class CoinsListAdapter extends RecyclerView.Adapter<CoinsListAdapter.CoinViewHolder> {
    Context mContext;
    ArrayList<CoinInfo> coinInfoList;
    String currencySymbol = "$";


    public CoinsListAdapter(Context context, ArrayList<CoinInfo> coinInfoList){
        mContext = context;
        this.coinInfoList = coinInfoList;
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_CoinLogo;
        public TextView tv_CoinName;
        public TextView tv_CoinSymbol;
        public TextView tv_CurrentPrice;
        public TextView tv_24HrChangePercent;
        public CardView cv_CoinCard;
        public Button button_AddAlert;
        public Button button_AddTransaction;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);
            img_CoinLogo = itemView.findViewById(R.id.imageView_CoinLogo);
            tv_CoinName = itemView.findViewById(R.id.textView_CoinName);
            tv_CoinSymbol = itemView.findViewById(R.id.textView_CoinSymbol);
            tv_CurrentPrice = itemView.findViewById(R.id.textView_CurrentPrice);
            tv_24HrChangePercent = itemView.findViewById(R.id.textView_24HrChangePercent);
            cv_CoinCard = itemView.findViewById(R.id.card_CoinCard);
            button_AddAlert = itemView.findViewById(R.id.button_AddAlert);
            button_AddTransaction = itemView.findViewById(R.id.button_AddTransaction);

            // Launching Alert Editor to add new Alert for that Crypto
            button_AddAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    launchNewAlertEditor(coinInfoList.get(position));
                }
            });

            button_AddTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddTransactionActivity.class);
                    intent.putExtra(Contract.PICKER_MODE, Contract.PICKER_MODE_TRANSACTION);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_coin_item, parent, false);
        return new CoinViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder coinViewHolder, int position) {
        CoinInfo coin = coinInfoList.get(position);

        DecimalFormat df = new DecimalFormat("###,###.####");

        final String coinId = coin.getId();
        String coinLogoUrl = coin.getImage();
        String coinName = Utils.capitalizeWord(coinId);
        String coinSymbol = coin.getSymbol().toUpperCase();
        String currentPrice = currencySymbol + df.format(coin.getCurrentPrice());
        String change24HPercent;

        if(coin.getPriceChangePercentage24h() > 0){
            coinViewHolder.tv_24HrChangePercent.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            change24HPercent = Contract.UP_ARROWHEAD + df.format(coin.getPriceChangePercentage24h()) + "%";
        }
        else {
            coinViewHolder.tv_24HrChangePercent.setTextColor(mContext.getResources().getColor(R.color.colorRed));
            change24HPercent = Contract.DOWN_ARROWHEAD + df.format(coin.getPriceChangePercentage24h()) + "%";
        }

        coinViewHolder.tv_CoinName.setText(coinName);
        coinViewHolder.tv_CoinSymbol.setText(coinSymbol);
        coinViewHolder.tv_CurrentPrice.setText(currentPrice);
        coinViewHolder.tv_24HrChangePercent.setText(change24HPercent);


        // Loading Crypto LOGO IMAGE with Picasa
        Picasso.get().load(coinLogoUrl)
                .into(coinViewHolder.img_CoinLogo);

        coinViewHolder.cv_CoinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CoinDetailActivity.class);
                intent.putExtra(Contract.COIN_ID, coinId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coinInfoList.size();
    }


    private void launchNewAlertEditor(CoinInfo coin){
        Bundle coinPickedBundle = new Bundle();
        coinPickedBundle.putString(Contract.PICKER_DATA_COINID, coin.getId());
        coinPickedBundle.putString(Contract.PICKER_DATA_COINNAME, coin.getName());
        coinPickedBundle.putString(Contract.PICKER_DATA_COINSYMBOL, coin.getSymbol());
        coinPickedBundle.putString(Contract.EXTRAS_TAG, Contract.EXTRA_TAG_NEWALERT);
        Intent intent = new Intent(mContext, AddAlertActivity.class);
        intent.putExtras(coinPickedBundle);
        mContext.startActivity(intent);
    }
}


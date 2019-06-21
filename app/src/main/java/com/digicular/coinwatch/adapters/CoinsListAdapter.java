package com.digicular.coinwatch.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.activities.CoinDetailActivity;
import com.digicular.coinwatch.activities.PickCryptoActivity;
import com.digicular.coinwatch.activities.PriceAlertListActivity;
import com.digicular.coinwatch.model.CoinInfo;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class CoinsListAdapter extends RecyclerView.Adapter<CoinsListAdapter.CoinViewHolder> {
    Context mContext;
    ArrayList<CoinInfo> listCoinInfo;
    String currencySymbol = "$";
    String upArrowHead = "\u25B2";
    String downArrowHead = "\u25BC";

    public CoinsListAdapter(Context context, ArrayList<CoinInfo> coinsInfoList){
        mContext = context;
        listCoinInfo = coinsInfoList;
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_CoinLogo;
        public TextView tv_CoinSymbol;
        public TextView tv_CurrentPrice;
        public TextView tv_24HrChangePercent;
        public CardView cv_CoinCard;
        public Button button_AddAlert;
        public Button button_AddTransaction;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);
            img_CoinLogo = itemView.findViewById(R.id.imageView_CoinLogo);
            tv_CoinSymbol = itemView.findViewById(R.id.textView_CoinSymbol);
            tv_CurrentPrice = itemView.findViewById(R.id.textView_CurrentPrice);
            tv_24HrChangePercent = itemView.findViewById(R.id.textView_24HrChangePercent);
            cv_CoinCard = itemView.findViewById(R.id.card_CoinCard);
            button_AddAlert = itemView.findViewById(R.id.button_AddAlert);
            button_AddTransaction = itemView.findViewById(R.id.button_AddTransaction);

            // Launching Alerts activity
            button_AddAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PriceAlertListActivity.class);
                    mContext.startActivity(intent);
                }
            });

            button_AddTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PickCryptoActivity.class);
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
        CoinInfo coin = listCoinInfo.get(position);

        DecimalFormat df = new DecimalFormat("###,###.####");

        final String coinId = coin.getId();
        String coinLogoUrl = coin.getImage();
        String coinSymbol = coin.getSymbol().toUpperCase();
        String currentPrice = currencySymbol + df.format(coin.getCurrentPrice());
        String change24HPercent;

        if(coin.getPriceChangePercentage24h() > 0){
            coinViewHolder.tv_24HrChangePercent.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            change24HPercent = upArrowHead + df.format(coin.getPriceChangePercentage24h()) + "%";
        }
        else {
            coinViewHolder.tv_24HrChangePercent.setTextColor(mContext.getResources().getColor(R.color.colorRed));
            change24HPercent = downArrowHead + df.format(coin.getPriceChangePercentage24h()) + "%";
        }

        coinViewHolder.tv_CoinSymbol.setText(coinSymbol);
        coinViewHolder.tv_CurrentPrice.setText(currentPrice);
        coinViewHolder.tv_24HrChangePercent.setText(change24HPercent);
        Picasso.get().load(coinLogoUrl)
                .into(coinViewHolder.img_CoinLogo);

        coinViewHolder.cv_CoinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CoinDetailActivity.class);
                intent.putExtra("coinId", coinId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCoinInfo.size();
    }

}


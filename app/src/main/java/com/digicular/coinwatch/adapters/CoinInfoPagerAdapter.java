package com.digicular.coinwatch.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.digicular.coinwatch.fragments.MoreCoinInfoFragment;
import com.digicular.coinwatch.fragments.PriceChartFragment;

import java.util.List;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class CoinInfoPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private int tabCount = 2;
    private String[] tabTitles = {"Price Chart", "More Info"};

    public CoinInfoPagerAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                PriceChartFragment priceChartFragment = new PriceChartFragment();
                return priceChartFragment;
            case 1:
                MoreCoinInfoFragment moreCoinInfoFragment = new MoreCoinInfoFragment();
                return moreCoinInfoFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

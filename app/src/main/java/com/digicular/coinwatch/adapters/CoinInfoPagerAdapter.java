package com.digicular.coinwatch.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.digicular.coinwatch.fragments.MoreCoinInfoFragment;
import com.digicular.coinwatch.fragments.PriceChartFragment;
import com.digicular.coinwatch.utils.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class CoinInfoPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private int tabCount = 2;
    private String[] tabTitles = {"Price Chart", "More Info"};

    private List<Bundle> fragmentBundles;

    public CoinInfoPagerAdapter(Context context, List<Bundle> bundles, FragmentManager fm){
        super(fm);
        mContext = context;
        fragmentBundles = bundles;
    }
    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                PriceChartFragment priceChartFragment = new PriceChartFragment();
//                priceChartFragment.setArguments(fragmentBundles.get(i));
                return priceChartFragment;
            case 1:
                MoreCoinInfoFragment moreCoinInfoFragment = new MoreCoinInfoFragment();
                moreCoinInfoFragment.setArguments(fragmentBundles.get(i));
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

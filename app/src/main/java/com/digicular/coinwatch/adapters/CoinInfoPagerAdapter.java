package com.digicular.coinwatch.adapters;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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

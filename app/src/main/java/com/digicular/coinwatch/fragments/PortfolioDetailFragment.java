package com.digicular.coinwatch.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digicular.coinwatch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioDetailFragment extends Fragment {

    private Context mContext;

    public PortfolioDetailFragment(Context context) {
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_portfolio_detail, container, false);
    }

}

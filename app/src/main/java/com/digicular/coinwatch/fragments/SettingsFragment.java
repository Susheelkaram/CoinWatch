package com.digicular.coinwatch.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digicular.coinwatch.R;


public class SettingsFragment extends Fragment {

    private Context mContext;
    private String fragmentTitle = "Settings";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        // Setting title
        TextView titleView  = (TextView) getActivity().findViewById(R.id.text_ToolbarTitle);
        if(titleView != null){
            titleView.setText(fragmentTitle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


}

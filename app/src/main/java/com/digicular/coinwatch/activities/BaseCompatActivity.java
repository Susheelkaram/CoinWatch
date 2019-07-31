package com.digicular.coinwatch.activities;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.utils.Utils;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class BaseCompatActivity extends AppCompatActivity{
    @BindView(R.id.AppBar) Toolbar toolbar;
    @BindView(R.id.button_Search) Button btnSearch;
    @BindView(R.id.text_ToolbarTitle) TextView textTitle;
    @BindView(R.id.button_Menu) Button btnMenu;

    private String title;

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        btnSearch.setVisibility(View.GONE);
        btnMenu.setVisibility(View.GONE);
        textTitle.setVisibility(View.GONE);

        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

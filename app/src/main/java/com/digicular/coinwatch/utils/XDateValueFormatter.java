package com.digicular.coinwatch.utils;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class XDateValueFormatter extends IndexAxisValueFormatter {
    String mode;

    SimpleDateFormat sd;

    public XDateValueFormatter(String mode) {
        this.mode = mode;

        switch (mode) {
            case "hrs":
                sd = new SimpleDateFormat("HH:mm");
                break;

            case "days":
                sd = new SimpleDateFormat("dd MMM");
                break;

            case "months":
                sd = new SimpleDateFormat("MMM ''yy");
                break;
        }
    }

    @Override
    public String getFormattedValue(float value) {
        return sd.format(new Date((long) value));
    }
}

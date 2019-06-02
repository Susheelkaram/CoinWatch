package com.digicular.coinwatch.database;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class DBContract {

    // PriceAlerts DB
    public static final String ALERTS_TABLE_NAME = "price_alerts";
    public static final String ALERTS_COL_ID = "id";
    public static final String ALERTS_COL_COINID = "coin_id";
    public static final String ALERTS_COL_CONDITION = "condition";
    public static final String ALERTS_COL_VALUE = "value";
    public static final String ALERTS_COL_ISENABLED = "is_alert_enabled";
    public static final String ALERTS_COL_REPEAT = "is_repeat_enabled";

}

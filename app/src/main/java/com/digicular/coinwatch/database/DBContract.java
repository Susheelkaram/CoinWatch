package com.digicular.coinwatch.database;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class DBContract {

    // Price Alerts Database
    public static final String ALERTS_TABLE_NAME = "price_alerts";
    public static final String ALERTS_COL_ID = "id";
    public static final String ALERTS_COL_COINID = "coin_id";
    public static final String ALERTS_COL_COINSYMBOL = "coin_symbol";
    public static final String ALERTS_COL_CONDITION = "condition";
    public static final String ALERTS_COL_VALUE = "value";
    public static final String ALERTS_COL_ISENABLED = "is_alert_enabled";
    public static final String ALERTS_COL_REPEAT = "is_repeat_enabled";
    public static final String ALERTS_COL_TIMEMILLIS = "time_in_millis";

    // Portfolio Transactions Database
    public static final String TRANSACTION_TABLE_NAME = "portfolio_transactions";
    public static final String TRANSACTION_COL_COINID = "transaction_coinid";
    public static final String TRANSACTION_COL_SYMBOL = "transaction_coin_symbol";
    public static final String TRANSACTION_COL_TYPE = "transaction_type";
    public static final String TRANSACTION_COL_QUANTITY = "transaction_quantity";
    public static final String TRANSACTION_COL_VALUE = "transaction_value";
    public static final String TRANSACTION_COL_RATE = "transaction_exchange_rate";
    public static final String TRANSACTION_COL_NOTE = "transaction_note";
    public static final String TRANSACTION_COL_TIMESTAMP = "transaction_timestamp";
    public static final int TRANSACTION_TYPE_SELL = 0;
    public static final int TRANSACTION_TYPE_BUY = 1;



}

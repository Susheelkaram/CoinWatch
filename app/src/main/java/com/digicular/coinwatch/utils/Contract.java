package com.digicular.coinwatch.utils;

import com.digicular.coinwatch.model.Condition;

import java.util.ArrayList;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class Contract {

    // Coin API
    public static final String BASE_URL = "https://api.coingecko.com/api/v3/";
    public static final String CURRENCY = "usd";


    // Fragment: Coin More info
    public static final String CHART_COINID = "coinId";

    public static final String MOREINFO_FB_BASEURL = "https://facebook.com/";
    public static final String MOREINFO_TWITTER_BASEURL = "https://twitter.com/";
    public static final String MOREINFO_LINKS = "coinLinks";
    public static final String MOREINFO_DESCRIPTION = "coinDescription";

    // Alerts
    public static final String ALERT_LOWER = "Falls below";
    public static final String ALERT_HIGHER = "Rises above";
    public static final String ALERT_VALLOWER = "<=";
    public static final String ALERT_VALHIGHER = ">=";
    public static final String ALERT_EXTRA = "price_alert_extra";


    // Preferences
    public static final String[] DEFAULT_COINWATCHLIST = { "bitcoin", "ethereum","ripple",
            "litecoin","bitcoin-cash", "eos","binancecoin","bitcoin-cash-sv", "cardano", "stellar"};
    public static final String PREF_SETTINGS = "app_settings";
    public static final String PREFO_ISFIRSTLAUNCH = "is_first_launch";
    public static final String PREFO_CURRENCY = "default_currency";
    public static final String PREFO_COINSTOWATCH = "coins_to_watch";

}

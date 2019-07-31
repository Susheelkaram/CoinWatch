package com.digicular.coinwatch.utils;

import com.digicular.coinwatch.model.Condition;

import java.util.ArrayList;

/**
 * Created by AsusPC
 * Website - SusheelKaram.com
 */
public class Contract {

    public static final String UP_ARROWHEAD = "\u25B2";
    public static final String DOWN_ARROWHEAD = "\u25BC";

    // Coin API
    public static final String BASE_URL = "https://api.coingecko.com/api/v3/";
    public static final String CURRENCY = "usd";
    public static final String DOLLAR_SYMBOL = "$";
    public static final String COIN_ID = "coinId";

    // Retrofit constants
    public static final long cacheSize = 20 * 1024 * 1024; // 20MB


    // Fragment: Coin More info
    public static final String CHART_COINID = COIN_ID;

    public static final String MOREINFO_FB_BASEURL = "https://facebook.com/";
    public static final String MOREINFO_TWITTER_BASEURL = "https://twitter.com/";
    public static final String MOREINFO_LINKS = "coinLinks";
    public static final String MOREINFO_DESCRIPTION = "coinDescription";

    // Alerts
    public static final String ALERT_LOWER = "Falls below";
    public static final String ALERT_HIGHER = "Rises above";
    public static final String ALERT_VALLOWER = "below";
    public static final String ALERT_VALHIGHER = "above";
    public static final String ALERT_EXTRA = "price_alert_extra";


    // Preferences
    public static final String[] DEFAULT_COINWATCHLIST = { "bitcoin", "ethereum","ripple",
            "litecoin","bitcoin-cash", "eos","binancecoin","bitcoin-cash-sv", "cardano", "stellar"};
    public static final String PREF_SETTINGS = "app_settings";
    public static final String PREFO_ISFIRSTLAUNCH = "is_first_launch";
    public static final String PREFO_CURRENCY = "default_currency";
    public static final String PREFO_COINSTOWATCH = "coins_to_watch";

    // Notifications
    public static final String NOTIF_TITLESTATIC = " Price alert";

    // WorkManager
    public static final String WORKREQUEST_TAG = "price_check_onetimerequest";
    public static final int WORKREQUEST_DELAY_MINS = 2;

    // Crypto currency Picker
    public static final String PICKER_MODE = "picker_mode";
    public static final String PICKER_MODE_ALERT = "picker_add_alert";
    public static final String PICKER_MODE_TRANSACTION = "picker_add_transaction";
    public static final String PICKER_MODE_VIEW = "picker_view";
    public static final String PICKER_DATA_COINID = COIN_ID;
    public static final String PICKER_DATA_COINNAME = "picker_data_coinname";
    public static final String PICKER_DATA_COINSYMBOL = "picker_data_coinsymbol";

    // Extras / Bundle tags
    public static final String EXTRAS_TAG = "extras_tag";
    public static final String EXTRA_TAG_EDITALERT = "extra_editalert";
    public static final String EXTRA_TAG_NEWALERT = "extra_newalert";

}

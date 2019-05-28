package com.digicular.coinwatch.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import com.google.gson.annotations.SerializedName;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class Links implements Parcelable {

    @SerializedName("homepage")
    private ArrayList<String> homepage;

    @SerializedName("blockchain_site")
    private ArrayList<String> blockChainSites;

    @SerializedName("official_forum_url")
    private ArrayList<String> forumUrl;

    @SerializedName("twitter_screen_name")
    private String twitterUserName;

    @SerializedName("facebook_username")
    private String facebookUserName;

    @SerializedName("subreddit_url")
    private String subredditUrl;



    // GETTER METHODS
    public String getHomepage() {
        return homepage.get(0);
    }

    public ArrayList<String> getBlockChainSites() {
        return blockChainSites;
    }
    public String getBlockChainSitesAsString() {
        ArrayList<String> blockChainSites = getBlockChainSites();
        StringBuilder sb = new StringBuilder();
        for(String i : blockChainSites){
            sb.append(i + "\n");
        }
        return sb.toString();
    }


    public String getForumUrl() {
        return forumUrl.get(0);
    }

    public String getTwitterUserName() {
        return twitterUserName;
    }

    public String getFacebookUserName() {
        return facebookUserName;
    }

    public String getSubredditUrl() {
        return subredditUrl;
    }


    // Parcelable methods
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeList(homepage);
        out.writeList(blockChainSites);
        out.writeList(forumUrl);
        out.writeString(twitterUserName);
        out.writeString(facebookUserName);
        out.writeString(subredditUrl);
    }

    private Links(Parcel in){
        homepage = in.readArrayList(null);
        blockChainSites = in.readArrayList(null);
        forumUrl = in.readArrayList(null);
        twitterUserName = in.readString();
        facebookUserName = in.readString();
        subredditUrl = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}

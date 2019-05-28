package com.digicular.coinwatch.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digicular.coinwatch.R;
import com.digicular.coinwatch.model.Links;
import com.digicular.coinwatch.utils.Contract;



public class MoreCoinInfoFragment extends Fragment {

//    @BindView(R.id.textView_Description) TextView tvCoinDescription;
//    @BindView(R.id.textView_TwitterLink) TextView tvCoinTwitterLink;
//    @BindView(R.id.textView_FBLink) TextView tvCoinFBLink;
//    @BindView(R.id.textView_ForumLink) TextView tvCoinForumLink;
//    @BindView(R.id.textView_WebsiteLink) TextView tvCoinWebsite;
//    @BindView(R.id.textView_RedditLink) TextView tvCoinSubredditLink;

    private TextView tvDescription;
    private TextView tvWebsiteLink;
    private TextView tvForumLink;
    private TextView tvTwitterLink;
    private TextView tvFBLink;
    private TextView tvRedditLink;
    private TextView tvBlockChainLinks;

    private String coinDescription;
    private Links links;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if(arguments != null){
            coinDescription = arguments.getString(Contract.MOREINFO_DESCRIPTION);
            links = arguments.getParcelable(Contract.MOREINFO_LINKS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_coin_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // View Binding ButterKnife
//        ButterKnife.bind(this, view);
        tvDescription = (TextView) view.findViewById(R.id.textView_Description);
        tvWebsiteLink = (TextView) view.findViewById(R.id.textView_WebsiteLink);
        tvForumLink = (TextView) view.findViewById(R.id.textView_ForumLink);
        tvTwitterLink = (TextView) view.findViewById(R.id.textView_TwitterLink);
        tvFBLink = (TextView) view.findViewById(R.id.textView_FBLink);
        tvRedditLink = (TextView) view.findViewById(R.id.textView_RedditLink);
        tvBlockChainLinks = (TextView) view.findViewById(R.id.textView_BlockChainLinks);

        bindValues(links, coinDescription);
    }

    public void bindValues(Links links, String coinDescription){
        String websiteLink = links.getHomepage();
        String twitterLink = links.getTwitterUserName();
        String fbLink = links.getFacebookUserName();
        String forumLink = links.getForumUrl();
        String blockChainLinks = links.getBlockChainSitesAsString();
        String subredditLink = links.getSubredditUrl();

        tvDescription.setText(Html.fromHtml(coinDescription));
        tvWebsiteLink.setText(websiteLink);
        tvFBLink.setText(fbLink);
        tvForumLink.setText(forumLink);
        tvTwitterLink.setText(twitterLink);
        tvRedditLink.setText(subredditLink);
        tvBlockChainLinks.setText(blockChainLinks);
    }
}

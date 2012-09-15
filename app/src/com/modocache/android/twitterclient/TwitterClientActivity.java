package com.modocache.android.twitterclient;

import com.modocache.android.twitterapi.Tweet;
import com.modocache.android.twitterapi.TwitterAPIEngine;
import com.modocache.android.twitterapi.TwitterAPIEngine.TwitterAPIEngineDelegate;

import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class TwitterClientActivity extends ListActivity implements TwitterAPIEngineDelegate {

    TwitterAPIEngine apiEngine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_client);

        String screenName = "sassy_watson";
        getActionBar().setTitle(screenName);

        apiEngine = new TwitterAPIEngine(this);
        apiEngine.loadTweetsForUserWithScreenName(screenName, 10);
    }

    @Override
    public void onEngineDidLoadTweets(Tweet[] tweets) {
        setListAdapter(new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets));
    }

    @Override
    public void onEngineError(Error error) {
        Toast.makeText(getBaseContext(),
                       error.getMessage(),
                       Toast.LENGTH_LONG).show();
    }
}
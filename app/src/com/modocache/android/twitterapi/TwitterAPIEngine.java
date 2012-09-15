package com.modocache.android.twitterapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.os.AsyncTask;

public class TwitterAPIEngine {

    public interface TwitterAPIEngineDelegate {
        public void onEngineDidLoadTweets(Tweet[] tweets);
        public void onEngineError(Error error);
    }

    TwitterAPIEngineDelegate delegate;
    public TwitterAPIEngine(TwitterAPIEngineDelegate delegate) {
        super();
        this.delegate = delegate;
    }

    public void loadTweetsForUserWithScreenName(String screenName, int limit) {
        String urlString = "https://twitter.com/status/user_timeline/%s.json?count=%d";
        urlString = String.format(urlString, screenName, limit);
        new ReadTweetsTask().execute(urlString);
    }

    private class ReadTweetsTask extends AsyncTask<String, Tweet, String> {
        @Override
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                Tweet[] tweets = new Tweet[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    Tweet tweet = new Tweet(jsonArray.getJSONObject(i));
                    tweets[i] = tweet;
                }

                if (delegate != null) {
                    delegate.onEngineDidLoadTweets(tweets);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String readJSONFeed(String urlString) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlString);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();

            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStreamReader streamReader = new InputStreamReader(httpEntity.getContent());
                BufferedReader bufferedReader = new BufferedReader(streamReader);

                String lineString;
                while ((lineString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(lineString);
                }
            } else {
                Error error = new Error("Could not access resource.");
                delegate.onEngineError(error);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
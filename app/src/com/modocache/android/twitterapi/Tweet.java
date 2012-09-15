package com.modocache.android.twitterapi;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet extends TwitterJSONObject {
    public String text;
    public User user;

    public Tweet(JSONObject jsonObject) {
        super(jsonObject);
        this.text = getAttribute("text", "<no text>");
        this.user = getUser();
    }

    @Override
    public String toString() {
        return this.text;
    }

    private User getUser() {
        try {
            return new User(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            return null;
        }
    }
}
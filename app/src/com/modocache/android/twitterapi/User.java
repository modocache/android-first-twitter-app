package com.modocache.android.twitterapi;

import org.json.JSONObject;

public class User extends TwitterJSONObject {
    public String screenName;

    public User(JSONObject jsonObject) {
        super(jsonObject);
        this.screenName = getAttribute("screen_name", "<no screen name>");
    }

    @Override
    public String toString() {
        return this.screenName;
    }
}
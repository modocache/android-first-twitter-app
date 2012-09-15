package com.modocache.android.twitterapi;

import org.json.JSONException;
import org.json.JSONObject;

public class TwitterJSONObject {
    protected JSONObject jsonObject;

    public TwitterJSONObject(JSONObject jsonObject) {
        super();
        this.jsonObject = jsonObject;
    }

    protected String getAttribute(String attributeName, String defaultValue) {
        try {
            return jsonObject.getString(attributeName);
        } catch (JSONException e) {
            return defaultValue;
        }
    }
}
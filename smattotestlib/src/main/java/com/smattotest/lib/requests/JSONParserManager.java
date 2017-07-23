package com.smattotest.lib.requests;

/**
 * Created by pasqualini on 8/18/16.
 */

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParserManager {
    Context context = null;
    private String TAG = null;

    public JSONParserManager(Context context, String TAG) {
        this.TAG = TAG;
        this.context = context;
    }

    public static boolean isNullOrEmpty(JSONObject obj, String tag) {
        return obj.isNull(tag) || !obj.has(tag);
    }

    public static JSONObject convertStringToJSONObject(String resultJson) throws JSONException {
        JSONObject jsonObject = null;
        jsonObject = new JSONObject(resultJson);
        return jsonObject;
    }

    public static JSONArray convertStringToJSONArray(String resultJson) throws JSONException {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(resultJson);
        } catch (JSONException var4) {
            var4.printStackTrace();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return jsonArray;
    }
}

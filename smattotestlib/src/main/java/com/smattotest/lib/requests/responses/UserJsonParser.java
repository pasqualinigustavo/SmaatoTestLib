package com.smattotest.lib.requests.responses;

import android.content.Context;

import com.smattotest.lib.model.User;
import com.smattotest.lib.model.json.AbstractJsonParser;
import com.smattotest.lib.requests.JSONParserManager;

import org.json.JSONObject;

public class UserJsonParser extends AbstractJsonParser<User> {

    public static String TAG = "user";

    public UserJsonParser(Context context) {
        super(context);
    }

    public JSONObject entityToJson(User entity) throws Exception {
        JSONObject json = new JSONObject();
        //
        return json;
    }

    public User jsonToEntity(JSONObject json) throws Exception {
        if (json != null) {
            User entity = new User();
            if (!JSONParserManager.isNullOrEmpty(json, "name"))
                entity.setName(json.optString("name"));
            if (!JSONParserManager.isNullOrEmpty(json, "country"))
                entity.setCountry(json.optString("country"));
            return entity;
        }
        return null;
    }
}

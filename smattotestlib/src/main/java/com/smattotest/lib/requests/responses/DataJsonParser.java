package com.smattotest.lib.requests.responses;

import android.content.Context;

import com.smattotest.lib.model.Data;
import com.smattotest.lib.model.json.AbstractJsonParser;
import com.smattotest.lib.requests.JSONParserManager;

import org.json.JSONObject;

public class DataJsonParser extends AbstractJsonParser<Data> {

    public static String TAG = "data";

    public DataJsonParser(Context context) {
        super(context);
    }

    public JSONObject entityToJson(Data entity) throws Exception {
        JSONObject json = new JSONObject();
        //
        return json;
    }

    public Data jsonToEntity(JSONObject json) throws Exception {
        if (json != null) {
            Data entity = new Data();
            if (!JSONParserManager.isNullOrEmpty(json, "url"))
                entity.setUrl(json.optString("url"));
            if (!JSONParserManager.isNullOrEmpty(json, "text"))
                entity.setText(json.optString("text"));
            return entity;
        }
        return null;
    }
}

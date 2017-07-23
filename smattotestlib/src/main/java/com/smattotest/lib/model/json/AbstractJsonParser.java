package com.smattotest.lib.model.json;

/**
 * Created by pasqualini on 8/18/16.
 */

import android.content.Context;

import com.smattotest.lib.requests.JSONParserManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJsonParser<T> {
    protected Context context;
    protected JSONParserManager jsonParserManager;

    public AbstractJsonParser(Context context) {
        this.context = context;
        this.jsonParserManager = new JSONParserManager(this.context, this.getClass().getSimpleName());
    }

    public abstract JSONObject entityToJson(T var1) throws Exception;

    public abstract T jsonToEntity(JSONObject var1) throws Exception;

    public T jsonToEntity(String responseString) throws Exception {
        JSONObject jsonObject = this.jsonParserManager.convertStringToJSONObject(responseString);
        return this.jsonToEntity(jsonObject);
    }

    public List<T> jsonArrayToEntityList(JSONArray array) throws Exception {
        ArrayList list = new ArrayList();
        if (array != null) {
            int length = array.length();

            for (int i = 0; i < length; ++i) {
                JSONObject item = array.optJSONObject(i);
                if (item != null) {
                    Object obj = this.jsonToEntity(item);
                    list.add(obj);
                }
            }
        }

        return list;
    }

    public List<T> jsonArrayToEntityList(String jsonArrayString) throws Exception {
        JSONArray jsonArr = this.jsonParserManager.convertStringToJSONArray(jsonArrayString);
        return this.jsonArrayToEntityList(jsonArr);
    }

    public JSONArray entityListToJsonArray(List<T> entity) throws Exception {
        JSONArray arr = new JSONArray();
        for (T x : entity) {
            arr.put(entityToJson(x));
        }
        return arr;
    }

    ;

}

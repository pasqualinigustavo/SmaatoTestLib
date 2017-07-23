package com.smattotest.lib.requests.responses;

import android.content.Context;

import com.smattotest.lib.model.Question;
import com.smattotest.lib.model.json.AbstractJsonParser;
import com.smattotest.lib.requests.JSONParserManager;

import org.json.JSONObject;

public class QuestionJsonParser extends AbstractJsonParser<Question> {

    private UserJsonParser userJsonParser = null;
    private DataJsonParser dataJsonParser = null;

    public QuestionJsonParser(Context context) {
        super(context);
        userJsonParser = new UserJsonParser(context);
        dataJsonParser = new DataJsonParser(context);
    }

    public JSONObject entityToJson(Question entity) throws Exception {
        JSONObject json = new JSONObject();
        //
        return json;
    }

    public Question jsonToEntity(JSONObject json) throws Exception {
        if (json != null) {
            Question entity = new Question();
            if (!JSONParserManager.isNullOrEmpty(json, "created"))
                entity.setCreated(json.optInt("created"));
            if (!JSONParserManager.isNullOrEmpty(json, "type"))
                entity.setType(json.optString("type"));
            if (!JSONParserManager.isNullOrEmpty(json, DataJsonParser.TAG))
                entity.setData(dataJsonParser.jsonToEntity(json.getJSONObject(DataJsonParser.TAG)));
            if (!JSONParserManager.isNullOrEmpty(json, UserJsonParser.TAG))
                entity.setUser(userJsonParser.jsonToEntity(json.getJSONObject(UserJsonParser.TAG)));
            return entity;
        }
        return null;
    }
}

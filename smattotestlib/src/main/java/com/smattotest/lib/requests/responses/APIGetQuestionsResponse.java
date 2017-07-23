package com.smattotest.lib.requests.responses;

import android.content.Context;

import com.smattotest.lib.model.Question;

import org.json.JSONArray;

import java.util.List;

public class APIGetQuestionsResponse {

    private List<Question> questionList = null;
    private QuestionJsonParser questionJsonParser = null;

    public APIGetQuestionsResponse(Context context, JSONArray jsonArray) throws Exception {
        questionJsonParser = new QuestionJsonParser(context);
        questionList = questionJsonParser.jsonArrayToEntityList(jsonArray);
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}

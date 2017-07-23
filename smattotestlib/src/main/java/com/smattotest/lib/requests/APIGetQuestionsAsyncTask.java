package com.smattotest.lib.requests;

import android.app.Activity;
import android.os.AsyncTask;

import com.smattotest.lib.requests.responses.APIGetQuestionsResponse;
import com.smattotest.lib.utils.STConstants;
import com.smattotest.lib.utils.StreamUtils;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pasqualini on 8/19/16.
 */
public class APIGetQuestionsAsyncTask extends AsyncTask<Void, Object, Void> {

    private Exception exception = null;
    private Activity context = null;
    private IGetCakesListener listener = null;
    private APIGetQuestionsResponse apiGetQuestionsResponse = null;

    public interface IGetCakesListener {
        void onPreExecute();

        void onPostExecute(Exception e, APIGetQuestionsResponse apiGetQuestionsResponse);
    }

    public APIGetQuestionsAsyncTask(Activity context, IGetCakesListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onPreExecute();
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onPostExecute(this.exception, this.apiGetQuestionsResponse);
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... param) {
        try {
            URL url = new URL(STConstants.JSON_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                byte[] bytes = StreamUtils.readUnknownFully(in);
                String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));
                String jsonText = new String(bytes, charset);
                apiGetQuestionsResponse = new APIGetQuestionsResponse(context, new JSONArray(jsonText));
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }

    private static String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }
}

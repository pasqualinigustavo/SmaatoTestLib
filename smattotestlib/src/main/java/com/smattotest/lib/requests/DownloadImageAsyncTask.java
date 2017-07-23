package com.smattotest.lib.requests;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.smattotest.lib.requests.responses.APIGetQuestionsResponse;
import com.smattotest.lib.utils.StringUtils;
import com.smattotest.lib.utils.image.ImageController;
import com.smattotest.lib.utils.image.ImageLoader;

import java.io.IOException;

/**
 * Created by pasqualini on 8/19/16.
 */
public class DownloadImageAsyncTask extends AsyncTask<Void, Object, Bitmap> {

    private Exception exception = null;
    private String url = null;
    private ImageController imageController = null;
    private IDownloadImageListener listener = null;
    private APIGetQuestionsResponse apiGetQuestionsResponse = null;

    /**
     * Defines the behavior of responseListener
     */
    public interface IDownloadImageListener {
        void onPreExecute();

        void onPostExecute(Exception e, Bitmap bitmapResult);
    }

    public DownloadImageAsyncTask(Context context, String url, IDownloadImageListener listener) {
        this.url = url;
        this.listener = listener;
        this.imageController = new ImageController(context);
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
    protected void onPostExecute(Bitmap bitmapResult) {
        if (bitmapResult != null)
            try {
                String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
                if (!StringUtils.isNullOrEmpty(fileName))
                    imageController.writeMediaOnDisk(fileName, bitmapResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        listener.onPostExecute(this.exception, bitmapResult);
    }

    @Override
    protected Bitmap doInBackground(Void... param) {
        Bitmap bitmap = null;
        try {
            bitmap = ImageLoader.convertToBitmap(ImageLoader.loadImageData(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}

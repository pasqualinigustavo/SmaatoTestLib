package com.smattotest.lib.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smattotest.lib.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    public ImageLoader() {
    }

    public static byte[] loadImageData(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        InputStream inputStream = null;
        try {
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                inputStream = connection.getErrorStream();
            }
            return StreamUtils.readUnknownFully(inputStream);
        } finally {
            StreamUtils.close(inputStream);
            connection.disconnect();
        }
    }

    public static Bitmap convertToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}

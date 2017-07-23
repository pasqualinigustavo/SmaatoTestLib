package com.smattotest.lib.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

    private static final String TAG = StreamUtils.class.getSimpleName();

    public static byte[] readUnknownFully(InputStream stream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            int read = stream.read();
            while (read != -1) {
                outputStream.write(read);
                read = stream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}

package com.smattotest.lib.utils.image;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Pasqualini on 22/03/16.
 */

public class ImageController {

    private Context context = null;
    private static final String DIR_IMAGES = "images";

    public ImageController(Context context) {
        this.context = context;
    }

    public void writeMediaOnDisk(String identifier, Bitmap loadedImage) throws IOException {
        Bitmap img = loadedImage;

        if (img != null) {
            // /data/data/appPACKAGE/app_images/
            File imageDir = getImageDir();

            File imageFile = new File(imageDir, identifier);

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(imageFile);
                img.compress(Bitmap.CompressFormat.JPEG, 40, fos);
            } catch (Exception e) {
                throw e;
            } finally {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            }
        }
    }

    public static String getImageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Bitmap getImageBitmap(String identifier, int size) {
        File imageDir = getImageDir();
        File imageFile = new File(imageDir, identifier);
        if (imageFile.exists()) {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = size;

            Bitmap img = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

            //scale the image //hardware accelerated = true problem....
            //Bitmap too large to be uploaded into a texture (2340x4160, max=4096x4096)
            int nh = (int) (img.getHeight() * (512.0 / img.getWidth()));
            img = Bitmap.createScaledBitmap(img, 512, nh, true);
            return img;
        }

        return null;
    }

    public Drawable getImageDrawable(String identifier, int sampleSize) {
        File imageDir = getImageDir();
        File imageFile = new File(imageDir, identifier);
        if (imageFile.exists()) {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;

            try {
                InputStream is = new FileInputStream(imageFile);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return new BitmapDrawable(context.getResources(), bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    /**
     * Return images directory:
     *
     * @return
     */
    public File getImageDir() {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(DIR_IMAGES, Context.MODE_PRIVATE);

        return directory;
    }


    public Bitmap crop(Bitmap img, int screenWidth) {
        int cropX = 0;
        if (img.getWidth() > screenWidth) {
            cropX = img.getWidth() - screenWidth;
        }

        Bitmap croppedBmp = Bitmap.createBitmap(img, 0, 0, img.getWidth() - cropX, img.getHeight());
        return croppedBmp;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}

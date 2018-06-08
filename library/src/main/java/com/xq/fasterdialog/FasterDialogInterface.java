package com.xq.fasterdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

public class FasterDialogInterface {

    private static ImageLoder imageLoaderd;

    public static ImageLoder getImageLoaderd() {
        if (imageLoaderd == null)
            setImageLoaderd(new DefaultImageLoder());
        return imageLoaderd;
    }

    public static void setImageLoaderd(ImageLoder imageLoaderd) {
        FasterDialogInterface.imageLoaderd = imageLoaderd;
    }

    private static class DefaultImageLoder implements ImageLoder{

        @Override
        public void loadImage(final Context context, final ImageView view, final String url,Object... object) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        final Bitmap bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.setImageBitmap(bitmap);
                            }
                        });
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

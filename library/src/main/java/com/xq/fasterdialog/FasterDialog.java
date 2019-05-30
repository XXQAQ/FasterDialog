package com.xq.fasterdialog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.xq.fasterdialog.util.ImageLoader;

import java.io.IOException;
import java.net.URL;

public class FasterDialog {

    private static Application app;
    private static ImageLoader imageLoader;

    public static void init(Application app){
        init(app,null);
    }

    public static void init(Application app, ImageLoader imageLoader){
        FasterDialog.app = app;
        FasterDialog.imageLoader = imageLoader;
    }

    public static Application getApp() {
        return app;
    }

    public static ImageLoader getImageLoader() {
        if (imageLoader == null)
        {
            imageLoader = new ImageLoader() {
                @Override
                public void loadImage(final Context context, final ImageView view, final String url, Object... object) {
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
            };
        }
        return imageLoader;
    }

}

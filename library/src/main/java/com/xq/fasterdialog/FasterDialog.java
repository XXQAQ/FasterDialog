package com.xq.fasterdialog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.xq.fasterdialog.util.DialogImageLoder;

import java.io.IOException;
import java.net.URL;

public class FasterDialog {

    private static Application app;
    private static DialogImageLoder imageLoaderd;

    public static void init(Application app){
        init(app,null);
    }

    public static void init(Application app, DialogImageLoder imageLoaderd){
        FasterDialog.app = app;
        FasterDialog.imageLoaderd = imageLoaderd;
    }

    public static Application getApp() {
        return app;
    }

    public static DialogImageLoder getImageLoaderd() {
        if (imageLoaderd == null)
        {
            imageLoaderd = new DialogImageLoder() {
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
        return imageLoaderd;
    }

}

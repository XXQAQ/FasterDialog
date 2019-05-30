package com.xq.fasterdialog.util;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLoader {

    public void loadImage(Context context, ImageView view, String url, Object... object);

}

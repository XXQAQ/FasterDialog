package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseImageDialog;


public class ImageDialog extends BaseImageDialog<ImageDialog> {

    private static int STYLE_DEFAULT = STYLE_MATERIALALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_imagedialog_xq;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public ImageDialog(@NonNull Context context) {
        this(context,STYLE_DEFAULT);
    }

    public ImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    private void init() {
        setCustomView(LAYOUT_DEFAULT);
    }

}

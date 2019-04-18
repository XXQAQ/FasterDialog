package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseImageDialog;


public class ImageDialog extends BaseImageDialog<ImageDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_imagedialog_xq;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public ImageDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    public ImageDialog setXQLayoutStyle(){
        setStyle(STYLE_ALERTDIALOG);
        setCustomView(LAYOUT_XQ);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    private void init() {
        setStyle(STYLE_DEFAULT);
        setCustomView(LAYOUT_DEFAULT);
    }

}

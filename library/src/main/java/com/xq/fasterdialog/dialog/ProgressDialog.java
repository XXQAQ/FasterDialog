package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.dialog.base.BaseProgressDialog;

public class ProgressDialog extends BaseProgressDialog<ProgressDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERT;

    public static int LAYOUT_HORIZONTAL_XQ = R.layout.layout_horizontalprogressdialog_xq;
    public static int LAYOUT_HORIZONTAL_METERAIL = R.layout.layout_horizontalprogressdialog_meterail;
    public static int LAYOUT_CIRCLE_XQ = R.layout.layout_circleprogressdialog_xq;
    public static int LAYOUT_CIRCLE_METERAIL = R.layout.layout_circleprogressdialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_CIRCLE_XQ;

    public ProgressDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    public ProgressDialog setHorizontalXQLayoutStyle(){
        setStyle(STYLE_ALERT);
        setCustomView(LAYOUT_HORIZONTAL_XQ,false);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    public ProgressDialog setHorizontalMeterailLayoutStyle(){
        setStyle(STYLE_ALERT);
        setCustomView(LAYOUT_HORIZONTAL_METERAIL,false);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    public ProgressDialog setCircleXQLayoutStyle(){
        setStyle(STYLE_ALERT);
        setCustomView(LAYOUT_CIRCLE_XQ,true);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    public ProgressDialog setCircleMeterailLayoutStyle(){
        setStyle(STYLE_ALERT);
        setCustomView(LAYOUT_CIRCLE_METERAIL,true);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    private void init() {

        setStyle(STYLE_DEFAULT);
        setCustomView(LAYOUT_DEFAULT,true);

        setCancelable(false);
    }
}

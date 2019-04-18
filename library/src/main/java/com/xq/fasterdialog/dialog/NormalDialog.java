package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseNormalDialog;

public class NormalDialog extends BaseNormalDialog<NormalDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_normaldialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_normaldialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public NormalDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    public NormalDialog setXQLayoutStyle(){
        setStyle(STYLE_ALERTDIALOG);
        setCustomView(LAYOUT_XQ);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    public NormalDialog setMeterailLayoutStyle(){
        setStyle(STYLE_ALERTDIALOG);
        setCustomView(LAYOUT_METERAIL);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    private void init() {
        setStyle(STYLE_DEFAULT);
        setCustomView(LAYOUT_DEFAULT);
    }

}

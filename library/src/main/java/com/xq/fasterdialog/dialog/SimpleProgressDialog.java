package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseSimpleDialog;

public class SimpleProgressDialog extends BaseSimpleDialog<SimpleProgressDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_simpleprogressdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_simpleprogressdialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public SimpleProgressDialog(@NonNull Context context) {
        this(context,STYLE_DEFAULT);
    }

    public SimpleProgressDialog(@NonNull Context context, int themeResId) {
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

        setCancel(false);

        setCustomView(LAYOUT_DEFAULT);
    }
}

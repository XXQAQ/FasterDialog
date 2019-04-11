package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseProgressDialog;

public class ProgressDialog extends BaseProgressDialog<ProgressDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERTDIALOG;

    public static int LAYOUT_HORIZONTAL_XQ = R.layout.layout_horizontalprogressdialog_xq;
    public static int LAYOUT_HORIZONTAL_METERAIL = R.layout.layout_horizontalprogressdialog_meterail;
    public static int LAYOUT_CIRCLE_XQ = R.layout.layout_circleprogressdialog_xq;
    public static int LAYOUT_CIRCLE_METERAIL = R.layout.layout_circleprogressdialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_CIRCLE_XQ;

    public ProgressDialog(@NonNull Context context) {
        this(context,STYLE_DEFAULT);
    }

    public ProgressDialog(@NonNull Context context, int themeResId) {
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

        setCancelable(false);

        setIndeterminate(true,LAYOUT_DEFAULT);
    }
}

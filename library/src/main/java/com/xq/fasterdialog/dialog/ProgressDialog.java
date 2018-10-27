package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseProgressDialog;

public class ProgressDialog extends BaseProgressDialog<ProgressDialog> {

    static {
        STYLE_DEFAULT = STYLE_MATERIALALERTDIALOG;
    }

    public static int LAYOUT_XQ = R.layout.layout_progressdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_progressdialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

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

        setCancel(false);

        setCustomView(LAYOUT_DEFAULT);
    }
}

package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseEditDialog;

public class EditDialog extends BaseEditDialog<EditDialog> {

    private static int STYLE_DEFAULT = STYLE_MATERIALALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_editdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_editdialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public EditDialog(@NonNull Context context) {
        this(context,STYLE_DEFAULT);
    }

    public EditDialog(@NonNull Context context, int themeResId) {
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

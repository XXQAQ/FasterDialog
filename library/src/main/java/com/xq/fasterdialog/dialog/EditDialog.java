package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseEditDialog;

public class EditDialog extends BaseEditDialog<EditDialog> {

    public static int LAYOUT_XQ = R.layout.layout_editdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_editdialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public EditDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public EditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(LAYOUT_DEFAULT);
    }

}

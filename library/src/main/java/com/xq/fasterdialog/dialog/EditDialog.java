package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseEditDialog;

public class EditDialog extends BaseEditDialog<EditDialog> {

    public static int LAYOUT_DEFAULT = R.layout.layout_editdialog_default;
    public static int LAYOUT_METERAIL = R.layout.layout_editdialog_meterail;

    public EditDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public EditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(LAYOUT_DEFAULT);
    }


}

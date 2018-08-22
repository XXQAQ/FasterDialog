package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseProgressDialog;

public class ProgressDialog extends BaseProgressDialog<ProgressDialog> {

    public static int LAYOUT_DEFAULT = R.layout.layout_progressdialog_default;
    public static int LAYOUT_METERAIL = R.layout.layout_progressdialog_meterail;

    public ProgressDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public ProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        setCancele(false);

        setCustomView(LAYOUT_DEFAULT);
    }
}

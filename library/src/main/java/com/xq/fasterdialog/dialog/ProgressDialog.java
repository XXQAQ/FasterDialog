package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseProgressDialog;

public class ProgressDialog extends BaseProgressDialog<ProgressDialog> {

    public ProgressDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    @Override
    protected void init() {
        super.init();

        setCancele(false);

        setCustomView(R.layout.layout_progressdialog);
    }
}

package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseNormalDialog;

public class NormalDialog extends BaseNormalDialog<NormalDialog> {

    public NormalDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(R.layout.layout_normaldialog);
    }
}

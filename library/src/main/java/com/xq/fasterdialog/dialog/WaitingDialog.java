package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseSimpleDialog;

public class WaitingDialog extends BaseSimpleDialog<WaitingDialog>{

    public WaitingDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(R.layout.layout_waitingdialog);
    }
}

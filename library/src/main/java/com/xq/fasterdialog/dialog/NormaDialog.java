package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseSimpleDialog;

public class NormaDialog extends BaseSimpleDialog<NormaDialog> {

    public NormaDialog(@NonNull Context context) {
        super(context, R.style.AlertDialog);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(R.layout.layout_normaldialog);
    }
}

package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseNormalDialog;

public class NormalDialog extends BaseNormalDialog<NormalDialog> {

    public NormalDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public NormalDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        defaultLayout();
    }

    public NormalDialog defaultLayout(){
        setCustomView(R.layout.layout_normaldialog_default);
        return this;
    }

    public NormalDialog meterailLayout(){
        setCustomView(R.layout.layout_normaldialog_meterail);
        return this;
    }

}

package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseSimpleDialog;

public class ProgressSimpleDialog extends BaseSimpleDialog<ProgressSimpleDialog>{

    public ProgressSimpleDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public ProgressSimpleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        setCancele(false);

        defaultLayout();
    }

    public ProgressSimpleDialog defaultLayout(){
        setCustomView(R.layout.layout_progresssimpledialog_default);
        return this;
    }

    public ProgressSimpleDialog meterailLayout(){
        setCustomView(R.layout.layout_progresssimpledialog_meterail);
        return this;
    }

}

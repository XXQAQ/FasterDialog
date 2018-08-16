package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseSimpleDialog;

public class SimpleProgressDialog extends BaseSimpleDialog<SimpleProgressDialog> {

    public SimpleProgressDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public SimpleProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        setCancele(false);

        defaultLayout();
    }

    public SimpleProgressDialog defaultLayout(){
        setCustomView(R.layout.layout_simpleprogressdialog_default);
        return this;
    }

    public SimpleProgressDialog meterailLayout(){
        setCustomView(R.layout.layout_simpleprogressdialog_meterail);
        return this;
    }

}

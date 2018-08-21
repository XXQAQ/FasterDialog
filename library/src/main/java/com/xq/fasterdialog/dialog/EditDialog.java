package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseEditDialog;

public class EditDialog extends BaseEditDialog<EditDialog> {

    public EditDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public EditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        defaultLayout();
    }

    public EditDialog meterailLayout(){
        setCustomView(R.layout.layout_editdialog_meterail);
        return this;
    }

    public EditDialog defaultLayout(){
        setCustomView(R.layout.layout_editdialog_default);
        return this;
    }

}

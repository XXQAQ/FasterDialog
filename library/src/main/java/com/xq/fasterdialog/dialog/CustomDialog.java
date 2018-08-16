package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.base.BaseDialog;

public class CustomDialog extends BaseDialog<CustomDialog> {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Deprecated
    @Override
    public CustomDialog setCustomView(int layoutId) {
        return super.setCustomView(layoutId);
    }
}

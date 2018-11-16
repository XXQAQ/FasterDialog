package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.xq.fasterdialog.base.BaseDialog;

public class CustomDialog extends BaseDialog<CustomDialog> {

    public CustomDialog(@NonNull Context context) {
        this(context,STYLE_BASEDIALOG);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomDialog setCustomView(View view){
        this.rootView = view;
        return this;
    }

    @Deprecated
    @Override
    public CustomDialog setCustomView(int layoutId) {
        return this;
    }
}

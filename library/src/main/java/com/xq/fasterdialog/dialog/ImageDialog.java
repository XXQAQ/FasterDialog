package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseImageDialog;


public class ImageDialog extends BaseImageDialog<ImageDialog> {

    public ImageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(R.layout.layout_normaldialog);
    }
}

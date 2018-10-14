package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseImageDialog;


public class ImageDialog extends BaseImageDialog<ImageDialog> {

    public ImageDialog(@NonNull Context context) {
        super(context,R.style.MaterialAlertDialog);
    }

    public ImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(R.layout.layout_imagedialog);
    }
}

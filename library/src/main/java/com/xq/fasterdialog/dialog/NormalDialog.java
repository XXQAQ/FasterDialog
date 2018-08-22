package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseNormalDialog;

public class NormalDialog extends BaseNormalDialog<NormalDialog> {

    public static int LAYOUT_DEFAULT = R.layout.layout_normaldialog_default;
    public static int LAYOUT_METERAIL = R.layout.layout_normaldialog_meterail;

    public NormalDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public NormalDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(LAYOUT_DEFAULT);
    }

}

package com.xq.fasterdialog.dialog;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseNormalDialog;

public class NormalDialog extends BaseNormalDialog<NormalDialog> {

    public static int LAYOUT_XQ = R.layout.layout_normaldialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_normaldialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public NormalDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public NormalDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(LAYOUT_DEFAULT);
    }

}

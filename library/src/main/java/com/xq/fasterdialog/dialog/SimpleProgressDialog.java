package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseSimpleDialog;

public class SimpleProgressDialog extends BaseSimpleDialog<SimpleProgressDialog> {

    public static int LAYOUT_XQ = R.layout.layout_simpleprogressdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_simpleprogressdialog_meterail;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    public SimpleProgressDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public SimpleProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    @Override
    protected void init() {
        super.init();

        setCancele(false);

        setCustomView(LAYOUT_DEFAULT);
    }


}

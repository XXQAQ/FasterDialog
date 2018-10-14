package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseListDialog;

public class ListDialog extends BaseListDialog<ListDialog> {

    public static int LAYOUT_DEFAULT = R.layout.layout_listdialog_default;
    public static int ITEM_DEFAULT = R.layout.item_listdialog_default;
    public static int LAYOUT_METERAIL = R.layout.layout_listdialog_meterail;
    public static int ITEM_METERAIL = R.layout.item_listdialog_meterail;

    public ListDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public ListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(LAYOUT_DEFAULT);
        setItemView(ITEM_DEFAULT);
    }

}

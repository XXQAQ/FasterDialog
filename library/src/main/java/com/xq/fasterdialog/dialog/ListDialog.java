package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseListDialog;

public class ListDialog extends BaseListDialog<ListDialog> {

    public static int LAYOUT_XQ = R.layout.layout_listdialog_xq;
    public static int ITEM_XQ = R.layout.item_listdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_listdialog_meterail;
    public static int ITEM_METERAIL = R.layout.item_listdialog_meterail;
    private static int LAYOUT_DEFAULT = LAYOUT_XQ;
    private static int ITEM_DEFAULT = ITEM_XQ;

    public ListDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public ListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static void setDefaultLayout(int layoutId,int itemId){
        LAYOUT_DEFAULT = layoutId;
        ITEM_DEFAULT = itemId;
    }

    @Override
    protected void init() {
        super.init();

        setCustomView(LAYOUT_DEFAULT);
        setItemView(ITEM_DEFAULT);
    }

}

package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseListDialog;

public class ListDialog extends BaseListDialog<ListDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_listdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_listdialog_meterail;

    public static int ITEM_SINGLE_XQ = R.layout.item_singlelist_xq;
    public static int ITEM_SINGLE_METERAIL = R.layout.item_singlelist_meterail;
    public static int ITEM_MULTI_XQ = R.layout.item_multilist_xq;
    public static int ITEM_MULTI_METERAIL = R.layout.item_multilist_meterail;

    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;
    protected static int ITEM_DEFAULT = ITEM_SINGLE_XQ;

    public ListDialog(@NonNull Context context) {
        this(context,STYLE_DEFAULT);
    }

    public ListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId,int itemLayoutId){
        LAYOUT_DEFAULT = layoutId;
        ITEM_DEFAULT = itemLayoutId;
    }

    private void init() {

        setChooseMode(CHOOSEMODE_SINGLE,LAYOUT_DEFAULT,ITEM_DEFAULT);

    }

}

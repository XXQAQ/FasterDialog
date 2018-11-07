package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseMultiListDialog;

public class MultiListDialog extends BaseMultiListDialog<MultiListDialog> {

    private static int STYLE_DEFAULT = STYLE_MATERIALALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_listdialog_xq;
    public static int ITEM_XQ = R.layout.item_multilistdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_listdialog_meterail;
    public static int ITEM_METERAIL = R.layout.item_multilistdialog_meterail;
    private static int LAYOUT_DEFAULT = LAYOUT_XQ;
    private static int ITEM_DEFAULT = ITEM_XQ;

    public MultiListDialog(@NonNull Context context) {
        this(context,STYLE_DEFAULT);
    }

    public MultiListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId,int itemId){
        LAYOUT_DEFAULT = layoutId;
        ITEM_DEFAULT = itemId;
    }

    private void init() {
        setCustomView(LAYOUT_DEFAULT);
        setItemView(ITEM_DEFAULT);
    }

}

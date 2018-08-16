package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseListDialog;

public class ListDialog extends BaseListDialog<ListDialog> {

    public ListDialog(@NonNull Context context) {
        super(context, R.style.MaterialAlertDialog);
    }

    public ListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void init() {
        super.init();
        setCustomView(R.layout.layout_listdialog_default);
        setItemLayoutId(R.layout.item_listdialog);
    }
}

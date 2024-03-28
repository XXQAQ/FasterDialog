package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xq.fasterdialog.dialog.base.BaseDialog;

public class CustomDialog extends BaseDialog<CustomDialog> {

    protected boolean disconView = true;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disconView) ((ViewGroup)getRootView().getParent()).removeAllViews();
    }

    public CustomDialog setDisconViewOnDismiss(boolean disconView) {
        this.disconView = disconView;
        return this;
    }
}

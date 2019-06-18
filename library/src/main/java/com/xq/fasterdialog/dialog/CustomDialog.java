package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.xq.fasterdialog.dialog.base.BaseDialog;

public class CustomDialog extends BaseDialog<CustomDialog> {

    protected boolean disconView = true;

    public CustomDialog(@NonNull Context context) {
        super(context);
        init();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disconView) ((ViewGroup)getRootView().getParent()).removeView(getRootView());
    }

    private void init(){

    }

    public CustomDialog setDisconViewOnDismiss(boolean disconView) {
        this.disconView = disconView;
        return this;
    }
}

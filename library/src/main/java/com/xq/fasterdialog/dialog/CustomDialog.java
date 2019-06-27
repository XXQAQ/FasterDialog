package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
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
        if (disconView) getDialog().getWindow().setContentView(new View(getContext()));
    }

    private void init(){

    }

    public CustomDialog setDisconViewOnDismiss(boolean disconView) {
        this.disconView = disconView;
        return this;
    }
}

package com.xq.fasterdialog.popupwindow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.xq.fasterdialog.popupwindow.base.BasePopupWindow;

public class CustomPopupWindow extends BasePopupWindow<CustomPopupWindow> {

    protected boolean disconView = true;

    public CustomPopupWindow(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disconView) ((ViewGroup)getCustomView().getParent()).removeAllViews();
    }

    public CustomPopupWindow setDisconViewOnDismiss(boolean disconView) {
        this.disconView = disconView;
        return this;
    }
}

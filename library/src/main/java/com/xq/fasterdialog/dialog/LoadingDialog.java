package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseDialog;

public class LoadingDialog extends BaseDialog<LoadingDialog> {

    private TextView lodingView;

    private String lodingText;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void init() {
        super.init();

        top();

        setY(DensityUtils.dip2px(context,100));

        setCustomView(R.layout.layout_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lodingView = findViewById(context.getResources().getIdentifier("lodingView", "id", context.getPackageName()));

        setLodingText(lodingText);
    }

    public LoadingDialog setLodingText(String lodingText) {
        this.lodingText = lodingText;
        setTextToView(lodingView,lodingText);
        return this;
    }

    public String getLodingText() {
        return lodingText;
    }
}

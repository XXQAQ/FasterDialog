package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseDialog;

public class LoadingDialog extends BaseDialog<LoadingDialog> {

    private static int STYLE_DEFAULT = BaseDialog.STYLE_BASEDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_loadingdialog;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    private TextView loadingView;

    private CharSequence loadingText;

    public LoadingDialog(@NonNull Context context) {
        this(context,STYLE_DEFAULT);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    private void init() {

        loadingText = getContext().getResources().getString(R.string.loading);

        setCancelable(false);

        setCustomView(LAYOUT_DEFAULT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingView = findViewById(getContext().getResources().getIdentifier("loadingView", "id", getContext().getPackageName()));

        setLoadingText(loadingText);
    }

    public LoadingDialog setLoadingText(CharSequence loadingText) {
        this.loadingText = loadingText;
        setTextToView(loadingView,loadingText, View.GONE);
        return this;
    }

    public CharSequence getLodingText() {
        return loadingText;
    }
}

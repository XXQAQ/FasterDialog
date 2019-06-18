package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xq.fasterdialog.R;
import com.xq.fasterdialog.dialog.base.BaseDialog;

public class LoadingDialog extends BaseDialog<LoadingDialog> {

    private static int STYLE_DEFAULT = BaseDialog.STYLE_BASE;

    public static int LAYOUT_XQ = R.layout.layout_loadingdialog;
    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;

    private TextView loadingView;

    private CharSequence loadingText;

    public LoadingDialog(Context context) {
        super(context);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId){
        LAYOUT_DEFAULT = layoutId;
    }

    public LoadingDialog setXQLayoutStyle(){
        setStyle(STYLE_BASE);
        setCustomView(LAYOUT_XQ);
        setWidthWrap();
        setHeightWrap();
        return this;
    }

    private void init() {

        loadingText = getContext().getResources().getString(R.string.loading);

        setStyle(STYLE_DEFAULT);
        setCustomView(LAYOUT_DEFAULT);

        setCancelable(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingView = getView(getContext().getResources().getIdentifier("loadingView", "id", getContext().getPackageName()));

        setLoadingText(loadingText);
    }

    public LoadingDialog setLoadingText(CharSequence loadingText) {
        this.loadingText = loadingText;
        setText(loadingView,loadingText, View.GONE);
        return this;
    }

    public CharSequence getLodingText() {
        return loadingText;
    }
}

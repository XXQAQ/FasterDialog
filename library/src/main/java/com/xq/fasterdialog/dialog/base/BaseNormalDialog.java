package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.xq.fasterdialog.R;
import static com.xq.fasterdialog.FasterDialog.getApp;

public abstract class BaseNormalDialog<T extends BaseNormalDialog> extends BaseSimpleDialog<T> {

    public static String CONFIRM = getApp() == null?"确定" : getApp().getResources().getString(R.string.confirm);
    public static String CANCEL = getApp() == null?"取消" : getApp().getResources().getString(R.string.cancel);

    protected TextView negativeView;
    protected TextView positiveView;
    protected TextView neutralView;

    protected CharSequence negativeText;
    protected CharSequence positiveText;
    protected CharSequence neutralText;
    protected OnDialogClickListener negativeListener;
    protected OnDialogClickListener positiveListener;
    protected OnDialogClickListener neutralListener;

    public BaseNormalDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positiveView = findViewById(getContext().getResources().getIdentifier("positiveView", "id", getContext().getPackageName()));
        negativeView = findViewById(getContext().getResources().getIdentifier("negativeView", "id", getContext().getPackageName()));
        neutralView = findViewById(getContext().getResources().getIdentifier("neutralView", "id", getContext().getPackageName()));

        setPositiveText(positiveText);
        setNegativeText(negativeText);
        setNeutralText(neutralText);

        OnDialogClickListener defaultListener = new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {

            }
        };
        if (!TextUtils.isEmpty(positiveText) && positiveListener == null)
            setPositiveListener(defaultListener);
        else
            setPositiveListener(positiveListener);
        if (!TextUtils.isEmpty(negativeText) && negativeListener == null)
            setNegativeListener(defaultListener);
        else
            setNegativeListener(negativeListener);
        if (!TextUtils.isEmpty(neutralText) && neutralListener == null)
            setNeutralListener(defaultListener);
        else
            setNeutralListener(neutralListener);
    }

    public T setNegativeText(CharSequence negativeText) {
        this.negativeText = negativeText;
        setTextToView(negativeView, negativeText, View.GONE);
        return (T) this;
    }

    public T setPositiveText(CharSequence positiveText) {
        this.positiveText = positiveText;
        setTextToView(positiveView, positiveText, View.GONE);
        return (T) this;
    }

    public T setNeutralText(CharSequence neutralText) {
        this.neutralText = neutralText;
        setTextToView(neutralView, neutralText, View.GONE);
        return (T) this;
    }

    public T setNegativeListener(OnDialogClickListener negativeListener) {
        this.negativeListener = negativeListener;
        bindDialogClickListenerWithView(negativeView, negativeListener,true);
        return (T) this;
    }

    public T setPositiveListener(OnDialogClickListener positiveListener) {
        this.positiveListener = positiveListener;
        bindDialogClickListenerWithView(positiveView, positiveListener,true);
        return (T) this;
    }

    public T setNeutralListener(OnDialogClickListener neutralListener) {
        this.neutralListener = neutralListener;
        bindDialogClickListenerWithView(neutralView, neutralListener,true);
        return (T) this;
    }

    public CharSequence getNegativeText() {
        return negativeText;
    }

    public CharSequence getPositiveText() {
        return positiveText;
    }

    public CharSequence getNeutralText() {
        return neutralText;
    }

    public OnDialogClickListener getNegativeListener() {
        return negativeListener;
    }

    public OnDialogClickListener getPositiveListener() {
        return positiveListener;
    }

    public OnDialogClickListener getNeutralListener() {
        return neutralListener;
    }
}

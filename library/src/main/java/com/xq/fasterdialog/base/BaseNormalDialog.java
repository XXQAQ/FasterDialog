package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.xq.fasterdialog.FasterDialogInterface;
import com.xq.fasterdialog.R;

public abstract class BaseNormalDialog<T extends BaseNormalDialog> extends BaseSimpleDialog<T> {

    public static final String SURE = FasterDialogInterface.getApp().getResources().getString(R.string.sure);
    public static final String CANCLE = FasterDialogInterface.getApp().getResources().getString(R.string.cancle);

    protected TextView negativeView;
    protected TextView positiveView;
    protected TextView neutralView;

    protected String negativeText;
    protected String positiveText;
    protected String neutralText;
    protected OnDialogClickListener negativeListener;
    protected OnDialogClickListener positiveListener;
    protected OnDialogClickListener neutralListener;

    public BaseNormalDialog(@NonNull Context context) {
        super(context);
    }

    public BaseNormalDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        negativeView = findViewById(context.getResources().getIdentifier("negativeView", "id", context.getPackageName()));
        positiveView = findViewById(context.getResources().getIdentifier("positiveView", "id", context.getPackageName()));
        neutralView = findViewById(context.getResources().getIdentifier("neutralView", "id", context.getPackageName()));

        setTextToView(negativeView, negativeText);
        setTextToView(positiveView, positiveText);
        setTextToView(neutralView, neutralText);

        bindDialogClickListenerWithView(negativeView, negativeListener);
        bindDialogClickListenerWithView(positiveView, positiveListener);
        bindDialogClickListenerWithView(neutralView, neutralListener);
    }

    public T setNegativeText(String negativeText) {
        this.negativeText = negativeText;
        setTextToView(negativeView, negativeText);
        return (T) this;
    }

    public T setPositiveText(String positiveText) {
        this.positiveText = positiveText;
        setTextToView(positiveView, positiveText);
        return (T) this;
    }

    public T setNeutralText(String neutralText) {
        this.neutralText = neutralText;
        setTextToView(neutralView, neutralText);
        return (T) this;
    }

    public T setNegativeListener(OnDialogClickListener negativeListener) {
        this.negativeListener = negativeListener;
        bindDialogClickListenerWithView(negativeView, negativeListener);
        return (T) this;
    }

    public T setPositiveListener(OnDialogClickListener positiveListener) {
        this.positiveListener = positiveListener;
        bindDialogClickListenerWithView(positiveView, positiveListener);
        return (T) this;
    }

    public T setNeutralListener(OnDialogClickListener neutralListener) {
        this.neutralListener = neutralListener;
        bindDialogClickListenerWithView(neutralView, neutralListener);
        return (T) this;
    }

    public String getNegativeText() {
        return negativeText;
    }

    public String getPositiveText() {
        return positiveText;
    }

    public String getNeutralText() {
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

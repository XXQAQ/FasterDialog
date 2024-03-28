package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public abstract class BaseNormalDialog<T extends BaseNormalDialog<?>> extends BaseSimpleDialog<T> {

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

        positiveView = getView(getContext().getResources().getIdentifier("positiveView", "id", getContext().getPackageName()));
        negativeView = getView(getContext().getResources().getIdentifier("negativeView", "id", getContext().getPackageName()));
        neutralView = getView(getContext().getResources().getIdentifier("neutralView", "id", getContext().getPackageName()));

        setPositiveText(positiveText);
        setNegativeText(negativeText);
        setNeutralText(neutralText);

        setNegativeListener(negativeListener);
        setPositiveListener(positiveListener);
        setNeutralListener(neutralListener);

    }

    public T setNegativeText(CharSequence negativeText) {
        this.negativeText = negativeText;
        setText(negativeView, negativeText, View.GONE);
        return (T) this;
    }

    public T setPositiveText(CharSequence positiveText) {
        this.positiveText = positiveText;
        setText(positiveView, positiveText, View.GONE);
        return (T) this;
    }

    public T setNeutralText(CharSequence neutralText) {
        this.neutralText = neutralText;
        setText(neutralView, neutralText, View.GONE);
        return (T) this;
    }

    public T setNegativeListener(OnDialogClickListener negativeListener) {
        this.negativeListener = negativeListener;
        setClickListener(negativeView, negativeListener);
        return (T) this;
    }

    public T setPositiveListener(OnDialogClickListener positiveListener) {
        this.positiveListener = positiveListener;
        setClickListener(positiveView, positiveListener);
        return (T) this;
    }

    public T setNeutralListener(OnDialogClickListener neutralListener) {
        this.neutralListener = neutralListener;
        setClickListener(neutralView, neutralListener);
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

}

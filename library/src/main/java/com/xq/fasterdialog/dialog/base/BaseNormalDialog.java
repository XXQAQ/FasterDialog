package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.xq.fasterdialog.R;

public abstract class BaseNormalDialog<T extends BaseNormalDialog> extends BaseSimpleDialog<T> {

    public static String CONFIRM;
    public static String CANCEL;

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
        init();
    }

    private void init(){
        CONFIRM = getContext().getResources().getString(R.string.confirm);
        CANCEL = getContext().getResources().getString(R.string.cancel);
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

        OnDialogClickListener listener = new OnDialogClickListener(true) {
            @Override
            public void onClick(BaseDialog dialog) {

            }
        };
        setPositiveListener(positiveListener != null?positiveListener:listener);
        setNegativeListener(negativeListener != null?negativeListener:listener);
        setNeutralListener(neutralListener != null?neutralListener:listener);
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

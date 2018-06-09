package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xq.fasterdialog.R;

public abstract class BaseSimpleDialog<T extends BaseSimpleDialog> extends BaseDialog<T> {

    protected TextView titleView;
    protected TextView contentView;
    protected ImageView iconView;
    protected View closeView;

    protected String title;
    protected float titleSize;      //单位sp
    protected int titleColor = -1;
    protected String content;
    protected float contentSize;    //单位sp
    protected int contentColor = -1;
    protected int icon;

    public BaseSimpleDialog(@NonNull Context context) {
        super(context, R.style.MaterialDialog);
    }

    public BaseSimpleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleView = findViewById(context.getResources().getIdentifier("titleView", "id", context.getPackageName()));
        contentView = findViewById(context.getResources().getIdentifier("contentView", "id", context.getPackageName()));
        iconView = findViewById(context.getResources().getIdentifier("iconView", "id", context.getPackageName()));
        closeView = findViewById(context.getResources().getIdentifier("closeView", "id", context.getPackageName()));

        setTextToView(titleView,title);
        setTextSizeToView(titleView,titleSize);
        setTextColorToView(titleView,titleColor);

        setTextToView(contentView,content);
        setTextSizeToView(contentView,contentSize);
        setTextColorToView(contentView,contentColor);

        setImageResourceToView(iconView,icon);

        bindDialogClickListenerWithView(closeView, new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {

            }
        });
    }

    public T setTitle(String title) {
        this.title = title;
        setTextToView(titleView,title);
        return (T) this;
    }

    public T setTitleSize(float titleSize) {
        this.titleSize = DensityUtils.sp2px(context,titleSize);
        setTextSizeToView(titleView,titleSize);
        return (T) this;
    }

    public T setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        setTextColorToView(titleView,titleColor);
        return (T) this;
    }

    public T setContent(String content) {
        this.content = content;
        setTextToView(contentView,content);
        return (T) this;
    }

    public T setContentSize(float contentSize) {
        this.contentSize = DensityUtils.sp2px(context,contentSize);
        setTextSizeToView(contentView,contentSize);
        return (T) this;
    }

    public T setContentColor(int contentColor) {
        this.contentColor = contentColor;
        setTextColorToView(contentView,contentColor);
        return (T) this;
    }

    public T setIcon(int resId) {
        this.icon = resId;
        setImageResourceToView(iconView,resId);
        return (T) this;
    }

    public String getTitle() {
        return title;
    }

    public float getTitleSize() {
        return titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public String getContent() {
        return content;
    }

    public float getContentSize() {
        return contentSize;
    }

    public int getContentColor() {
        return contentColor;
    }

    public int getIcon() {
        return icon;
    }
}

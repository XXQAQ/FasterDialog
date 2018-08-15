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

    protected CharSequence tile;
    protected CharSequence content;
    protected int icon;

    public BaseSimpleDialog(@NonNull Context context) {
        super(context);
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

        setTextToView(titleView, tile);

        setTextToView(contentView,content);

        setImageResourceToView(iconView,icon);

        bindDialogClickListenerWithView(closeView, new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {

            }
        });
    }

    public T setData(CharSequence title,CharSequence content,int resId){
        setTile(title);
        setContent(content);
        setIcon(resId);
        return (T) this;
    }

    public T setTile(CharSequence title) {
        this.tile = title;
        setTextToView(titleView,title);
        return (T) this;
    }

    public T setContent(CharSequence content) {
        this.content = content;
        setTextToView(contentView,content);
        return (T) this;
    }


    public T setIcon(int resId) {
        this.icon = resId;
        setImageResourceToView(iconView,resId);
        return (T) this;
    }

    public CharSequence getTile() {
        return tile;
    }

    public CharSequence getContent() {
        return content;
    }

    public int getIcon() {
        return icon;
    }
}

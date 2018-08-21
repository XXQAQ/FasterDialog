package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


public abstract class BaseSimpleDialog<T extends BaseSimpleDialog> extends BaseDialog<T> {

    protected TextView titleView;
    protected TextView contentView;
    protected ImageView iconView;
    protected View closeView;
    protected CompoundButton checkedView;

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
        checkedView = findViewById(context.getResources().getIdentifier("checkedView", "id", context.getPackageName()));

        //考虑到TextView中会含有超链接等局部监听，需要进行下处理
        titleView.setMovementMethod(LinkMovementMethod.getInstance());
        contentView.setMovementMethod(LinkMovementMethod.getInstance());

        setTile(tile);
        setContent(content);
        setIcon(icon);
        bindDialogClickListenerWithView(closeView, new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {

            }
        },true);
    }

    public T setData(int resId,CharSequence title,CharSequence content){
        setTile(title);
        setContent(content);
        setIcon(resId);
        return (T) this;
    }

    public T setTile(CharSequence title) {
        this.tile = title;
        setTextToView(titleView,title, View.GONE);
        return (T) this;
    }

    public T setContent(CharSequence content) {
        this.content = content;
        setTextToView(contentView,content, View.GONE);
        return (T) this;
    }


    public T setIcon(int resId) {
        this.icon = resId;
        setImageResourceToView(iconView,resId,View.GONE);
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

    public boolean isChecked(){
        return checkedView.isChecked();
    }

}

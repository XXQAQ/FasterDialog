package com.xq.fasterdialog.dialog.base;

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

    protected CharSequence title;
    protected CharSequence content;
    protected int icon;

    public BaseSimpleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleView = findViewById(getContext().getResources().getIdentifier("titleView", "id", getContext().getPackageName()));
        contentView = findViewById(getContext().getResources().getIdentifier("contentView", "id", getContext().getPackageName()));
        iconView = findViewById(getContext().getResources().getIdentifier("iconView", "id", getContext().getPackageName()));
        closeView = findViewById(getContext().getResources().getIdentifier("closeView", "id", getContext().getPackageName()));
        checkedView = findViewById(getContext().getResources().getIdentifier("checkedView", "id", getContext().getPackageName()));

        //考虑到TextView中会含有超链接等局部监听，需要进行下处理
        if (titleView != null) titleView.setMovementMethod(LinkMovementMethod.getInstance());
        if (contentView != null) contentView.setMovementMethod(LinkMovementMethod.getInstance());

        setTitle(title);
        setContent(content);
        setIcon(icon);
        bindDialogClickListenerWithView(closeView, new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {

            }
        },true);
    }

    public T setData(int resId,CharSequence title,CharSequence content){
        setTitle(title);
        setContent(content);
        setIcon(resId);
        return (T) this;
    }

    public T setTitle(CharSequence title) {
        this.title = title;
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

    public CharSequence getTitle() {
        return title;
    }

    public CharSequence getContent() {
        return content;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isChecked(){
        if (checkedView == null) return false;
        return checkedView.isChecked();
    }

}

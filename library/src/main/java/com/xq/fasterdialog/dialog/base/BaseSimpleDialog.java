package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


public abstract class BaseSimpleDialog<T extends BaseSimpleDialog<?>> extends BaseDialog<T> {

    protected TextView titleView;
    protected TextView contentView;
    protected ImageView imageView;
    protected View closeView;
    protected CompoundButton checkedView;

    protected CharSequence title;
    protected CharSequence content;
    protected Drawable imageDrawable;

    public BaseSimpleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleView = getView(getContext().getResources().getIdentifier("titleView", "id", getContext().getPackageName()));
        contentView = getView(getContext().getResources().getIdentifier("contentView", "id", getContext().getPackageName()));
        imageView = getView(getContext().getResources().getIdentifier("imageView", "id", getContext().getPackageName()));
        closeView = getView(getContext().getResources().getIdentifier("closeView", "id", getContext().getPackageName()));
        checkedView = getView(getContext().getResources().getIdentifier("checkedView", "id", getContext().getPackageName()));

        //考虑到TextView中会含有超链接等局部监听，需要进行下处理
        if (titleView != null){
            titleView.setMovementMethod(LinkMovementMethod.getInstance());
            titleView.setHighlightColor(Color.TRANSPARENT);
        }
        if (contentView != null){
            contentView.setMovementMethod(LinkMovementMethod.getInstance());
            contentView.setHighlightColor(Color.TRANSPARENT);
        }

        setTitle(title);
        setContent(content);
        setImageDrawable(imageDrawable);

        setClickListener(closeView, new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {
                dismiss();
            }
        });
    }

    public T setTitle(CharSequence title) {
        this.title = title;
        setText(titleView,title, View.GONE);
        return (T) this;
    }

    public T setContent(CharSequence content) {
        this.content = content;
        setText(contentView,content, View.GONE);
        return (T) this;
    }

    public T setImageDrawable(Drawable imageDrawable){
        this.imageDrawable = imageDrawable;
        setImageDrawable(imageView,imageDrawable, View.GONE);
        return (T) this;
    }

    public T setImageRes(int imageRes){
        this.imageDrawable = getContext().getResources().getDrawable(imageRes);
        setImageDrawable(imageView,imageDrawable, View.GONE);
        return (T) this;
    }

    public CharSequence getTitle() {
        return title;
    }

    public CharSequence getContent() {
        return content;
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public boolean isChecked(){
        if (checkedView == null) return false;
        return checkedView.isChecked();
    }

}

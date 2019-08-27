package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils;

import com.xq.androidfaster.util.tools.ResourceUtils;

public abstract class BaseSimpleDialog<T extends BaseSimpleDialog> extends BaseDialog<T> {

    protected TextView titleView;
    protected TextView contentView;
    protected ImageView imageView;
    protected View closeView;
    protected CompoundButton checkedView;

    protected CharSequence title;
    protected CharSequence content;
    protected Drawable imageDrawable;
    protected String imageUrl;

    public BaseSimpleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleView = getView(getContext().getResources().getIdentifier("titleView", "id", getContext().getPackageName()));
        contentView = getView(getContext().getResources().getIdentifier("contentView", "id", getContext().getPackageName()));
        imageView = getView(getContext().getResources().getIdentifier("imageView", "id", getContext().getPackageName()));
        if (imageView == null) imageView = getView(getContext().getResources().getIdentifier("iconView", "id", getContext().getPackageName()));//对旧版本的id:iconView作兼容
        closeView = getView(getContext().getResources().getIdentifier("closeView", "id", getContext().getPackageName()));
        checkedView = getView(getContext().getResources().getIdentifier("checkedView", "id", getContext().getPackageName()));
        //考虑到TextView中会含有超链接等局部监听，需要进行下处理
        if (titleView != null) titleView.setMovementMethod(LinkMovementMethod.getInstance());
        if (contentView != null) contentView.setMovementMethod(LinkMovementMethod.getInstance());

        setTitle(title);
        setContent(content);
        setImageDrawable(imageDrawable);
        if (!TextUtils.isEmpty(imageUrl))   setImageUrl(imageUrl);

        setClickListener(closeView, new OnDialogClickListener(true) {
            @Override
            public void onClick(BaseDialog dialog) {

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
        this.imageDrawable = ResourceUtils.getDrawable(imageRes);
        setImageDrawable(imageView,imageDrawable, View.GONE);
        return (T) this;
    }

    public T setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
        setImageUrl(imageView,imageUrl,View.GONE);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isChecked(){
        if (checkedView == null) return false;
        return checkedView.isChecked();
    }

    @Deprecated
    public T setIcon(int resId) {
        setImageRes(resId);
        return (T) this;
    }

}

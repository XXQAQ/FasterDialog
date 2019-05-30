package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils;

public abstract class BaseSimpleDialog<T extends BaseSimpleDialog> extends BaseDialog<T> {

    protected TextView titleView;
    protected TextView contentView;
    protected ImageView imageView;
    protected View closeView;
    protected CompoundButton checkedView;

    protected CharSequence title;
    protected CharSequence content;
    protected int imageRes;
    protected String imageUrl;

    public BaseSimpleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleView = findViewById(getContext().getResources().getIdentifier("titleView", "id", getContext().getPackageName()));
        contentView = findViewById(getContext().getResources().getIdentifier("contentView", "id", getContext().getPackageName()));
        imageView = findViewById(getContext().getResources().getIdentifier("imageView", "id", getContext().getPackageName()));
        if (imageView == null) imageView = findViewById(getContext().getResources().getIdentifier("iconView", "id", getContext().getPackageName()));//对旧版本的id:iconView作兼容
        closeView = findViewById(getContext().getResources().getIdentifier("closeView", "id", getContext().getPackageName()));
        checkedView = findViewById(getContext().getResources().getIdentifier("checkedView", "id", getContext().getPackageName()));

        //考虑到TextView中会含有超链接等局部监听，需要进行下处理
        if (titleView != null) titleView.setMovementMethod(LinkMovementMethod.getInstance());
        if (contentView != null) contentView.setMovementMethod(LinkMovementMethod.getInstance());

        setTitle(title);
        setContent(content);
        setImageRes(imageRes);
        if (!TextUtils.isEmpty(imageUrl))   setImageUrl(imageUrl);

        bindClickListenerWithView(closeView, new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {

            }
        },true);
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

    public T setImageRes(int imageRes){
        this.imageRes = imageRes;
        setImageResourceToView(imageView,imageRes, View.GONE);
        return (T) this;
    }

    public T setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
        setImageUrlToView(imageView,imageUrl,View.GONE);
        return (T) this;
    }

    public CharSequence getTitle() {
        return title;
    }

    public CharSequence getContent() {
        return content;
    }

    public int getImageRes() {
        return imageRes;
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

    @Deprecated
    public int getIcon() {
        return imageRes;
    }

}

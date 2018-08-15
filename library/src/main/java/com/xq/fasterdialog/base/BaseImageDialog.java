package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

public abstract class BaseImageDialog<T extends BaseImageDialog> extends BaseNormalDialog<T> {

    private ImageView imageView;

    private String imageUrl;
    private int imageResId;

    public BaseImageDialog(@NonNull Context context) {
        super(context);
    }

    public BaseImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageView = findViewById(context.getResources().getIdentifier("imageView", "id", context.getPackageName()));

        if (!TextUtils.isEmpty(imageUrl))
            setImageUrlToView(imageView, imageUrl);
        else
            setImageResourceToView(imageView,imageResId);
    }

    public T setData(int resId,CharSequence title,CharSequence content,String imageUrl){
        super.setData(resId,title,content);
        setImageUrl(imageUrl);
        return (T) this;
    }

    public T setData(int resId,CharSequence title,CharSequence content,int imageResId){
        super.setData(resId,title,content);
        setImageResId(imageResId);
        return (T) this;
    }

    public T setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.imageResId = 0;
        setImageUrlToView(imageView,imageUrl);
        return (T) this;
    }

    public T setImageResId(int imageResId) {
        this.imageResId = imageResId;
        this.imageUrl = null;
        setImageResourceToView(imageView,imageResId);
        return (T) this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getImageResId() {
        return imageResId;
    }
}

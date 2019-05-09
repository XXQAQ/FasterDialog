package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

public abstract class BaseImageDialog<T extends BaseImageDialog> extends BaseNormalDialog<T> {

    protected ImageView imageView;

    protected String imageUrl;
    protected int imageRes;

    public BaseImageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageView = findViewById(getContext().getResources().getIdentifier("imageView", "id", getContext().getPackageName()));

        if (imageRes == 0 && TextUtils.isEmpty(imageUrl))
        {
            setImageRes(imageRes);
        }
        else
        {
            if (imageRes != 0)  setImageRes(imageRes);
            if (!TextUtils.isEmpty(imageUrl))   setImageUrl(imageUrl);
        }
    }

    public T setData(int resId,CharSequence title,CharSequence content,String imageUrl){
        super.setData(resId,title,content);
        setImageUrl(imageUrl);
        return (T) this;
    }

    public T setData(int resId,CharSequence title,CharSequence content,int imageRes){
        super.setData(resId,title,content);
        setImageRes(imageRes);
        return (T) this;
    }

    public T setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        setImageUrlToView(imageView,imageUrl,View.GONE);
        return (T) this;
    }

    public T setImageRes(int imageRes) {
        this.imageRes = imageRes;
        setImageResourceToView(imageView, this.imageRes, View.GONE);
        return (T) this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getImageRes() {
        return imageRes;
    }
}

package com.xq.fasterdialog.bean;

import android.graphics.drawable.Drawable;
import com.xq.androidfaster.util.tools.ResourceUtils;
import com.xq.fasterdialog.bean.behavior.ItemBehavior;
import com.xq.worldbean.bean.entity.base.BaseBean;

public class ItemBean extends BaseBean implements ItemBehavior {

    private int position;
    private CharSequence title;
    private String imageUrl;
    private Drawable imageDrawable;

    public ItemBean() {
    }

    public ItemBean(CharSequence title) {
        this.title = title;
    }

    public ItemBean(CharSequence title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ItemBean(CharSequence title, Drawable imageDrawable) {
        this.title = title;
        this.imageDrawable = imageDrawable;
    }

    public ItemBean(CharSequence title, int imageRes) {
        this.title = title;
        this.imageDrawable = ResourceUtils.getDrawable(imageRes);
    }

    public ItemBean(CharSequence title, String imageUrl, Object tag) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.tag = tag;
    }

    public ItemBean(CharSequence title, Drawable imageDrawable, Object tag) {
        this.title = title;
        this.imageDrawable = imageDrawable;
        this.tag = tag;
    }

    public ItemBean(CharSequence title, int imageRes, Object tag) {
        this.title = title;
        this.imageDrawable = ResourceUtils.getDrawable(imageRes);
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "position=" + position +
                ", title=" + title +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageDrawable=" + imageDrawable +
                ", id='" + id + '\'' +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemBean itemBean = (ItemBean) o;

        if (title != null ? !title.equals(itemBean.title) : itemBean.title != null) return false;
        if (imageUrl != null ? !imageUrl.equals(itemBean.imageUrl) : itemBean.imageUrl != null)
            return false;
        return imageDrawable != null ? imageDrawable.equals(itemBean.imageDrawable) : itemBean.imageDrawable == null;
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (imageDrawable != null ? imageDrawable.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    @Override
    public void setImageDrawable(Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

}


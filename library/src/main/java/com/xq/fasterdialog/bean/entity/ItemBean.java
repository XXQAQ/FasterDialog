package com.xq.fasterdialog.bean.entity;

import java.io.Serializable;

public class ItemBean implements Serializable {

    private int position;
    private CharSequence title;
    protected String imageUrl;
    protected int imageRes;
    private Object tag;

    public ItemBean() {
    }

    public ItemBean(CharSequence title) {
        this.title = title;
    }

    public ItemBean(CharSequence title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ItemBean(CharSequence title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }

    public ItemBean(CharSequence title, String imageUrl, Object tag) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.tag = tag;
    }

    public ItemBean(CharSequence title, int imageRes, Object tag) {
        this.title = title;
        this.imageRes = imageRes;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "position=" + position +
                ", title=" + title +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageRes=" + imageRes +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBean itemBean = (ItemBean) o;

        if (position != itemBean.position) return false;
        if (imageRes != itemBean.imageRes) return false;
        if (title != null ? !title.equals(itemBean.title) : itemBean.title != null) return false;
        if (imageUrl != null ? !imageUrl.equals(itemBean.imageUrl) : itemBean.imageUrl != null)
            return false;
        return tag != null ? tag.equals(itemBean.tag) : itemBean.tag == null;
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + imageRes;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}


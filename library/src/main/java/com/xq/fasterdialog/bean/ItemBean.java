package com.xq.fasterdialog.bean;

import java.io.Serializable;

public class ItemBean implements Serializable {

    private int position;
    private CharSequence title;
    protected String iconUrl;
    protected int iconRes;
    private Object tag;

    public ItemBean() {
    }

    public ItemBean(CharSequence title) {
        this.title = title;
    }

    public ItemBean(CharSequence title, String iconUrl) {
        this.title = title;
        this.iconUrl = iconUrl;
        this.iconRes = 0;
    }

    public ItemBean(CharSequence title, int iconRes) {
        this.title = title;
        this.iconRes = iconRes;
        this.iconUrl = null;
    }

    public ItemBean(CharSequence title, String iconUrl, Object tag) {
        this.title = title;
        this.iconUrl = iconUrl;
        this.iconRes = 0;
        this.tag = tag;
    }

    public ItemBean(CharSequence title, int iconRes, Object tag) {
        this.title = title;
        this.iconRes = iconRes;
        this.iconUrl = null;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "position=" + position +
                ", title=" + title +
                ", iconUrl='" + iconUrl + '\'' +
                ", iconRes=" + iconRes +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBean itemBean = (ItemBean) o;

        if (position != itemBean.position) return false;
        if (iconRes != itemBean.iconRes) return false;
        if (title != null ? !title.equals(itemBean.title) : itemBean.title != null) return false;
        if (iconUrl != null ? !iconUrl.equals(itemBean.iconUrl) : itemBean.iconUrl != null)
            return false;
        return tag != null ? tag.equals(itemBean.tag) : itemBean.tag == null;
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        result = 31 * result + iconRes;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
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


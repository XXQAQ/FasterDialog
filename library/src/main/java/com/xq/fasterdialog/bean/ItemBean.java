package com.xq.fasterdialog.bean;

import com.xq.fasterdialog.bean.behavior.ItemBehavior;

public class ItemBean implements ItemBehavior {

    private CharSequence title;
    private Object tag;

    public ItemBean() {
    }

    public ItemBean(CharSequence title) {
        this.title = title;
    }

    public ItemBean(CharSequence title,Object tag) {
        this.title = title;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "title=" + title +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBean itemBean = (ItemBean) o;

        if (title != null ? !title.equals(itemBean.title) : itemBean.title != null) return false;
        return tag != null ? tag.equals(itemBean.tag) : itemBean.tag == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
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


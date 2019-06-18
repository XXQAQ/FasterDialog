package com.xq.fasterdialog.bean.entity;

import com.xq.fasterdialog.bean.behavior.ItemBehavior;
import com.xq.fasterdialog.dialog.base.BaseListDialog;

public class ItemBean implements ItemBehavior<ItemBean> {

    private int position;
    private CharSequence title;
    private String imageUrl;
    private int imageRes;
    private String id;
    private Object tag;
    private BaseListDialog.OnItemSelectedListener selectedListener;

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
                ", id='" + id + '\'' +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBean that = (ItemBean) o;

        if (position != that.position) return false;
        if (imageRes != that.imageRes) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return tag != null ? tag.equals(that.tag) : that.tag == null;

    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + imageRes;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public ItemBean setPosition(int position) {
        this.position = position;
        return this;
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public ItemBean setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public ItemBean setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public int getImageRes() {
        return imageRes;
    }

    @Override
    public ItemBean setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ItemBean setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public ItemBean setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public BaseListDialog.OnItemSelectedListener getSelectedListener() {
        return selectedListener;
    }

    @Override
    public ItemBean setSelectedListener(BaseListDialog.OnItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
        return this;
    }
}


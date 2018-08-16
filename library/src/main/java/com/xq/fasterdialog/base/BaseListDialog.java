package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xq.fasterdialog.R;

import java.util.LinkedList;
import java.util.List;


public class BaseListDialog<T extends BaseListDialog>extends BaseNormalDialog<T> {

    private int itemLayoutId;

    private RecyclerView rv;

    private ItemClickListener listener;

    private List<ItemBean> list_item = new LinkedList<>();

    public BaseListDialog(@NonNull Context context) {
        super(context);
    }

    public BaseListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv = findViewById(context.getResources().getIdentifier("rv", "id", context.getPackageName()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new TitleAdapter());

    }

    public T setItemLayoutId(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
        return (T) this;
    }

    public T setItemList(List<ItemBean> list){
        if (rv != null)
        {
            List newList = new LinkedList();
            newList.addAll(list);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(list_item,newList),false);
            diffResult.dispatchUpdatesTo(rv.getAdapter());
            list_item.clear();
            list_item.addAll(list);
        }
        else
            list_item.addAll(list);
        return (T) this;
    }

    public T setItemClickListener(ItemClickListener listener){
        this.listener = listener;
        return (T) this;
    }

    protected class TitleAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listdialog, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, final int position) {
            ViewHolder holder = (ViewHolder)h;
            holder.titleView.setText(list_item.get(position).getTitle());
            if (listener != null)
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(BaseListDialog.this,list_item.get(position),position);
                    }
                });
        }

        @Override
        public int getItemCount() {
            return list_item.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView titleView;
            public ViewHolder(View itemView) {
                super(itemView);
                titleView = findViewById(R.id.titleView);
            }
        }

    }

    public static interface ItemClickListener {

        public void onClick(BaseDialog dialog,ItemBean bean,int position);

    }

    protected static class DiffCallBack extends DiffUtil.Callback {
        private List mOldDatas, mNewDatas;

        public DiffCallBack(List mOldDatas, List mNewDatas) {
            this.mOldDatas = mOldDatas;
            this.mNewDatas = mNewDatas;
        }

        @Override
        public int getOldListSize() {
            return mOldDatas != null ? mOldDatas.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return mNewDatas != null ? mNewDatas.size() : 0;
        }
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
        }
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
        }
    }

    public static class ItemBean {

        private CharSequence title;
        private Object tag;

        public ItemBean() {
        }

        public ItemBean(CharSequence title) {
            this.title = title;
        }

        public ItemBean(CharSequence title, Object tag) {
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

            return title != null ? title.equals(itemBean.title) : itemBean.title == null;
        }

        @Override
        public int hashCode() {
            return title != null ? title.hashCode() : 0;
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

}

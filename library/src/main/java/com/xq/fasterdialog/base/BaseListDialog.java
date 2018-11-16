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
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xq.fasterdialog.bean.ItemBean;

import java.util.LinkedList;
import java.util.List;


public class BaseListDialog<T extends BaseListDialog>extends BaseNormalDialog<T> {

    protected int itemLayoutId;

    protected OnItemChosseListener chosseListener;

    protected RecyclerView rv;

    protected ItemBean select;
    protected List<ItemBean> list_item = new LinkedList<>();

    public BaseListDialog(@NonNull Context context) {
        super(context);
    }

    public BaseListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv = findViewById(getContext().getResources().getIdentifier("rv", "id", getContext().getPackageName()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new TitleAdapter());
        rv.getAdapter().notifyDataSetChanged();
    }

    public T setItemView(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
        return (T) this;
    }

    public T setData(int resId, CharSequence title, CharSequence content,List<ItemBean> list) {
        super.setData(resId,title,content);
        setItemList(list);
        return (T) this;
    }

    public T setData(int resId, CharSequence title, CharSequence content,List<ItemBean> list,ItemBean select) {
        setData(resId,title,content,list);
        setSelect(select);
        return (T) this;
    }


    public T setSelect(ItemBean select){
        this.select = select;
        if (rv != null)
            rv.getAdapter().notifyDataSetChanged();
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

    public T setOnItemChosseListener(OnItemChosseListener listener){
        this.chosseListener = listener;
        return (T) this;
    }

    protected class TitleAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, final int position) {
            ViewHolder holder = (ViewHolder)h;
            final ItemBean bean = list_item.get(position);
            holder.text.setText(bean.getTitle());
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isPressed())
                    {
                        if (chosseListener != null)
                            chosseListener.onItemChoose(BaseListDialog.this,bean);
                        dismiss();
                    }
                }
            });
            if (holder.text instanceof CompoundButton)
            {
                if (select != null && select.equals(bean))
                    ((CompoundButton) holder.text).setChecked(true);
                else
                    ((CompoundButton) holder.text).setChecked(false);
            }
        }

        @Override
        public int getItemCount() {
            return list_item.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView text;
            public ViewHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(getContext().getResources().getIdentifier("text", "id", getContext().getPackageName()));
            }
        }
    }

    public static interface OnItemChosseListener {

        public void onItemChoose(BaseListDialog dialog, ItemBean bean);

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
            return mNewDatas.get(newItemPosition).getClass().isAssignableFrom(mOldDatas.get(oldItemPosition).getClass());
        }
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
        }
    }

}

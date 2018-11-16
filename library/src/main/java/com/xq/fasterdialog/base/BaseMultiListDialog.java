package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import com.xq.fasterdialog.bean.ItemBean;
import java.util.LinkedList;
import java.util.List;


public class BaseMultiListDialog<T extends BaseMultiListDialog>extends BaseNormalDialog<T> {

    protected int itemLayoutId;

    protected OnItemsChosseListener chosseListener;

    protected RecyclerView rv;

    protected List<ItemBean> list_select = new LinkedList<>();
    protected List<ItemBean> list_item = new LinkedList<>();

    public BaseMultiListDialog(@NonNull Context context) {
        super(context);
    }

    public BaseMultiListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv = findViewById(getContext().getResources().getIdentifier("rv", "id", getContext().getPackageName()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new TitleAdapter());
        rv.getAdapter().notifyDataSetChanged();

        if (TextUtils.isEmpty(positiveText))
            setPositiveText(SURE);
        setPositiveListener(new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {
                if (chosseListener != null)
                {
                    if (list_select.size() >0)
                    {
                        chosseListener.onItemsChoose(BaseMultiListDialog.this,list_select);
                        dismiss();
                    }
                }
            }
        });
    }

    //确认键监听已被默认占用，不建议再自行设置
    @Deprecated
    @Override
    public T setPositiveListener(OnDialogClickListener positiveListener) {
        this.positiveListener = positiveListener;
        bindDialogClickListenerWithView(positiveView, positiveListener,false);
        return (T) this;
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

    public T setData(int resId, CharSequence title, CharSequence content,List<ItemBean> list_item,List<ItemBean> list_select) {
        setData(resId,title,content,list_item);
        setSelectList(list_select);
        return (T) this;
    }

    public T setSelectList(List<ItemBean> list){
        list_select.clear();
        list_select.addAll(list);
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

            //删除多余的选择项
            for (ItemBean bean : list_select)
                if (!list_item.contains(bean))
                    list_item.remove(bean);
        }
        else
            list_item.addAll(list);
        return (T) this;
    }

    public T setOnItemsChosseListener(OnItemsChosseListener listener){
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
            holder.text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton v, boolean b) {
                    if (v.isPressed())
                    {
                        if (v.isChecked())
                            list_select.add(bean);
                        else
                            list_select.remove(bean);
                    }
                }
            });
            if (list_select.contains(bean))
                holder.text.setChecked(true);
            else
                holder.text.setChecked(false);
        }

        @Override
        public int getItemCount() {
            return list_item.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            CompoundButton text;
            public ViewHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(getContext().getResources().getIdentifier("text", "id", getContext().getPackageName()));
            }
        }
    }

    public static interface OnItemsChosseListener {

        public void onItemsChoose(BaseMultiListDialog dialog, List<ItemBean> list);

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

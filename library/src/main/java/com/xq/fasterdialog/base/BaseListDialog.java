package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.xq.fasterdialog.bean.ItemBean;
import java.util.LinkedList;
import java.util.List;

public class BaseListDialog<T extends BaseListDialog>extends BaseNormalDialog<T> {

    public static final int CHOOSEMODE_SINGLE = 1;
    public static final int CHOOSEMODE_MULTI = 2;

    protected RecyclerView recyclerView;

    protected int chooseMode = CHOOSEMODE_SINGLE;

    protected int itemLayoutId;

    protected List<ItemBean> list_item = new LinkedList<>();

    //单选模式的监听
    protected OnItemSelectedListener onItemSelectedListener;
    //多选模式的 监听
    protected OnItemsSelectedListener onItemsSelectedListener;

    //单选模式的选择项
    protected ItemBean selection;
    //多选模式的选择项
    protected List<ItemBean> list_selection = new LinkedList<>();

    public BaseListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(getContext().getResources().getIdentifier("recyclerView", "id", getContext().getPackageName()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
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
                if (chooseMode == CHOOSEMODE_SINGLE)
                {
                    holder.text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.isPressed())
                            {
                                if (onItemSelectedListener != null)
                                    onItemSelectedListener.onItemSelected(BaseListDialog.this,bean);
                                dismiss();
                            }
                        }
                    });
                    if (holder.text instanceof CompoundButton)
                    {
                        if (selection != null && selection.equals(bean))
                            ((CompoundButton) holder.text).setChecked(true);
                        else
                            ((CompoundButton) holder.text).setChecked(false);
                    }
                }
                else    if (chooseMode == CHOOSEMODE_MULTI)
                {
                    ((CompoundButton)holder.text).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton v, boolean b) {
                            if (v.isPressed())
                            {
                                if (v.isChecked())
                                    list_selection.add(bean);
                                else
                                    list_selection.remove(bean);
                            }
                        }
                    });
                    if (list_selection.contains(bean))
                        ((CompoundButton)holder.text).setChecked(true);
                    else
                        ((CompoundButton)holder.text).setChecked(false);
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
        });
        recyclerView.getAdapter().notifyDataSetChanged();

        if (chooseMode == CHOOSEMODE_SINGLE)
        {
            setPositiveListener(new OnDialogClickListener() {
                @Override
                public void onClick(BaseDialog dialog) {
                    if (onItemsSelectedListener != null)
                    {
                        if (selection != null)
                        {
                            onItemSelectedListener.onItemSelected(BaseListDialog.this, selection);
                            dismiss();
                        }
                    }
                }
            });
        }
        else    if (chooseMode == CHOOSEMODE_MULTI)
        {
            if (TextUtils.isEmpty(positiveText)) setPositiveText(CONFIRM);
            setPositiveListener(new OnDialogClickListener() {
                @Override
                public void onClick(BaseDialog dialog) {
                    if (onItemsSelectedListener != null)
                    {
                        if (list_selection.size() >0)
                        {
                            onItemsSelectedListener.onItemsSelected(BaseListDialog.this, list_selection);
                            dismiss();
                        }
                    }
                }
            });
        }
    }

    //确认键监听已被默认占用，不建议再自行设置
    @Deprecated
    @Override
    public T setPositiveListener(OnDialogClickListener positiveListener) {
        this.positiveListener = positiveListener;
        bindDialogClickListenerWithView(positiveView, positiveListener,false);
        return (T) this;
    }

    public T setChooseMode(int chooseMode) {
        this.chooseMode = chooseMode;
        return (T) this;
    }

    public T setChooseMode(int chooseMode,int layoutId,int itemLayoutId) {
        setChooseMode(chooseMode);
        setCustomView(layoutId,itemLayoutId);
        return (T) this;
    }

    public T setCustomView(int layoutId,int itemLayoutId) {
        setCustomView(layoutId);
        this.itemLayoutId = itemLayoutId;
        return (T) this;
    }

    @Deprecated
    @Override
    public T setCustomView(int layoutId) {
        return super.setCustomView(layoutId);
    }

    public T setItemList(List<ItemBean> list){
        if (recyclerView != null)
        {
            //删除多余的选择项
            if (chooseMode == CHOOSEMODE_SINGLE)
            {
                if(!list.contains(selection))
                    selection = null;
            }
            else    if (chooseMode == CHOOSEMODE_MULTI)
            {
                for (ItemBean bean : list_selection)
                    if (!list.contains(bean))
                        list_selection.remove(bean);
            }
            list_item.clear();
            list_item.addAll(list);
            recyclerView.getAdapter().notifyDataSetChanged();
            measure();
        }
        else
            list_item.addAll(list);
        return (T) this;
    }

    public T setSelection(ItemBean selection){
        this.selection = selection;
        return (T) this;
    }

    public T setSelectionList(List<ItemBean> list){
        list_selection.clear();
        list_selection.addAll(list);
        return (T) this;
    }

    public T setOnItemSelectedListener(OnItemSelectedListener listener){
        this.onItemSelectedListener = listener;
        return (T) this;
    }

    public T setOnItemsSelectedListener(OnItemsSelectedListener onItemsSelectedListener) {
        this.onItemsSelectedListener = onItemsSelectedListener;
        return (T) this;
    }

    public static interface OnItemSelectedListener {

        public void onItemSelected(BaseListDialog dialog, ItemBean bean);

    }

    public static interface OnItemsSelectedListener {

        public void onItemsSelected(BaseListDialog dialog, List<ItemBean> list);

    }

}

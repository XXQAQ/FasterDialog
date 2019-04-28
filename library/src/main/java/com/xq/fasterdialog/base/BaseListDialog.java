package com.xq.fasterdialog.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.xq.fasterdialog.bean.ItemBean;
import java.util.LinkedList;
import java.util.List;

public class BaseListDialog<T extends BaseListDialog>extends BaseNormalDialog<T> {

    public static final int CHOOSEMODE_SINGLE = 1;
    public static final int CHOOSEMODE_MULTI = 2;

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected Drawable dividerDrawable;

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
        if (layoutManager == null) layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        if (dividerDrawable != null)
        {
            int orientation = DividerItemDecoration.VERTICAL;
            if (layoutManager instanceof LinearLayoutManager)
            {
                if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL)
                    orientation = DividerItemDecoration.VERTICAL;
                else    if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL)
                    orientation = DividerItemDecoration.HORIZONTAL;
            }
            else    if (layoutManager instanceof GridLayoutManager)
            {
                orientation = DividerItemDecoration.HORIZONTAL | DividerItemDecoration.VERTICAL;
            }
            DividerItemDecoration decoration = new DividerItemDecoration(getContext(), orientation);
            decoration.setDrawable(dividerDrawable);
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder h, final int position) {
                final ViewHolder holder = (ViewHolder)h;
                final ItemBean bean = list_item.get(position);bean.setPosition(position);
                if (holder.text != null)
                {
                    if (!TextUtils.isEmpty(bean.getTitle()))
                        holder.text.setText(bean.getTitle());
                    else
                        holder.text.setText("");
                }
                if (holder.icon != null)
                {
                    if (!TextUtils.isEmpty(bean.getIconUrl()))
                        dialogImageLoder.loadImage(getContext(),holder.icon,bean.getIconUrl());
                    else    if (bean.getIconRes() != 0)
                        holder.icon.setImageResource(bean.getIconRes());
                    else
                        dialogImageLoder.loadImage(getContext(),holder.icon,null);
                }
                if (chooseMode == CHOOSEMODE_SINGLE)
                {
                    final CompoundButton.OnCheckedChangeListener listener =  new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked)
                            {
                                selection = bean;
                                if (onItemSelectedListener != null) onItemSelectedListener.onItemSelected(BaseListDialog.this,selection);
                                dismiss();
                            }
                        }
                    };
                    if (holder.state == null)
                    {
                        if (holder.text instanceof CompoundButton)
                        {
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((CompoundButton)holder.text).setOnCheckedChangeListener(null);
                                    ((CompoundButton)holder.text).toggle();
                                    ((CompoundButton)holder.text).setOnCheckedChangeListener(listener);
                                }
                            });
                            ((CompoundButton) holder.text).setOnCheckedChangeListener(null);
                            if (selection != null && selection.equals(bean))
                                ((CompoundButton) holder.text).setChecked(true);
                            else
                                ((CompoundButton) holder.text).setChecked(false);
                            ((CompoundButton) holder.text).setOnCheckedChangeListener(listener);
                        }
                        else
                        {
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    listener.onCheckedChanged(null,true);
                                }
                            });
                        }
                    }
                    else
                    {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder.state.setOnCheckedChangeListener(null);
                                holder.state.toggle();
                                holder.state.setOnCheckedChangeListener(listener);
                            }
                        });
                        (holder.state).setOnCheckedChangeListener(null);
                        if (selection != null && selection.equals(bean))
                            (holder.state).setChecked(true);
                        else
                            (holder.state).setChecked(false);
                        holder.state.setOnCheckedChangeListener(listener);
                    }
                }
                else    if (chooseMode == CHOOSEMODE_MULTI)
                {
                    final CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked)
                                list_selection.add(bean);
                            else
                                list_selection.remove(bean);
                        }
                    };
                    if (holder.state == null)
                    {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((CompoundButton) holder.text).setOnCheckedChangeListener(null);
                                ((CompoundButton) holder.text).toggle();
                                ((CompoundButton) holder.text).setOnCheckedChangeListener(listener);
                            }
                        });
                        ((CompoundButton)holder.text).setOnCheckedChangeListener(null);
                        if (list_selection.contains(bean))
                            ((CompoundButton)holder.text).setChecked(true);
                        else
                            ((CompoundButton)holder.text).setChecked(false);
                        ((CompoundButton)holder.text).setOnCheckedChangeListener(listener);
                    }
                    else
                    {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder.state.setOnCheckedChangeListener(null);
                                holder.state.toggle();
                                holder.state.setOnCheckedChangeListener(listener);
                            }
                        });
                        holder.state.setOnCheckedChangeListener(null);
                        if (list_selection.contains(bean))
                            (holder.state).setChecked(true);
                        else
                            (holder.state).setChecked(false);
                        holder.state.setOnCheckedChangeListener(listener);
                    }
                }
            }

            @Override
            public int getItemCount() {
                return list_item.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder{
                TextView text;
                ImageView icon;
                CompoundButton state;
                public ViewHolder(View itemView) {
                    super(itemView);
                    text = itemView.findViewById(getContext().getResources().getIdentifier("text", "id", getContext().getPackageName()));
                    icon = itemView.findViewById(getContext().getResources().getIdentifier("icon", "id", getContext().getPackageName()));
                    state = itemView.findViewById(getContext().getResources().getIdentifier("state", "id", getContext().getPackageName()));
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

    public T setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return (T) this;
    }

    public T setDividerDrawable(Drawable dividerDrawable) {
        this.dividerDrawable = dividerDrawable;
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

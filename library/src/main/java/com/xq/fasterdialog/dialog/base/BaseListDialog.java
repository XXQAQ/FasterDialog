package com.xq.fasterdialog.dialog.base;

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
import com.xq.fasterdialog.FasterDialog;
import com.xq.fasterdialog.bean.entity.ItemBean;
import java.util.LinkedList;
import java.util.List;

public class BaseListDialog<T extends BaseListDialog>extends BaseNormalDialog<T> {

    public static final int CHOOSE_MODE_SINGLE = 1;
    public static final int CHOOSE_MODE_MULTI = 2;

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected Drawable dividerDrawable;

    protected int chooseMode = CHOOSE_MODE_SINGLE;

    protected int itemLayoutId;

    protected List<ItemBean> list_item = new LinkedList<>();

    //单选模式的监听
    protected OnItemSelectedListener onItemSelectedListener;
    //多选模式的监听
    protected OnItemListSelectedListener onItemListSelectedListener;
    //选择项
    protected List<ItemBean> list_selection = new LinkedList<>();


    public BaseListDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = getView(getContext().getResources().getIdentifier("recyclerView", "id", getContext().getPackageName()));
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
                if (holder.titleView != null)
                {
                    if (!TextUtils.isEmpty(bean.getTitle()))
                        holder.titleView.setText(bean.getTitle());
                    else
                        holder.titleView.setText("");
                }
                if (holder.imageView != null)
                {
                    if (bean.getImageRes() != 0)
                        holder.imageView.setImageResource(bean.getImageRes());
                    if (!TextUtils.isEmpty(bean.getImageUrl()))
                        FasterDialog.getImageLoader().loadImage(getContext(),holder.imageView,bean.getImageUrl());
                }
                if (holder.stateView != null)
                {
                    final CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (chooseMode == CHOOSE_MODE_SINGLE)
                            {
                                if (isChecked)
                                {
                                    list_selection.clear();
                                    list_selection.add(bean);
                                }
                                else
                                {
                                    list_selection.remove(bean);
                                }

                                recyclerView.getAdapter().notifyDataSetChanged();

                                onItemSelectedListener.onItemSelected(BaseListDialog.this, list_selection.isEmpty()?null:list_selection.get(0));
                                if (onItemSelectedListener.isDismiss()) dismiss();
                            }
                            else    if (chooseMode == CHOOSE_MODE_MULTI)
                            {
                                if (isChecked)
                                    list_selection.add(bean);
                                else
                                    list_selection.remove(bean);
                            }
                        }
                    };
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.stateView.toggle();
                        }
                    });
                    holder.stateView.setOnCheckedChangeListener(null);
                    if (list_selection.contains(bean))
                        (holder.stateView).setChecked(true);
                    else
                        (holder.stateView).setChecked(false);
                    holder.stateView.setOnCheckedChangeListener(listener);
                }
                else
                {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (chooseMode == CHOOSE_MODE_SINGLE)
                            {
                                if (!list_selection.contains(bean))
                                {
                                    list_selection.clear();
                                    list_selection.add(bean);
                                }
                                else
                                {
                                    list_selection.remove(bean);
                                }

                                recyclerView.getAdapter().notifyDataSetChanged();

                                onItemSelectedListener.onItemSelected(BaseListDialog.this, list_selection.isEmpty()?null:list_selection.get(0));
                                if (onItemSelectedListener.isDismiss()) dismiss();
                            }
                            else    if (chooseMode == CHOOSE_MODE_MULTI)
                            {
                                if (!list_selection.contains(bean))
                                    list_selection.add(bean);
                                else
                                    list_selection.remove(bean);
                            }
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return list_item.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder{
                TextView titleView;
                ImageView imageView;
                CompoundButton stateView;
                public ViewHolder(View itemView) {
                    super(itemView);
                    titleView = itemView.findViewById(getContext().getResources().getIdentifier("titleView", "id", getContext().getPackageName()));
                    imageView = itemView.findViewById(getContext().getResources().getIdentifier("imageView", "id", getContext().getPackageName()));
                    stateView = itemView.findViewById(getContext().getResources().getIdentifier("stateView", "id", getContext().getPackageName()));
                    if (stateView == null && titleView instanceof CompoundButton)   stateView = (CompoundButton) titleView;
                }
            }
        });

        if (chooseMode == CHOOSE_MODE_MULTI && TextUtils.isEmpty(positiveText)) setPositiveText(CONFIRM);
    }

    //确认键监听已被默认占用，不建议再自行设置
    @Deprecated
    @Override
    public T setPositiveListener(OnDialogClickListener positiveListener) {
        return super.setPositiveListener(new OnDialogClickListener(false) {
            @Override
            public void onClick(BaseDialog dialog) {
                if (chooseMode == CHOOSE_MODE_SINGLE)
                {
                    if (onItemSelectedListener != null)
                    {
                        onItemSelectedListener.onItemSelected(BaseListDialog.this, list_selection.isEmpty()?null:list_selection.get(0));
                        if (onItemSelectedListener.isDismiss()) dismiss();
                    }
                }
                else    if (chooseMode == CHOOSE_MODE_MULTI)
                {
                    if (onItemListSelectedListener != null)
                    {
                        onItemListSelectedListener.onItemListSelected(BaseListDialog.this, list_selection);
                        if (onItemListSelectedListener.isDismiss())  dismiss();
                    }
                }
            }
        });
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
        //删除多余的选择项
        for (ItemBean bean : list_selection)
            if (!list.contains(bean))
                list_selection.remove(bean);

        list_item.clear();
        list_item.addAll(list);
        if (recyclerView != null)
        {
            recyclerView.getAdapter().notifyDataSetChanged();
            measure();
        }
        return (T) this;
    }

    public T setSelection(ItemBean selection){
        list_selection.clear();
        list_selection.add(selection);
        if(recyclerView != null)    recyclerView.getAdapter().notifyDataSetChanged();
        return (T) this;
    }

    public T setSelectionList(List<ItemBean> list){
        list_selection.clear();
        list_selection.addAll(list);
        if(recyclerView != null)    recyclerView.getAdapter().notifyDataSetChanged();
        return (T) this;
    }

    public T setOnItemSelectedListener(OnItemSelectedListener listener){
        this.onItemSelectedListener = listener;
        return (T) this;
    }

    public T setOnItemListSelectedListener(OnItemListSelectedListener listener) {
        this.onItemListSelectedListener = listener;
        return (T) this;
    }

    public static abstract class OnItemSelectedListener extends DialogBehaviorListener{

        public OnItemSelectedListener() {
        }

        public OnItemSelectedListener(boolean isDismiss) {
            super(isDismiss);
        }

        public abstract void onItemSelected(BaseListDialog dialog, ItemBean bean);
    }

    public static abstract class OnItemListSelectedListener extends DialogBehaviorListener{

        public OnItemListSelectedListener() {
        }

        public OnItemListSelectedListener(boolean isDismiss) {
            super(isDismiss);
        }

        public abstract void onItemListSelected(BaseListDialog dialog, List<ItemBean> list);
    }

}

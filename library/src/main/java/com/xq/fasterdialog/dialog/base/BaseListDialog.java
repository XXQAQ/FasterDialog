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
import com.xq.fasterdialog.bean.behavior.ItemBehavior;
import com.xq.worldbean.util.ImageLoader;
import com.xq.worldbean.util.callback.UniverseCallback;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BaseListDialog<T extends BaseListDialog>extends BaseNormalDialog<T> {

    public static final int CHOOSE_MODE_MULTI = 0;
    public static final int CHOOSE_MODE_SINGLE = 1;

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected Drawable dividerDrawable;

    protected int maxChoose = CHOOSE_MODE_SINGLE;

    protected int itemLayoutId;

    //数据源
    protected List<ItemBehavior> list_item = new LinkedList<>();
    //选择项
    protected CustomList<ItemBehavior> list_selection = new CustomList<>(maxChoose);

    //选择监听器
    protected OnItemSelectedListener onItemSelectedListener;


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

                final ItemBehavior bean = list_item.get(position);
                //设置位置属性
                bean.setPosition(position);
                //如果存在交互，则设置交互Callback
                if (bean.getCallback() != null)     bean.getCallback().onCallback(list_item.get(position));

                //标题相关
                if (holder.titleView != null)
                {
                    if (!TextUtils.isEmpty(bean.getTitle()))
                        holder.titleView.setText(bean.getTitle());
                    else
                        holder.titleView.setText("");
                }

                //图片相关
                if (holder.imageView != null)
                {
                    if (bean.getImageRes() != 0)
                        holder.imageView.setImageResource(bean.getImageRes());
                    if (!TextUtils.isEmpty(bean.getImageUrl()))
                        ImageLoader.loadImage(getContext(),bean.getImageUrl(),holder.imageView);
                }

                //状态相关
                final UniverseCallback callback = new UniverseCallback() {
                    @Override
                    public void onCallback(Object... objects) {
                        if (!list_selection.contains(bean))
                            list_selection.add(bean);
                        else
                            list_selection.remove(bean);

                        recyclerView.getAdapter().notifyDataSetChanged();

                        if (onItemSelectedListener != null)
                        {
                            onItemSelectedListener.onItemSelected(BaseListDialog.this,bean);
                            if (onItemSelectedListener.isDismiss() && maxChoose != 0 && list_selection.size() >= maxChoose) dismiss();
                        }
                    }
                };
                if (holder.stateView != null)
                {
                    final CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            callback.onCallback();
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
                            callback.onCallback();
                        }
                    });
                }

                BaseListDialog.this.afterBindViewHolder(holder,position);
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
    }

    protected void afterBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

    }

    public T setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return (T) this;
    }

    public T setDividerDrawable(Drawable dividerDrawable) {
        this.dividerDrawable = dividerDrawable;
        return (T) this;
    }

    public T setMaxChoose(int maxChoose) {
        this.maxChoose = maxChoose;
        list_selection.setMax(maxChoose);
        return (T) this;
    }

    public T setChooseMode(int chooseMode) {
        setMaxChoose(chooseMode);
        return (T) this;
    }

    public T setCustomView(int layoutId,int itemLayoutId,int chooseMode) {
        setCustomView(layoutId,itemLayoutId);
        setChooseMode(chooseMode);
        return (T) this;
    }

    public T setCustomView(int layoutId,int itemLayoutId) {
        setCustomView(layoutId);
        this.itemLayoutId = itemLayoutId;
        return (T) this;
    }

    @Override
    public T setCustomView(int layoutId) {
        return super.setCustomView(layoutId);
    }

    public T setItemList(List list,boolean isAppend){
        //删除多余的选择项
        for (ItemBehavior bean : list_selection)
            if (!list.contains(bean))
                list_selection.remove(bean);

        if (!isAppend) list_item.clear();
        list_item.addAll(list);
        if (recyclerView != null)
        {
            recyclerView.getAdapter().notifyDataSetChanged();
            measure();
        }
        return (T) this;
    }

    public T setItemList(List list){
        return setItemList(list,false);
    }

    public List<ItemBehavior> getSelectionList() {
        return list_selection;
    }

    public ItemBehavior getSelection() {
        return getSelectionList().isEmpty()?null:getSelectionList().get(0);
    }

    public T setSelection(ItemBehavior selection){
        list_selection.add(selection);
        if(recyclerView != null)    recyclerView.getAdapter().notifyDataSetChanged();
        return (T) this;
    }

    public T setSelectionList(List list){
        list_selection.addAll(list);
        if(recyclerView != null)    recyclerView.getAdapter().notifyDataSetChanged();
        return (T) this;
    }

    public T setOnItemSelectedListener(OnItemSelectedListener listener){
        this.onItemSelectedListener = listener;
        return (T) this;
    }

    public static abstract class OnItemSelectedListener extends DialogBehaviorListener{

        public OnItemSelectedListener() {
        }

        public OnItemSelectedListener(boolean isDismiss) {
            super(isDismiss);
        }

        public abstract void onItemSelected(BaseListDialog dialog, ItemBehavior bean);
    }

    protected static class CustomList<T> extends LinkedList<T>{

        private int max;

        public CustomList(int max) {
            this.max = max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        @Override
        public boolean add(T t) {
            if (max != 0 && size() >= max)
            {
                super.removeFirst();
                super.add(t);
            }
            else
            {
                super.add(t);
            }
            return true;
        }

        @Override
        public boolean addAll(Collection c) {
            for (Object o:c)
                add((T) o);
            return true;
        }
    }

    public static abstract class OnItemCompletedListener extends OnDialogClickListener{

        public OnItemCompletedListener() {
        }

        public OnItemCompletedListener(boolean isDismiss) {
            super(isDismiss);
        }

        @Override
        public void onClick(BaseDialog dialog) {
            onItemCompleted((BaseListDialog) dialog,((BaseListDialog)dialog).getSelectionList());
        }

        public abstract void onItemCompleted(BaseListDialog dialog, List<ItemBehavior> list);
    }

}

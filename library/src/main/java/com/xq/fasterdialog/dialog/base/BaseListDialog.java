package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xq.fasterdialog.bean.behavior.ItemBehavior;

import java.util.LinkedList;
import java.util.List;

public class BaseListDialog<T extends BaseListDialog<?>>extends BaseNormalDialog<T> {

    public static final int CHOOSE_MODE_MULTI = Integer.MAX_VALUE;
    public static final int CHOOSE_MODE_SINGLE = 1;

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView.ItemDecoration itemDecoration;

    protected Integer minChoose;

    protected int maxChoose = CHOOSE_MODE_SINGLE;

    protected int itemLayoutId;

    //数据源
    protected List<ItemBehavior> list_item = new LinkedList<>();
    //选择项
    protected List<ItemBehavior> list_selection = new LinkedList<>();

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

        if (itemDecoration != null){
            recyclerView.addItemDecoration(itemDecoration);
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

                if (holder.titleView != null)
                {
                    if (bean.getTitle() != null)
                        holder.titleView.setText(bean.getTitle());
                    else
                        holder.titleView.setText("");
                }

                final List<CompoundButton> compoundButtonList = getAllSomeView(holder.itemView,CompoundButton.class);
                if (compoundButtonList != null && !compoundButtonList.isEmpty())
                {
                    final CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isEnabled()){

                                if (!list_selection.isEmpty() && !list_selection.contains(bean)){
                                    ItemBehavior lastSelect = list_selection.get(list_selection.size()-1);
                                    //notifyItemChanged 后 不会立即执行 onBindViewHolder，所以可以先调用这个方法，再做删除
                                    notifyItemChanged(list_item.indexOf(lastSelect));
                                }

                                if (isChecked){
                                    list_selection.clear();
                                    list_selection.add(bean);
                                    if (onItemSelectedListener != null) onItemSelectedListener.onItemSelected(BaseListDialog.this,bean);
                                } else {
                                    list_selection.remove(bean);
                                    if (onItemSelectedListener != null) onItemSelectedListener.onItemUnSelected(BaseListDialog.this,bean);
                                }

                                for (CompoundButton allCompoundButton : compoundButtonList){
                                    allCompoundButton.setEnabled(false);
                                    allCompoundButton.setChecked(isChecked);
                                    allCompoundButton.setEnabled(true);
                                }
                            }
                        }
                    };
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            compoundButtonList.get(0).toggle();
                        }
                    });
                    //初始化状态
                    for (final CompoundButton stateView : compoundButtonList){
                        stateView.setOnCheckedChangeListener(listener);
                        stateView.setEnabled(false);
                        stateView.setChecked(list_selection.contains(bean));
                        stateView.setEnabled(true);
                    }
                }
                else
                {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            list_selection.clear();
                            list_selection.add(bean);
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return list_item.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder{
                private final TextView titleView;
                public ViewHolder(View itemView) {
                    super(itemView);
                    titleView = itemView.findViewById(getContext().getResources().getIdentifier("titleView", "id", getContext().getPackageName()));
                }
            }
        });

        if (maxChoose == CHOOSE_MODE_SINGLE && list_selection != null && !list_selection.isEmpty()){
            recyclerView.scrollToPosition(list_item.indexOf(list_selection.get(0)));
        }
    }


    public T setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return (T) this;
    }

    public T setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
        return (T) this;
    }

    public T setMinChoose(int minChoose) {
        this.minChoose = minChoose;
        return (T) this;
    }

    public T setMaxChoose(int maxChoose) {
        this.maxChoose = maxChoose;
        return (T) this;
    }

    public T setChooseMode(int chooseMode) {
        setMaxChoose(chooseMode);
        return (T) this;
    }

    public T setItemView(int itemLayoutId){
        this.itemLayoutId = itemLayoutId;
        return (T) this;
    }

    public T setItemList(List<? extends ItemBehavior> list){
        //删除多余的选择项
        for (ItemBehavior bean : list_selection)
            if (!list.contains(bean))
                list_selection.remove(bean);

        list_item.clear();
        list_item.addAll(list);

        if (recyclerView != null)
        {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        return (T) this;
    }

    public List<ItemBehavior> getItemList() {
        return new LinkedList<>(list_item);
    }

    public List<ItemBehavior> getSelectionList() {
        return new LinkedList<>(list_selection);
    }

    public ItemBehavior getSelection() {
        return getSelectionList().isEmpty()?null:getSelectionList().get(0);
    }

    public T setSelection(int position){
        return setSelection(list_item.get(position));
    }

    public T setSelection(ItemBehavior selection){
        list_selection.clear();
        list_selection.add(selection);
        if(recyclerView != null)    recyclerView.getAdapter().notifyDataSetChanged();
        return (T) this;
    }

    public T setSelectionList(List list){
        list_selection.clear();
        list_selection.addAll(list);
        if(recyclerView != null)    recyclerView.getAdapter().notifyDataSetChanged();
        return (T) this;
    }

    public T setOnItemSelectedListener(OnItemSelectedListener listener){
        this.onItemSelectedListener = listener;
        return (T) this;
    }

    public interface OnItemSelectedListener{

         void onItemSelected(BaseListDialog<?> dialog, ItemBehavior bean);

         void onItemUnSelected(BaseListDialog<?> dialog, ItemBehavior bean);
    }

}

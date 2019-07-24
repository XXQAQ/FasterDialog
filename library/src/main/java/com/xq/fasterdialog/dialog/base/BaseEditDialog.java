package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.xq.fasterdialog.bean.InputBean;

import java.util.List;

public class BaseEditDialog<T extends BaseEditDialog> extends BaseNormalDialog<T> {

    protected SparseArray<EditText> array_edit = new SparseArray<>();
    protected SparseArray<InputBean> array_input = new SparseArray();

    public BaseEditDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏所有EditText
        goneAllEdit();

        for (int index=0;index < array_input.size();index++)
        {
            int key = array_input.keyAt(index);
            InputBean bean = array_input.get(key);
            EditText editText = getView(getContext().getResources().getIdentifier("edit_" + key, "id", getContext().getPackageName()));
            array_edit.put(key,editText);
            //设置EditText
            visibleEdit(editText);
            setEditHint(editText,bean.getHint());
            setEditMaxLength(editText,bean.getMaxLength());
            editText.setText(bean.getText());
            editText.setInputType(bean.getInputType());
            //设置固定文字
            if (!TextUtils.isEmpty(bean.getFixedText()))
            {
                TextView fixedView = getView(getContext().getResources().getIdentifier("fixed_" + key, "id", getContext().getPackageName()));
                if (fixedView != null) fixedView.setText(bean.getFixedText());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //自动在第一个EditText弹出输入法
        if (array_edit.size() > 0)
        {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(array_edit.get(0), 0);
        }
    }

    public CharSequence getText(){
        return getTextArray().get(0);
    }

    public SparseArray<CharSequence> getTextArray(){
        SparseArray<CharSequence> array = new SparseArray<>();
        for (int index=0;index < array_edit.size();index++)
        {
            int key = array_edit.keyAt(index);
            EditText editText = array_edit.get(key);
            array.put(key,editText.getText().toString());
        }
        return array;
    }

    public T setErro(int no,CharSequence text){
        setErro(no,text,null);
        return (T) this;
    }

    public T setErro(int no, CharSequence text, Drawable drawable){
        EditText editText = array_edit.get(no);
        setEditErro(editText,text,drawable);
        return (T) this;
    }

    public T setInputBean0(InputBean bean) {
        setInputBean(0,bean);
        return (T) this;
    }

    public T setInputBean1(InputBean bean) {
        setInputBean(1,bean);
        return (T) this;
    }

    public T setInputBean(int no,InputBean bean) {
        array_input.put(no,bean);
        return (T) this;
    }

    protected void invisibleEdit(EditText editText,int visibilityIfNot){
        if (editText.getParent().getParent() instanceof TextInputLayout)
            ((TextInputLayout) editText.getParent().getParent()).setVisibility(visibilityIfNot);
        else
            editText.setVisibility(visibilityIfNot);
    }

    protected void visibleEdit(EditText editText){
        if (editText.getParent().getParent() instanceof TextInputLayout)
            ((TextInputLayout) editText.getParent().getParent()).setVisibility(View.VISIBLE);
        else
            editText.setVisibility(View.VISIBLE);
    }

    protected void setEditErro(EditText editText, CharSequence text, Drawable drawable){
        if (TextUtils.isEmpty(text))
            return;
        if (editText.getParent().getParent() instanceof TextInputLayout && ((TextInputLayout) editText.getParent().getParent()).isErrorEnabled())
            ((TextInputLayout) editText.getParent().getParent()).setError(text);
        else
            if (drawable == null)
                editText.setError(text);
            else
                editText.setError(text,drawable);
    }

    protected void setEditMaxLength(EditText editText, int maxLength){
        if (maxLength <= 0)
            return;
        if (editText.getParent().getParent() instanceof TextInputLayout && ((TextInputLayout) editText.getParent().getParent()).isCounterEnabled())
            ((TextInputLayout) editText.getParent().getParent()).setCounterMaxLength(maxLength);
        else
            editText.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    protected void setEditHint(EditText editText, CharSequence text){
        if (editText.getParent().getParent() instanceof TextInputLayout && ((TextInputLayout) editText.getParent().getParent()).isHintEnabled())
            ((TextInputLayout) editText.getParent().getParent()).setHint(text);
        else
            editText.setHint(text);
    }

    protected void goneAllEdit() {
        List<EditText> list_view = getAllSomeView(getRootView(),EditText.class);
        for (EditText editText : list_view)
        {
            invisibleEdit(editText,View.GONE);
        }
    }

    public static abstract class OnEditCompletedListener extends OnDialogClickListener{

        public OnEditCompletedListener() {
        }

        public OnEditCompletedListener(boolean isDismiss) {
            super(isDismiss);
        }

        @Override
        public void onClick(BaseDialog dialog) {
            onEditCompleted((BaseEditDialog) dialog,((BaseEditDialog)dialog).getTextArray());
        }

        public abstract void onEditCompleted(BaseEditDialog dialog, SparseArray<CharSequence> array);
    }

}


package com.xq.fasterdialog.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.xq.fasterdialog.view.FixedEditInterface;
import java.util.ArrayList;
import java.util.List;

public class BaseEditDialog<T extends BaseEditDialog> extends BaseNormalDialog<T>{

    private OnEditCompleteListner listener;

    private SparseArray<EditText> array_edit = new SparseArray<>();
    private SparseArray<InputBean> array_input = new SparseArray();

    public BaseEditDialog(@NonNull Context context) {
        super(context);
    }

    public BaseEditDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏所有EditText
        goneAllEditText();

        for (int index=0;index < array_input.size();index++)
        {
            int key = array_input.keyAt(index);
            InputBean bean = array_input.get(key);
            EditText editText = findViewById(context.getResources().getIdentifier("edit" + key, "id", context.getPackageName()));
            array_edit.put(key,editText);
            //初始化EditText
            setViewVisible(editText);
            setHintToView(editText,bean.getHint());
            setTextToView(editText,bean.getText());
            setMaxLengthToView(editText,bean.getMaxLength());
            if (bean.getInputType() != -1)
                editText.setInputType(bean.getInputType());
            if (!TextUtils.isEmpty(bean.getFixedText()) && editText instanceof FixedEditInterface)
                ((FixedEditInterface) editText).setFixedText(bean.getFixedText());
        }

        if (TextUtils.isEmpty(positiveText))
            setPositiveText(SURE);
        setPositiveListener(new OnDialogClickListener() {
            @Override
            public void onClick(BaseDialog dialog) {
                if (listener != null)
                {
                    SparseArray<CharSequence> array = new SparseArray<>();
                    for (int index=0;index < array_edit.size();index++)
                    {
                        int key = array_edit.keyAt(index);
                        EditText editText = array_edit.get(key);
                        array.put(key,editText.getText().toString());
                    }
                    listener.onEditComplete(BaseEditDialog.this,array);
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

    public T setOnEditCompleteListner(OnEditCompleteListner listener) {
        this.listener = listener;
        return (T) this;
    }

    public T setErro(int no,CharSequence text){
        setErro(no,text,null);
        return (T) this;
    }

    public T setErro(int no, CharSequence text, Drawable drawable){
        EditText editText = array_edit.get(no);
        setErroToView(editText,text,drawable);
        return (T) this;
    }

    public T setInputBean(int no,InputBean bean) {
        array_input.put(no,bean);
        return (T) this;
    }

    public T setInputBean1(InputBean bean) {
        setInputBean(1,bean);
        return (T) this;
    }

    public T setInputBean2(InputBean bean) {
        setInputBean(2,bean);
        return (T) this;
    }

    //私有方法
    protected void setViewVisible(EditText editText){
        if (editText.getParent().getParent() instanceof TextInputLayout)
            ((TextInputLayout) editText.getParent().getParent()).setVisibility(View.VISIBLE);
        else
            editText.setVisibility(View.VISIBLE);
    }

    protected void setErroToView(EditText editText,CharSequence text,Drawable drawable){
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

    protected void setMaxLengthToView(EditText editText,int maxLength){
        if (maxLength <= 0)
            return;
        if (editText.getParent().getParent() instanceof TextInputLayout && ((TextInputLayout) editText.getParent().getParent()).isCounterEnabled())
            ((TextInputLayout) editText.getParent().getParent()).setCounterMaxLength(maxLength);
        else
            editText.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    protected void setHintToView(EditText editText, CharSequence text){
        if (editText.getParent().getParent() instanceof TextInputLayout && ((TextInputLayout) editText.getParent().getParent()).isHintEnabled())
            ((TextInputLayout) editText.getParent().getParent()).setHint(text);
        else
            editText.setHint(text);
    }

    protected void goneAllEditText() {
        List<EditText> list_view = getAllEditText(rootView);
        for (EditText et : list_view)
        {
            if (et.getParent().getParent() instanceof TextInputLayout)
                ((TextInputLayout) et.getParent().getParent()).setVisibility(View.GONE);
            else
                et.setVisibility(View.GONE);
        }
    }

    private List<EditText> getAllEditText(View view) {
        List<EditText> allchildren = new ArrayList<>();
        if (view instanceof ViewGroup)
        {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++)
            {
                View viewchild = vp.getChildAt(i);
                if (viewchild instanceof EditText)
                    allchildren.add((EditText) viewchild);
                //再次 调用本身（递归）
                allchildren.addAll(getAllEditText(viewchild));
            }
        }
        return allchildren;
    }

    public static interface OnEditCompleteListner {
        public void onEditComplete(BaseEditDialog dialog, SparseArray<CharSequence> array);
    }

    public static class InputBean{

        private CharSequence hint;
        private CharSequence text;
        private CharSequence fixedText;
        private int maxLength;
        private int inputType = -1;

        public InputBean() {
        }

        public InputBean(CharSequence hint) {
            this.hint = hint;
        }

        public InputBean(CharSequence hint, CharSequence text) {
            this.hint = hint;
            this.text = text;
        }

        public InputBean(CharSequence hint, CharSequence text, CharSequence fixedText, int maxLength, int inputType) {
            this.hint = hint;
            this.text = text;
            this.fixedText = fixedText;
            this.maxLength = maxLength;
            this.inputType = inputType;
        }

        @Override
        public String toString() {
            return "InputBean{" +
                    "hint=" + hint +
                    ", text=" + text +
                    ", fixedText=" + fixedText +
                    ", maxLength=" + maxLength +
                    ", inputType=" + inputType +
                    '}';
        }

        public CharSequence getHint() {
            return hint;
        }

        public InputBean setHint(CharSequence hint) {
            this.hint = hint;
            return this;
        }

        public CharSequence getText() {
            return text;
        }

        public InputBean setText(CharSequence text) {
            this.text = text;
            return this;
        }

        public CharSequence getFixedText() {
            return fixedText;
        }

        public InputBean setFixedText(CharSequence fixedText) {
            this.fixedText = fixedText;
            return this;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public InputBean setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public int getInputType() {
            return inputType;
        }

        public InputBean setInputType(int inputType) {
            this.inputType = inputType;
            return this;
        }
    }

}


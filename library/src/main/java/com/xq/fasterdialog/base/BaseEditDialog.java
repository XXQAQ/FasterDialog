package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.xq.fasterdialog.view.FixedEditText;

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

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //隐藏所有EditText
        goneAllEditText();

        for (int index=0;index < array_input.size();index++)
        {
            int key = array_input.keyAt(index);
            InputBean bean = array_input.get(key);
            EditText editText = findViewById(context.getResources().getIdentifier("edit" + key, "id", context.getPackageName()));
            array_edit.put(key,editText);
            setHintToView(editText,bean.getHint());
            setTextToView(editText,bean.getText());
            if (bean.getFixedText() != null && editText instanceof FixedEditText)
                ((FixedEditText) editText).setFixedText(bean.getFixedText());
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
    protected void setHintToView(EditText editText, CharSequence text){
        if (editText.getParent().getParent() instanceof TextInputLayout)
        {
            if (((TextInputLayout) editText.getParent().getParent()).isHintEnabled())
                ((TextInputLayout) editText.getParent().getParent()).setHint(text);
            else
                editText.setHint(text);
            ((TextInputLayout) editText.getParent().getParent()).setVisibility(View.VISIBLE);

        }
        else
        {
            editText.setHint(text);
            editText.setVisibility(View.VISIBLE);
        }
    }


    private void goneAllEditText() {
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

    //确认键监听已被默认占用，不建议再自行设置
    @Deprecated
    @Override
    public T setPositiveListener(OnDialogClickListener positiveListener) {
        this.positiveListener = positiveListener;
        bindDialogClickListenerWithView(positiveView, positiveListener,false);
        return (T) this;
    }

    public T setOnEditListner(OnEditCompleteListner listener) {
        this.listener = listener;
        return (T) this;
    }

    public T setErro(int no,CharSequence text){
        EditText editText = array_edit.get(no);
        if (editText.getParent().getParent() instanceof TextInputLayout)
            ((TextInputLayout) editText.getParent().getParent()).setError(text);
        else
            editText.setError(text);
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

    public static interface OnEditCompleteListner {
        public void onEditComplete(BaseEditDialog dialog, SparseArray<CharSequence> array);
    }

    public static class InputBean{

        private CharSequence hint;
        private CharSequence text;
        private CharSequence fixedText;

        public InputBean() {
        }

        public InputBean(CharSequence hint) {
            this.hint = hint;
        }

        public InputBean(CharSequence hint, CharSequence text) {
            this.hint = hint;
            this.text = text;
        }

        public InputBean(CharSequence hint, CharSequence text, CharSequence fixedText) {
            this.hint = hint;
            this.text = text;
            this.fixedText = fixedText;
        }

        @Override
        public String toString() {
            return "InputBean{" +
                    "hint=" + hint +
                    ", text=" + text +
                    ", fixedText=" + fixedText +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InputBean inputBean = (InputBean) o;

            if (hint != null ? !hint.equals(inputBean.hint) : inputBean.hint != null) return false;
            if (fixedText != null ? !fixedText.equals(inputBean.fixedText) : inputBean.fixedText != null)
                return false;
            return text != null ? text.equals(inputBean.text) : inputBean.text == null;
        }

        @Override
        public int hashCode() {
            int result = hint != null ? hint.hashCode() : 0;
            result = 31 * result + (fixedText != null ? fixedText.hashCode() : 0);
            result = 31 * result + (text != null ? text.hashCode() : 0);
            return result;
        }

        public InputBean setHint(CharSequence hint) {
            this.hint = hint;
            return this;
        }

        public InputBean setFixedText(CharSequence fixedText) {
            this.fixedText = fixedText;
            return this;
        }

        public InputBean setText(CharSequence text) {
            this.text = text;
            return this;
        }

        public CharSequence getHint() {
            return hint;
        }

        public CharSequence getFixedText() {
            return fixedText;
        }

        public CharSequence getText() {
            return text;
        }
    }

}


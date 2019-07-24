package com.xq.fasterdialog.bean;

import android.text.InputType;

public class InputBean{

    private CharSequence hint;
    private CharSequence text;
    private CharSequence fixedText;
    private int maxLength;
    private int inputType = InputType.TYPE_CLASS_TEXT;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InputBean inputBean = (InputBean) o;

        if (maxLength != inputBean.maxLength) return false;
        if (inputType != inputBean.inputType) return false;
        if (hint != null ? !hint.equals(inputBean.hint) : inputBean.hint != null) return false;
        if (text != null ? !text.equals(inputBean.text) : inputBean.text != null) return false;
        return fixedText != null ? fixedText.equals(inputBean.fixedText) : inputBean.fixedText == null;
    }

    @Override
    public int hashCode() {
        int result = hint != null ? hint.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (fixedText != null ? fixedText.hashCode() : 0);
        result = 31 * result + maxLength;
        result = 31 * result + inputType;
        return result;
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


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


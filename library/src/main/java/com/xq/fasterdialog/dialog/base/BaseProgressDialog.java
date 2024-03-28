package com.xq.fasterdialog.dialog.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public abstract class BaseProgressDialog<T extends BaseProgressDialog<?>> extends BaseSimpleDialog<T> {

    protected ProgressBar progressBar;

    protected float progress;

    private boolean indeterminate = true;

    public BaseProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = getView(getContext().getResources().getIdentifier("progressBar", "id", getContext().getPackageName()));

        //设置ProgressBar最大进度值
        if (progressBar != null)    progressBar.setMax(progressAccuracy);

        setIndeterminate(indeterminate);

        setProgress(progress);
    }

    @Override
    protected void onAutoDismissProgressChanged(float progress) {
        super.onAutoDismissProgressChanged(progress);

        setProgress(progress);
    }

    public T setProgress(float progress) {
        this.progress = progress;
        if (progressBar != null && progress >= 0) progressBar.setProgress((int) (progress*progressAccuracy));
        return (T) this;
    }

    public T setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
        if (progressBar != null) progressBar.setIndeterminate(indeterminate);
        return (T) this;
    }

    public float getProgress() {
        return progress;
    }
}

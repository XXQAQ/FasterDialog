package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

public abstract class BaseProgressDialog<T extends BaseProgressDialog> extends BaseSimpleDialog<T> {

    protected ProgressBar progressBar;

    protected int progress;

    public BaseProgressDialog(@NonNull Context context) {
        super(context);
    }

    public BaseProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = findViewById(getContext().getResources().getIdentifier("progressBar", "id", getContext().getPackageName()));

        if (progressBar != null && progress >= 0) progressBar.setProgress(progress);
    }

    @Override
    protected void onAutoDismissProgressChanged(int progress) {
        super.onAutoDismissProgressChanged(progress);

        setProgress(progress);
    }

    public T setProgress(int progress) {
        this.progress = progress;
        if (progressBar != null && progress >= 0) progressBar.setProgress(progress);
        return (T) this;
    }

    public int getProgress() {
        return progress;
    }
}

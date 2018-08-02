package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class BaseProgressDialog<T extends BaseProgressDialog> extends BaseSimpleDialog<T> {

    protected ProgressBar progressView;
    protected TextView pgDescriptView;

    protected int progress;

    public BaseProgressDialog(@NonNull Context context) {
        super(context);
    }

    public BaseProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressView = findViewById(context.getResources().getIdentifier("progressView", "id", context.getPackageName()));
        pgDescriptView = findViewById(context.getResources().getIdentifier("pgDescriptView", "id", context.getPackageName()));

        setProgressToView(progressView,progress);
    }

    @Override
    protected void onAutoDismissProgressChanged(int progress) {
        super.onAutoDismissProgressChanged(progress);

        setProgress(progress);
    }

    public T setProgress(int progress) {
        this.progress = progress;
        setProgressToView(progressView,progress);
        return (T) this;
    }

    public T setProgress(int progress,CharSequence pgDescript){
        setProgress(progress);
        setTextToView(pgDescriptView,pgDescript);
        return (T) this;
    }

    public int getProgress() {
        return progress;
    }
}

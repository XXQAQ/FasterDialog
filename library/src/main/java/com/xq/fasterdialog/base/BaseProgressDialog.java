package com.xq.fasterdialog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressView = findViewById(getContext().getResources().getIdentifier("progressView", "id", getContext().getPackageName()));
        pgDescriptView = findViewById(getContext().getResources().getIdentifier("pgDescriptView", "id", getContext().getPackageName()));

        if (progressView != null && progress >= 0)
            progressView.setProgress(progress);
    }

    @Override
    protected void onAutoDismissProgressChanged(int progress) {
        super.onAutoDismissProgressChanged(progress);

        setProgress(progress);
    }

    public T setProgress(int progress) {
        this.progress = progress;
        if (progressView != null && progress >= 0)
            progressView.setProgress(progress);
        return (T) this;
    }

    public T setProgress(int progress,CharSequence pgDescript){
        setProgress(progress);
        setTextToView(pgDescriptView,pgDescript, View.GONE);
        return (T) this;
    }

    public int getProgress() {
        return progress;
    }
}

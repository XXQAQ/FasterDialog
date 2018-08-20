package com.xq.fasterdialog.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xq.fasterdialog.DialogImageLoder;
import com.xq.fasterdialog.FasterDialogInterface;
import com.xq.fasterdialog.R;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseDialog<T extends BaseDialog> extends Dialog {

    protected Context context;

    //根布局
    protected View rootView;

    //自定义属性
    protected int layoutId;
    protected int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
    protected int width = WindowManager.LayoutParams.WRAP_CONTENT;
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;
    protected int x;
    protected int y;
    protected int animatStyle;
    protected int autoDismissTime;
    protected DialogImageLoder dialogImageLoder;
    protected Object tag;

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context,themeResId);
        this.context = context;
        init();
    }

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.BaseDialog);
    }

    @Deprecated
    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        this(context);
    }

    //重写此方法完成初始化工作
    protected void init() {

    }

    //AutoDismiss进度改变时的回调
    protected void onAutoDismissProgressChanged(int progress){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        if (rootView == null)
            rootView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutId,null);
        window.setContentView(rootView);

        //强制设置弹窗大小
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        lp.height = height;
        lp.x= x;
        lp.y= y;
        window.setAttributes(lp);

        //设置弹窗位置
        window.setGravity(gravity);

        if (animatStyle != 0)
            window.setWindowAnimations(animatStyle);
    }



    //以下重写Dialog方法
    @Override
    public void show() {
        if (((Activity)context).isFinishing())
            return;

        super.show();

        if (autoDismissTime > 0)
            autoDismiss();
    }

    @Override
    public void dismiss() {
        if (((Activity)context).isFinishing())
            return;

        if (autoDismissTime > 0 && task != null)
            task.cancel(true);

        super.dismiss();
    }

    @Override
    public <T_VIEW extends View> T_VIEW findViewById(int id) {
        return rootView.findViewById(id);
    }



    //所有set
    //设置Dialog从底部弹出
    public T setPopupFromBottom(){
        setMatchWidth();
        setAnimatStyle(R.style.Animation_Bottom);
        setBottom();
        return (T) this;
    }

    //设置Dialog从顶部弹出
    public T setPopupFromTop(){
        setMatchWidth();
        setAnimatStyle(R.style.Animation_Top);
        setTop();
        return (T) this;
    }

    public T setWidth(int width) {
        this.width = width;
        return (T) this;
    }

    public T setHeight(int height) {
        this.height = height;
        return (T) this;
    }

    public T setPercentWidth(float percent) {
        this.width = (int) (percent * ScreenUtils.getScreenW(context));
        return (T) this;
    }

    public T setPercentHeight(float percent) {
        this.height = (int) (percent * ScreenUtils.getScreenH(context));
        return (T) this;
    }

    public T setWrapWidth() {
        this.width = WindowManager.LayoutParams.WRAP_CONTENT;
        return (T) this;
    }

    public T setMatchWidth() {
        this.width = WindowManager.LayoutParams.MATCH_PARENT;
        return (T) this;
    }

    public T setWrapHeight() {
        this.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return (T) this;
    }

    public T setMatchHeight() {
        this.height = WindowManager.LayoutParams.MATCH_PARENT;
        return (T) this;
    }

    public T setX(int x) {
        this.x = x;
        return (T) this;
    }

    public T setY(int y) {
        this.y = y;
        return (T) this;
    }

    public T setCenter() {
        this.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        return (T) this;
    }

    public T setBottom() {
        this.gravity = Gravity.BOTTOM;
        return (T) this;
    }

    public T setTop() {
        this.gravity = Gravity.TOP;
        return (T) this;
    }

    public T setCustomView(int layoutId){
        this.layoutId = layoutId;
        return (T) this;
    }

    public T setCustomView(View view){
        this.rootView = view;
        return (T) this;
    }

    public T setDialogImageLoder(DialogImageLoder dialogImageLoder) {
        this.dialogImageLoder = dialogImageLoder;
        return (T) this;
    }

    public T setTag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T setAnimatStyle(int animatStyle) {
        this.animatStyle = animatStyle;
        return (T) this;
    }

    public T setAutoDismissTime(int autoDismissTime) {
        this.autoDismissTime = autoDismissTime;
        return (T) this;
    }

    public T setCancele(boolean cancel) {
        super.setCancelable(cancel);
        return (T) this;
    }

    public T setCancelOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        return (T) this;
    }

    public T setCancelMsg(@Nullable Message msg) {
        super.setCancelMessage(msg);
        return (T) this;
    }

    public T setDismissMsg(@Nullable Message msg) {
        super.setDismissMessage(msg);
        return (T) this;
    }

    public T setCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(listener);
        return (T) this;
    }

    private List<OnDismissListener> list_dismissListener = new LinkedList<>();
    public T addDismissListener(@Nullable OnDismissListener listener) {
        list_dismissListener.add(listener);
        super.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (OnDismissListener l : list_dismissListener)
                    l.onDismiss(dialog);
            }
        });
        return (T) this;
    }

    private List<OnShowListener> list_showListener = new LinkedList<>();
    public T addShowListener(@Nullable OnShowListener listener) {
        list_showListener.add(listener);
        super.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                for(OnShowListener l : list_showListener)
                    l.onShow(dialog);
            }
        });
        return (T) this;
    }



    //所有get
    //如果在设置layoutId后没有show出来就调用此方法，那么getRootView将返回null
    public View getCustomView() {
        return rootView;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAutoDismissTime() {
        return autoDismissTime;
    }

    public Object getTag() {
        return tag;
    }



    //私有方法
    protected void setTextToView(TextView view, CharSequence text){
        if (view == null)
            return;
        view.setText(text);
    }

    protected void setTextToView(TextView view, CharSequence text,int visibilityIfNot){
        if (view == null)
            return;

        if (TextUtils.isEmpty(text))
            view.setVisibility(visibilityIfNot);
        else
        {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void setImageResourceToView(ImageView view, int id){
        if (view == null)
            return;
        if (id != 0)
            view.setImageResource(id);
    }

    protected void setImageResourceToView(ImageView view, int id,int visibilityIfNot){
        if (view == null)
            return;

        if (id == 0)
            view.setVisibility(visibilityIfNot);
        else
        {
            view.setImageResource(id);
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void setImageUrlToView(final ImageView view, final String url){
        if (view == null)
            return;
        if (!TextUtils.isEmpty(url))
            if (dialogImageLoder == null)
                FasterDialogInterface.getImageLoaderd().loadImage(context,view,url);
            else
                dialogImageLoder.loadImage(context,view,url);
    }

    protected void setImageUrlToView(final ImageView view, final String url,int visibilityIfNot){
        if (view == null)
            return;

        if (TextUtils.isEmpty(url))
            view.setVisibility(visibilityIfNot);
        else
        {
            if (dialogImageLoder == null)
                FasterDialogInterface.getImageLoaderd().loadImage(context,view,url);
            else
                dialogImageLoder.loadImage(context,view,url);
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void setProgressToView(ProgressBar view, int progress){
        if (view == null)
            return;

        if (progress >=0 )
            view.setProgress(progress);
    }

    protected void bindDialogClickListenerWithView(View view, final OnDialogClickListener listener, final boolean isDismiss){
        if (view == null)
            return;

        if (listener != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(BaseDialog.this);
                    if (isDismiss)
                        dismiss();
                }
            });
    }

    protected AsyncTask task;
    protected void autoDismiss() {

        task = new AsyncTask<Object,Float,Void>(){

            @Override
            protected Void doInBackground(Object... objects) {
                int a = autoDismissTime/100;
                for (int i=a;i<autoDismissTime;i=i+a)
                {
                    publishProgress(i/(float)autoDismissTime);
                    try {
                        Thread.sleep(a);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(isCancelled())
                    return;
                dismiss();
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                if(isCancelled())
                    return;
                if (values[0]<=1)
                    onAutoDismissProgressChanged((int) (values[0]*100));
            }
        };
        task.execute();
    }



    //以下方法不建议使用了
    @Override
    @Deprecated
    public void setContentView(int layoutResID) throws IllegalAccessError {
        throw new IllegalAccessError(
                "setContentView() is not supported");
    }

    @Override
    @Deprecated
    public void setContentView(View view) throws IllegalAccessError {
        throw new IllegalAccessError(
                "setContentView() is not supported");
    }

    @Override
    @Deprecated
    public void setContentView(View view, @Nullable ViewGroup.LayoutParams params)
            throws IllegalAccessError {
        throw new IllegalAccessError(
                "setContentView() is not supported");
    }

    @Deprecated
    @Override
    public void setTitle(int titleId) {
        throw new IllegalAccessError(
                "setTitle() is not supported");
    }

    @Deprecated
    @Override
    public void setTitle(@Nullable CharSequence title) {
        throw new IllegalAccessError(
                "setTitle() is not supported");
    }

    @Deprecated
    @Override
    public void setCancelable(boolean flag) {
        throw new IllegalAccessError(
                "setCancelable() is not supported");
    }

    @Deprecated
    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        throw new IllegalAccessError(
                "setCanceledOnTouchOutside() is not supported");
    }

    @Deprecated
    @Override
    public void setCancelMessage(@Nullable Message msg) {
        throw new IllegalAccessError(
                "setCancelMessage() is not supported");
    }

    @Deprecated
    @Override
    public void setDismissMessage(@Nullable Message msg) {
        throw new IllegalAccessError(
                "setDismissMessage() is not supported");
    }

    @Deprecated
    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        throw new IllegalAccessError(
                "setOnCancelListener() is not supported");
    }

    @Deprecated
    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        throw new IllegalAccessError(
                "setOnDismissListener() is not supported");
    }

    @Deprecated
    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        throw new IllegalAccessError(
                "setOnShowListener() is not supported");
    }

    @Deprecated
    @Override
    public void setOnKeyListener(@Nullable OnKeyListener onKeyListener) {
        throw new IllegalAccessError(
                "setOnKeyListener() is not supported");
    }

    protected static class ScreenUtils {

        public static int dip2px(Context c, float dpValue) {
            final float scale = c.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        public static int dip2sp(Context c, float dpValue) {
            return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, c.getResources().getDisplayMetrics()));
        }

        public static int px2dip(Context c, float pxValue) {
            final float scale = c.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }


        public static int px2sp(Context c, float pxValue) {
            float fontScale = c.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }

        public static int sp2px(Context c, float spValue) {
            float fontScale = c.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }

        public static int sp2dip(Context c, float spValue) {
            return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, c.getResources().getDisplayMetrics()));
        }

        public static int getScreenW(Context c) {
            return c.getResources().getDisplayMetrics().widthPixels;
        }

        public static int getScreenH(Context c) {
            return c.getResources().getDisplayMetrics().heightPixels;
        }

        public static int getStatusBarH(Context context) {
            Class<?> c;
            Object obj;
            Field field;
            int statusBarHeight = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return statusBarHeight;
        }

        public static int getNavigationBarH(Context c) {
            Resources resources = c.getResources();
            int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            return resources.getDimensionPixelOffset(identifier);
        }
    }
}

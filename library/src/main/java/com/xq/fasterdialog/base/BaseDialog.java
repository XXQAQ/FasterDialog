package com.xq.fasterdialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.xq.fasterdialog.FasterDialog;
import com.xq.fasterdialog.R;
import com.xq.fasterdialog.util.DialogImageLoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseDialog<T extends BaseDialog>{

    public static int STYLE_BASEDIALOG = R.style.BaseDialog;
    public static int STYLE_TRANSLUCENTDIALOG = R.style.TranslucentDialog;
    public static int STYLE_ALERTDIALOG = R.style.AlertDialog;

    //Dialog
    protected Dialog dialog;

    //上下文
    protected Context context;

    //根布局
    protected View rootView;

    //自定义属性
    protected int layoutId;
    protected int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
    protected int width = WindowManager.LayoutParams.WRAP_CONTENT;
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;
    protected int maxWidth;
    protected int maxHeight;
    protected int x;
    protected int y;
    protected int animatStyle;
    protected int autoDismissTime;
    protected Object tag;
    protected DialogImageLoder dialogImageLoder;

    public BaseDialog(@NonNull Context context) {
        this(context,STYLE_BASEDIALOG);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super();
        this.context = context;
        dialog = new Dialog(context,themeResId);
    }

    public void onCreate(Bundle savedInstanceState) {

        Window window = getDialog().getWindow();

        if (rootView == null)
            rootView = getDialog().getLayoutInflater().inflate(layoutId,null);
        window.setContentView(rootView);

        //设置弹窗位置
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x= x;
        lp.y= y;
        window.setGravity(gravity);

        if (animatStyle != 0)
            window.setWindowAnimations(animatStyle);
    }

    public void onStart() {
        measure();
    }

    public void onDestory(){

    }

    //如果指定的ViewGroup下所有子控件均未不可见，则直接隐藏该ViewGroup
    protected void goneEmptyLayout(ViewGroup viewGroup){
        boolean isGone =true;
        for (int i = 0; i < viewGroup.getChildCount(); i++)
        {
            if (viewGroup.getChildAt(i).getVisibility() == View.VISIBLE)
                break;
            if (i == viewGroup.getChildCount()-1 && isGone)
                viewGroup.setVisibility(View.GONE);
        }
    }

    //指定控件具体类型，获取Container容器下所有该类型的控件
    protected List getAllSomeView(View container,Class someView) {
        List list = new ArrayList<>();
        if (container instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) container;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View view = viewGroup.getChildAt(i);
                if (someView.isAssignableFrom(view.getClass()))
                    list.add(view);
                //再次 调用本身（递归）
                list.addAll(getAllSomeView(view,someView));
            }
        }
        return list;
    }

    //当Dialog需要动态调整宽高的时候，请调用此方法
    protected void measure() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        rootView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        if (maxHeight > 0 && rootView.getMeasuredHeight() > maxHeight)
            lp.height = maxHeight;
        else
            lp.height = height;
        if (maxWidth > 0 && rootView.getMeasuredWidth() > maxWidth)
            lp.width = maxWidth;
        else
            lp.width = width;
    }

    public void show() {
        if (((Activity)getContext()).isFinishing())
            return;

        onCreate(null);
        onStart();
        dialog.show();

        if (autoDismissTime > 0)
            autoDismiss();
    }

    public void dismiss() {
        if (((Activity)getContext()).isFinishing())
            return;

        if (autoDismissTime > 0 && task != null)
            task.cancel(true);

        onDestory();

        dialog.dismiss();
    }

    public <T_VIEW extends View> T_VIEW findViewById(int id) {
        return rootView.findViewById(id);
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

    //AutoDismiss进度改变时的回调
    protected void onAutoDismissProgressChanged(int progress){

    }



    //所有set
    public T setWidth(int width) {
        this.width = width;
        return (T) this;
    }

    public T setHeight(int height) {
        this.height = height;
        return (T) this;
    }

    public T setWidthPercent(float percent) {
        this.width = (int) (percent * ScreenUtils.getScreenWidth(getContext()));
        return (T) this;
    }

    public T setHeightPercent(float percent) {
        this.height = (int) (percent * ScreenUtils.getScreenHeight(getContext()));
        return (T) this;
    }

    public T setWidthWrap() {
        this.width = WindowManager.LayoutParams.WRAP_CONTENT;
        return (T) this;
    }

    public T setHeightWrap() {
        this.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return (T) this;
    }

    public T setWidthMatch() {
        this.width = WindowManager.LayoutParams.MATCH_PARENT;
        return (T) this;
    }

    public T setHeightMatch() {
        this.height = WindowManager.LayoutParams.MATCH_PARENT;
        return (T) this;
    }

    public T setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return (T) this;
    }

    public T setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return (T) this;
    }

    public T setMaxWidthPercent(float percent) {
        this.maxWidth = (int) (percent * ScreenUtils.getScreenWidth(getContext()));
        return (T) this;
    }

    public T setMaxHeightPercent(float percent) {
        this.maxHeight = (int) (percent * ScreenUtils.getScreenHeight(getContext()));
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

    public T setPopupFromBottom(){
        setWidthMatch();
        setAnimatStyle(R.style.Animation_Bottom);
        this.gravity = Gravity.BOTTOM;
        return (T) this;
    }

    public T setPopupFromTop(){
        setWidthMatch();
        setAnimatStyle(R.style.Animation_Top);
        this.gravity = Gravity.TOP;
        return (T) this;
    }

    public T setCustomView(int layoutId){
        this.layoutId = layoutId;
        return (T) this;
    }

    public T setDialogImageLoder(DialogImageLoder dialogImageLoder) {
        this.dialogImageLoder = dialogImageLoder;
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

    public T setCancelable(boolean cancelable) {
        getDialog().setCancelable(cancelable);
        return (T) this;
    }

    public T setCanceledOnTouchOutside(boolean cancel) {
        getDialog().setCanceledOnTouchOutside(cancel);
        return (T) this;
    }

    public T setTag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    private List<OnDialogCancelListener> list_cancelListener = new LinkedList<>();
    public T addOnCancelListener(@Nullable final OnDialogCancelListener listener) {
        list_cancelListener.add(listener);
        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                for (OnDialogCancelListener l : list_cancelListener)
                    l.onCancel(BaseDialog.this);
            }
        });
        return (T) this;
    }

    private List<OnDialogDismissListener> list_dismissListener = new LinkedList<>();
    public T addOnDismissListener(@Nullable OnDialogDismissListener listener) {
        list_dismissListener.add(listener);
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (OnDialogDismissListener l : list_dismissListener)
                    l.onDismiss(BaseDialog.this);
            }
        });
        return (T) this;
    }

    private List<OnDialogShowListener> list_showListener = new LinkedList<>();
    public T addOnShowListener(@Nullable OnDialogShowListener listener) {
        list_showListener.add(listener);
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                for(OnDialogShowListener l : list_showListener)
                    l.onShow(BaseDialog.this);
            }
        });
        return (T) this;
    }



    //所有get
    public Dialog getDialog() {
        return dialog;
    }

    public Context getContext() {
        return context;
    }

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



    //便捷控件设置方法(包含了对父控件的处理)
    protected void setTextToView(TextView view, CharSequence text,int visibilityIfNot){
        if (view == null)
            return;

        if (TextUtils.isEmpty(text))
        {
            view.setVisibility(visibilityIfNot);
            goneEmptyLayout((ViewGroup) view.getParent());
        }
        else
        {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
            ((View) view.getParent()).setVisibility(View.VISIBLE);
        }
    }

    protected void setImageResourceToView(ImageView view, int id,int visibilityIfNot){
        if (view == null)
            return;

        if (id == 0)
        {
            view.setVisibility(visibilityIfNot);
            goneEmptyLayout((ViewGroup) view.getParent());
        }
        else
        {
            view.setImageResource(id);
            view.setVisibility(View.VISIBLE);
            ((View) view.getParent()).setVisibility(View.VISIBLE);
        }
    }

    protected void setImageUrlToView(final ImageView view, final String url,int visibilityIfNot){
        if (view == null)
            return;

        if (TextUtils.isEmpty(url))
        {
            view.setVisibility(visibilityIfNot);
            goneEmptyLayout((ViewGroup) view.getParent());
        }
        else
        {
            if (dialogImageLoder == null)
                FasterDialog.getImageLoaderd().loadImage(getContext(),view,url);
            else
                dialogImageLoder.loadImage(getContext(),view,url);
            view.setVisibility(View.VISIBLE);
            ((View) view.getParent()).setVisibility(View.VISIBLE);
        }
    }

    protected void bindDialogClickListenerWithView(View view, final OnDialogClickListener listener, final boolean isAutoDismiss){
        if (view == null)
            return;

        if (listener != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(BaseDialog.this);
                    if (isAutoDismiss) dismiss();
                }
            });
    }



    //内部工具类或者监听
    public static interface OnDialogClickListener {
        public void onClick(BaseDialog dialog);
    }

    public static interface OnDialogShowListener {
        public void onShow(BaseDialog dialog);
    }

    public static interface OnDialogDismissListener {
        public void onDismiss(BaseDialog dialog);
    }

    public static interface OnDialogCancelListener {
        public void onCancel(BaseDialog dialog);
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

        public static int getScreenWidth(Context c) {
            return c.getResources().getDisplayMetrics().widthPixels;
        }

        public static int getScreenHeight(Context c) {
            return c.getResources().getDisplayMetrics().heightPixels;
        }
    }
}

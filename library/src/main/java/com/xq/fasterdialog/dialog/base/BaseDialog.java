package com.xq.fasterdialog.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.xq.fasterdialog.FasterDialog;
import com.xq.fasterdialog.R;
import com.xq.fasterdialog.util.ImageLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseDialog<T extends BaseDialog> implements DialogInterface{

    //Dialog样式
    public static int STYLE_BASEDIALOG = R.style.BaseDialog;    //无任何特性,Dialog基础样式
    public static int STYLE_TRANSLUCENTDIALOG = R.style.TranslucentDialog;  //在上基础上，弹出时附带阴影效果
    public static int STYLE_ALERTDIALOG = R.style.AlertDialog;  //参照AlertDialog效果，Dialog宽度固定且附带阴影效果

    //弹出动画（包含进入与进出）
    public static int ANIMAT_ALPHA = R.style.Animat_Alpha;
    public static int ANIMAT_BOTTOM = R.style.Animat_Bottom;
    public static int ANIMAT_TOP = R.style.Animat_Top;
    public static int ANIMAT_LEFT = R.style.Animat_Left;
    public static int ANIMAT_RIGHT = R.style.Animat_Right;

    protected static int PROGRESS_ACCURACY = 3600;  //进度值精度(值越大精度越细，但是也不可以过大)

    //Dialog
    private Dialog dialog;

    //上下文
    private Context context;

    //根布局
    protected View rootView;

    //自定义属性
    protected int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
    protected int width = WindowManager.LayoutParams.WRAP_CONTENT;
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;
    protected int maxWidth;
    protected int maxHeight;
    protected int x;
    protected int y;
    protected float alpha = 1.0f;
    protected float elevation;
    protected int autoDismissTime;
    protected Object tag;
    protected ImageLoader imageLoader;
    //AttchView
    protected View attchView;
    //需要在初始化的时候传值给Dialog设置的属性
    protected int style = STYLE_BASEDIALOG;
    protected int animatStyle = ANIMAT_ALPHA;
    protected int layoutId;
    protected boolean cancelable = true;
    protected boolean cancelableOutside = true;
    protected List<OnDialogCancelListener> list_cancelListener = new LinkedList<>();
    protected List<OnDialogDismissListener> list_dismissListener = new LinkedList<>();
    protected List<OnDialogShowListener> list_showListener = new LinkedList<>();


    public BaseDialog(@NonNull Context context) {
        this.context = context;
    }


    private boolean isCreated = false;
    public void onCreate(Bundle savedInstanceState) {

        isCreated = true;

        if (rootView == null) rootView = getDialog().getLayoutInflater().inflate(layoutId,null);
        CardView cardView = new CardView(getContext());
        cardView.setCardElevation(elevation);
        cardView.setUseCompatPadding(true);
        cardView.addView(rootView);
        getDialog().getWindow().setContentView(cardView);

//        if (rootView == null) rootView = getDialog().getLayoutInflater().inflate(layoutId,null);
//        getDialog().getWindow().setContentView(rootView);

        getDialog().getWindow().setWindowAnimations(animatStyle);
        getDialog().getWindow().getAttributes().alpha = alpha;
        getDialog().setCancelable(cancelable);
        getDialog().setCanceledOnTouchOutside(cancelableOutside);
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                for(OnDialogShowListener l : list_showListener)
                    l.onShow(BaseDialog.this);
            }
        });
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (OnDialogDismissListener l : list_dismissListener)
                    l.onDismiss(BaseDialog.this);
            }
        });
        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                for (OnDialogCancelListener l : list_cancelListener)
                    l.onCancel(BaseDialog.this);
            }
        });
    }

    public void onStart() {

        measure();

        location();

    }

    public void onStop(){

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

    //当Dialog需要调整弹出位置的时候，请调用此方法
    protected void location(){
        Window window = getDialog().getWindow();
        if (attchView != null)
        {
            //注意这里获取的是屏幕的绝对坐标，其包含了状态栏的高度
            int[] location = new int[2] ;attchView.getLocationOnScreen(location);
            //因为dialog总是在状态栏下方，所以需要减去状态栏的高度
            location[1] = location[1] - ScreenUtils.getStatusBarHeight();
            rootView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            if (gravity == (Gravity.BOTTOM|Gravity.RIGHT))
            {
                location[0] = location[0] + attchView.getMeasuredWidth();
                location[1] = location[1] + attchView.getMeasuredHeight();
            }
            else    if (gravity == Gravity.BOTTOM)
            {
                location[0] = location[0]+((attchView.getMeasuredWidth()-rootView.getMeasuredWidth())/2);
                location[1] = location[1] + attchView.getMeasuredHeight();
            }
            else    if (gravity == Gravity.TOP)
            {
                location[0] = location[0]+((attchView.getMeasuredWidth()-rootView.getMeasuredWidth())/2);
                location[1] = location[1] - attchView.getMeasuredHeight();
            }
            else    if (gravity == Gravity.LEFT)
            {
                location[0] = location[0] - attchView.getMeasuredWidth();
                location[1] = location[1] +((attchView.getMeasuredHeight()-rootView.getMeasuredHeight())/2);
            }
            else    if (gravity == Gravity.RIGHT)
            {
                location[0] = location[0] + attchView.getMeasuredWidth();
                location[1] = location[1] +((attchView.getMeasuredHeight()-rootView.getMeasuredHeight())/2);
            }
            window.setGravity(Gravity.TOP|Gravity.START);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.x = location[0];
            lp.y = location[1];
        }
        else
        {
            int[] location = new int[]{x,y};
            window.setGravity(gravity);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.x = location[0];
            lp.y = location[1];
        }
    }

    public void show() {
        if (((Activity)getContext()).isFinishing()) return;

        if (!isCreated)
            dialog = new Dialog(getContext(),style){
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    BaseDialog.this.onCreate(savedInstanceState);
                }

                @Override
                protected void onStart() {
                    super.onStart();
                    BaseDialog.this.onStart();
                }

                @Override
                protected void onStop() {
                    super.onStop();
                    BaseDialog.this.onStop();
                }
            };

        getDialog().show();

        if (autoDismissTime > 0) autoDismiss();
    }

    @Override
    public void cancel() {
        getDialog().cancel();
    }

    @Override
    public void dismiss() {
        if (((Activity)getContext()).isFinishing()) return;

        if (autoDismissTime > 0 && timer != null) timer.cancel();

        getDialog().dismiss();

    }

    public <T_VIEW extends View> T_VIEW findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected CountDownTimer timer;
    protected void autoDismiss() {

        new CountDownTimer(autoDismissTime, autoDismissTime/PROGRESS_ACCURACY) {
            @Override
            public void onFinish() {
                dismiss();
            }
            @Override
            public void onTick(long millisUntilFinished) {
                onAutoDismissProgressChanged((autoDismissTime-millisUntilFinished)/(float)autoDismissTime);
            }
        }.start();
    }

    //AutoDismiss进度改变时的回调
    protected void onAutoDismissProgressChanged(float progress){

    }



    //所有set
    public T setStyle(int style) {
        this.style = style;
        return (T) this;
    }

    public T setAnimat(int animatStyle) {
        this.animatStyle = animatStyle;
        return (T) this;
    }

    public T setCustomView(int layoutId){
        this.layoutId = layoutId;
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

    public T setAlpha(float alpha) {
        this.alpha = alpha;
        return (T) this;
    }

    public T setElevation(float elevation) {
        this.elevation = elevation;
        return (T) this;
    }

    public T setPopupFromScreen(int gravity){
        setGravity(gravity);
        if (gravity == Gravity.BOTTOM)
            setAnimat(ANIMAT_BOTTOM);
        else    if (gravity == Gravity.TOP)
            setAnimat(ANIMAT_TOP);
        else    if (gravity == Gravity.LEFT)
            setAnimat(ANIMAT_LEFT);
        else    if (gravity == Gravity.RIGHT)
            setAnimat(ANIMAT_RIGHT);
        return (T) this;
    }

    public T setPopupFromView(View view){
        setPopupFromView(view,Gravity.BOTTOM|Gravity.RIGHT);
        return (T) this;
    }

    public T setPopupFromView(View view,int gravity){
        this.attchView = view;
        setGravity(gravity);
        if (gravity == (Gravity.BOTTOM|Gravity.RIGHT))
            ;
        else    if (gravity == Gravity.BOTTOM)
            setAnimat(ANIMAT_TOP);
        else    if (gravity == Gravity.TOP)
            setAnimat(ANIMAT_BOTTOM);
        else    if (gravity == Gravity.LEFT)
            setAnimat(ANIMAT_RIGHT);
        else    if (gravity == Gravity.RIGHT)
            setAnimat(ANIMAT_LEFT);
        return (T) this;
    }

    public T setPopupFromViewTouchLocation(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //注意这里获取的是屏幕的绝对坐标，其包含了状态栏的高度
                //因为dialog总是在状态栏下方，所以需要减去状态栏的高度
                setX((int) (event.getRawX()-ScreenUtils.getStatusBarHeight()));
                setY((int) (event.getRawY()-ScreenUtils.getStatusBarHeight()));
                setGravity(Gravity.TOP|Gravity.START);
                return false;
            }
        });
        return (T) this;
    }

    public T setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        if (!cancelable)    setCanceledOnTouchOutside(false);
        return (T) this;
    }

    public T setCanceledOnTouchOutside(boolean cancelableOutside) {
        this.cancelableOutside = cancelableOutside;
        return (T) this;
    }

    public T addOnCancelListener(@Nullable final OnDialogCancelListener listener) {
        list_cancelListener.add(listener);
        return (T) this;
    }

    public T addOnDismissListener(@Nullable OnDialogDismissListener listener) {
        list_dismissListener.add(listener);
        return (T) this;
    }

    public T addOnShowListener(@Nullable OnDialogShowListener listener) {
        list_showListener.add(listener);
        return (T) this;
    }

    public T setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return (T) this;
    }

    public T setAutoDismissTime(int autoDismissTime) {
        this.autoDismissTime = autoDismissTime;
        return (T) this;
    }

    public T setTag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    //可以通过自定义布局的控件Id快速设置一些常用的控件属性
    public T setText(int id,CharSequence text){
        setTextToView((TextView) findViewById(id),text,View.VISIBLE);
        return (T) this;
    }

    public T setImage(int id,int imageRes){
        setImageResourceToView((ImageView) findViewById(id),imageRes,View.VISIBLE);
        return (T) this;
    }

    public T setImage(int id,String imageUrl){
        setImageUrlToView((ImageView) findViewById(id),imageUrl,View.VISIBLE);
        return (T) this;
    }

    public T setClickListener(int id, OnDialogClickListener listener){
        setClickListenerToView(findViewById(id),listener,true);
        return (T) this;
    }

    public T setClickListener(int id, OnDialogClickListener listener, boolean isAutoDismiss){
        setClickListenerToView(findViewById(id),listener,isAutoDismiss);
        return (T) this;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setGravity(int gravity) {
        this.gravity = gravity;
    }



    //所有get
    public View getCustomView() {
        return rootView;
    }

    public Object getTag() {
        return tag;
    }

    public boolean isShowing(){
        return getDialog().isShowing();
    }

    protected Dialog getDialog() {
        return dialog;
    }

    protected Context getContext() {
        return context;
    }



    //便捷控件设置方法(包含了对父控件的处理)
    protected void setTextToView(TextView view, CharSequence text,int visibilityIfNot){
        if (view == null)
            return;

        if (TextUtils.isEmpty(text))
        {
            view.setVisibility(visibilityIfNot);
            invisibleEmptyLayout((ViewGroup) view.getParent(),visibilityIfNot);
        }
        else
        {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
            visibleLayout((ViewGroup) view.getParent());
        }
    }

    protected void setImageResourceToView(ImageView view, int id,int visibilityIfNot){
        if (view == null)
            return;

        if (id == 0)
        {
            view.setVisibility(visibilityIfNot);
            invisibleEmptyLayout((ViewGroup) view.getParent(),visibilityIfNot);
        }
        else
        {
            view.setImageResource(id);
            view.setVisibility(View.VISIBLE);
            visibleLayout((ViewGroup) view.getParent());
        }
    }

    protected void setImageUrlToView(final ImageView view, final String url,int visibilityIfNot){
        if (view == null)
            return;

        if (TextUtils.isEmpty(url))
        {
            view.setVisibility(visibilityIfNot);
            invisibleEmptyLayout((ViewGroup) view.getParent(),visibilityIfNot);
        }
        else
        {
            if (imageLoader == null)
                FasterDialog.getImageLoader().loadImage(getContext(),view,url);
            else
                imageLoader.loadImage(getContext(),view,url);
            view.setVisibility(View.VISIBLE);
            visibleLayout((ViewGroup) view.getParent());
        }
    }

    protected void setClickListenerToView(View view, final OnDialogClickListener listener, final boolean isAutoDismiss){
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


    //如果指定的ViewGroup下的所有子控件均未不可见，则直接隐藏该ViewGroup
    protected void invisibleEmptyLayout(ViewGroup viewGroup,int visibilityIfNot){
        if (viewGroup.getParent() == null)  return;

        boolean isGone =true;
        for (int i = 0; i < viewGroup.getChildCount(); i++)
        {
            if (viewGroup.getChildAt(i).getVisibility() == View.VISIBLE)
                break;
            if (i == viewGroup.getChildCount()-1 && isGone)
            {
                viewGroup.setVisibility(View.GONE);
                invisibleEmptyLayout((ViewGroup) viewGroup.getParent(),visibilityIfNot);
            }
        }
    }

    //将指定的ViewGroup以及以上所有parent全部设置为可见
    protected void visibleLayout(ViewGroup viewGroup){
        if (viewGroup.getParent() == null)  return;

        if (viewGroup.getVisibility() != View.VISIBLE)
        {
            viewGroup.setVisibility(View.VISIBLE);
            visibleLayout((ViewGroup) viewGroup.getParent());
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
                //再次调用
                list.addAll(getAllSomeView(view,someView));
            }
        }
        return list;
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

        public static int getStatusBarHeight() {
            Resources resources = Resources.getSystem();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            return resources.getDimensionPixelSize(resourceId);
        }
    }
}

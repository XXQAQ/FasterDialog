package com.xq.fasterdialog.dialog.base;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xq.fasterdialog.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseDialog<T extends BaseDialog<?>> implements DialogInterface{

    //Dialog样式
    public static int STYLE_BASE = R.style.BaseDialog;    //无任何特性,Dialog基础样式
    public static int STYLE_TRANSLUCENT = R.style.TranslucentDialog;  //在上基础上，弹出时附带黑色背景效果
    public static int STYLE_ALERT = R.style.AlertDialog;  //参照AlertDialog效果，Dialog宽度固定且附带阴影效果

    //弹出动画（包含进入与进出）
    public static int ANIMATE_ALPHA = R.style.Animate_Alpha;
    public static int ANIMATE_BOTTOM = R.style.Animate_Bottom;
    public static int ANIMATE_TOP = R.style.Animate_Top;
    public static int ANIMATE_LEFT = R.style.Animate_Left;
    public static int ANIMATE_RIGHT = R.style.Animate_Right;

    //进度精度(值越大精度越细，但是也不可以过大)
    protected int progressAccuracy = 1000;

    //上下文
    private final Context context;

    //Dialog
    private Dialog dialog;

    //用户的自定义布局
    private View rootView;

    //自定义相关属性
    protected int layoutId;
    protected int style = STYLE_BASE;
    protected int animate = ANIMATE_ALPHA;

    protected int gravity = Gravity.CENTER;
    protected int x = 0;
    protected int y = 0;
    private int[] offset = new int[]{0,0};
    protected Integer width;
    protected Integer height;
    protected Float dimAmount;
    protected Float alpha;
    protected Integer autoDismissTime;
    protected Object tag;
    protected View attchView;
    protected boolean cancelable = true;
    protected boolean cancelableOutside = true;

    protected List<OnDialogCancelListener> list_cancelListener = new LinkedList<>();
    protected List<OnDialogDismissListener> list_dismissListener = new LinkedList<>();
    protected List<OnDialogShowListener> list_showListener = new LinkedList<>();


    public BaseDialog(@NonNull Context context) {
        this.context = getReallyActivityContext(context);
    }

    protected Activity getReallyActivityContext(Context context) {
        //兼容安卓5.0以下在View中获取Context并非真实Activity Context的问题
        while (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        throw new IllegalStateException("The Context is not an Activity.");
    }

    public void onCreate(Bundle savedInstanceState) {

        if (rootView == null) {
            InflateBean inflateBean = inflate(layoutId);
            rootView = inflateBean.view;
            if (width == null){
                width = inflateBean.width;
            }
            if (height == null){
                height = inflateBean.height;
            }
        }
        else {
            if (width == null){
                width = WRAP_CONTENT;
            }
            if (height == null){
                height = WRAP_CONTENT;
            }
        }

        getDialog().getWindow().setContentView(rootView);

        if (alpha != null) getDialog().getWindow().getAttributes().alpha = alpha;
        if (dimAmount != null) getDialog().getWindow().setDimAmount(dimAmount);
        getDialog().getWindow().setWindowAnimations(animate);
        getDialog().setCancelable(cancelable);
        getDialog().setCanceledOnTouchOutside(cancelableOutside);
        getDialog().setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                for(OnDialogShowListener l : list_showListener)
                    l.onShow(BaseDialog.this);
            }
        });
        getDialog().setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (OnDialogDismissListener l : list_dismissListener)
                    l.onDismiss(BaseDialog.this);
            }
        });
        getDialog().setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                for (OnDialogCancelListener l : list_cancelListener)
                    l.onCancel(BaseDialog.this);
            }
        });

    }

    public void onStart() {

        location();

    }

    public void onStop(){

    }

    //当Dialog需要调整弹出位置的时候，请调用此方法
    protected void location(){

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        if (attchView != null)
        {
            //注意这里获取的是屏幕的绝对坐标，其包含了状态栏的高度
            int[] location = new int[2] ;attchView.getLocationOnScreen(location);
            //因为dialog总是在状态栏下方，所以需要减去状态栏的高度
            location[1] = location[1] - (isStatusBarVisible()? getStatusBarHeight() : 0);

            rootView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

            int mWidth = lp.width > 0?lp.width:lp.width == MATCH_PARENT? getAppScreenWidth():rootView.getMeasuredWidth();
            int mHeight = lp.height > 0?lp.height:lp.height == MATCH_PARENT? getAppScreenHeight():rootView.getMeasuredHeight();
            int aWidth = attchView.getMeasuredWidth();
            int aHeight = attchView.getMeasuredHeight();

            if (gravity == (Gravity.BOTTOM|Gravity.RIGHT))
            {
                location[0] = location[0] + aWidth;
                location[1] = location[1] + aHeight;
            }
            else    if (gravity == Gravity.BOTTOM)
            {
                location[0] = location[0] + ((aWidth-mWidth)/2);
                location[1] = location[1] + aHeight;
            }
            else    if (gravity == Gravity.TOP)
            {
                location[0] = location[0] + ((aWidth-mWidth)/2);
                location[1] = location[1] - mHeight;
            }
            else    if (gravity == Gravity.LEFT)
            {
                location[0] = location[0] - mWidth;
                location[1] = location[1] + ((aHeight-mHeight)/2);
            }
            else    if (gravity == Gravity.RIGHT)
            {
                location[0] = location[0] + aWidth;
                location[1] = location[1] + ((aHeight-mHeight)/2);
            }
            window.setGravity(Gravity.TOP|Gravity.START);
            lp.x = location[0] + offset[0];
            lp.y = location[1] + offset[1];
        }
        else
        {
            int[] location = new int[]{x,y};
            window.setGravity(gravity);
            lp.x = location[0] + offset[0];
            lp.y = location[1] + offset[1];
        }
    }

    public void show() {
        if (((Activity)getContext()).isFinishing()) return;

        if (!isCreated) create();

        getDialog().show();

        if (autoDismissTime != null) autoDismiss();
    }

    private boolean isCreated = false;
    public T create(){
        if (isCreated){
            return (T) this;
        }
        //生命周期同步
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
        dialog.create();
        isCreated = true;
        return (T) this;
    }

    static class InflateBean{
        View view;
        int width;
        int height;
        public InflateBean(View view, int width, int height) {
            this.view = view;
            this.width = width;
            this.height = height;
        }
    }

    public InflateBean inflate(int layoutId) {
        FrameLayout tempLayout = new FrameLayout(getContext());
        View result = LayoutInflater.from(getContext()).inflate(layoutId, tempLayout, false);
        ViewGroup.LayoutParams tempParams = result.getLayoutParams();
        return new InflateBean(result,tempParams.width,tempParams.height);
    }

    @Override
    public void cancel() {
        if (getDialog() == null) return;

        getDialog().cancel();
    }

    @Override
    public void dismiss() {
        if (getDialog() == null || ((Activity)getContext()).isFinishing()) return;

        if (timer != null){
            timer.cancel();
            timer = null;
        }

        getDialog().dismiss();
    }

    protected SparseArray<View> array_view = new SparseArray<>();
    protected  <T_VIEW extends View> T_VIEW getView(int id) {
        View view = array_view.get(id);
        if (view == null)
        {
            view = findViewById(id);
            array_view.put(id,view);
        }
        return (T_VIEW) view;
    }

    @Deprecated
    protected  <T_VIEW extends View> T_VIEW findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected CountDownTimer timer;
    protected void autoDismiss() {
        if (timer == null){
            timer = new CountDownTimer(autoDismissTime, (long) ((float)autoDismissTime/(float)progressAccuracy)) {
                @Override
                public void onFinish() {
                    if (this != BaseDialog.this.timer){
                        return;
                    }
                    dismiss();
                }
                @Override
                public void onTick(long millisUntilFinished) {
                    if (this != BaseDialog.this.timer){
                        return;
                    }
                    onAutoDismissProgressChanged((autoDismissTime-millisUntilFinished)/(float)autoDismissTime);
                }
            }.start();
        }
    }

    //AutoDismiss进度改变时的回调
    protected void onAutoDismissProgressChanged(float progress){

    }



    //所有set
    public T setStyle(int style) {
        this.style = style;
        return (T) this;
    }

    public void setDimAmount(Float dimAmount) {
        this.dimAmount = dimAmount;
    }

    public T setAnimate(int animate) {
        this.animate = animate;
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

    public T setWidthMatch(){
        return setWidth(MATCH_PARENT);
    }

    public T setWidthWrap(){
        return setWidth(WRAP_CONTENT);
    }

    public T setWidth(int width) {
        this.width = width;
        return (T) this;
    }

    public T setHeightMatch(){
        return setHeight(MATCH_PARENT);
    }

    public T setHeightWrap(){
        return setHeight(WRAP_CONTENT);
    }

    public T setHeight(int height) {
        this.height = height;
        return (T) this;
    }

    public T setAlpha(float alpha) {
        this.alpha = alpha;
        return (T) this;
    }

    public T setPopupFromScreen(){
        return setPopupFromScreen(Gravity.CENTER);
    }

    public T setPopupFromScreen(int gravity){
        return setPopupFromScreen(gravity,new int[]{0,0});
    }

    public T setPopupFromScreen(int gravity, int[] offset){
        setGravity(gravity);
        setOffset(offset);
        if (gravity == Gravity.CENTER)
            setAnimate(ANIMATE_ALPHA);
        if (gravity == Gravity.BOTTOM)
            setAnimate(ANIMATE_BOTTOM);
        else    if (gravity == Gravity.TOP)
            setAnimate(ANIMATE_TOP);
        else    if (gravity == Gravity.LEFT)
            setAnimate(ANIMATE_LEFT);
        else    if (gravity == Gravity.RIGHT)
            setAnimate(ANIMATE_RIGHT);
        return (T) this;
    }

    public T setPopupFromView(View view){
        setPopupFromView(view,Gravity.BOTTOM);
        return (T) this;
    }

    public T setPopupFromView(View view, int gravity){
        return setPopupFromView(view,gravity,new int[]{0,0});
    }

    public T setPopupFromView(View view, int gravity, int[] offset){
        this.attchView = view;
        setGravity(gravity);
        setOffset(offset);
//        if (gravity == (Gravity.BOTTOM|Gravity.RIGHT))
//            ;
        if (gravity == Gravity.BOTTOM)
            setAnimate(ANIMATE_TOP);
        else    if (gravity == Gravity.TOP)
            setAnimate(ANIMATE_BOTTOM);
        else    if (gravity == Gravity.LEFT)
            setAnimate(ANIMATE_RIGHT);
        else    if (gravity == Gravity.RIGHT)
            setAnimate(ANIMATE_LEFT);
        return (T) this;
    }

    public T setOffset(int[] offset) {
        this.offset = offset;
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

    public T setAutoDismissTime(int autoDismissTime) {
        this.autoDismissTime = autoDismissTime;
        return (T) this;
    }

    public T setTag(Object tag) {
        this.tag = tag;
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

    public T setGravity(int gravity) {
        this.gravity = gravity;
        return (T) this;
    }

    //以下通过自定义布局的控件Id快速设置一些常用的控件属性
    protected void setText(TextView view, CharSequence text,int visibilityIfNot){
        if (view == null) return;

        if (text == null)
        {
            invisibleViewWithAllParent(view,visibilityIfNot);
        }
        else
        {
            view.setText(text);
            visibleViewWithAllParent(view);
        }
    }

    protected void setImageDrawable(ImageView view, Drawable drawable,int visibilityIfNot){
        if (view == null) return;

        if (drawable == null)
        {
            invisibleViewWithAllParent(view,visibilityIfNot);
        }
        else
        {
            view.setImageDrawable(drawable);
            visibleViewWithAllParent(view);
        }
    }

    protected void setClickListener(View view, final OnDialogClickListener listener){
        if (view == null) return;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                {
                    listener.onClick(BaseDialog.this);
                }
            }
        });
    }

    //如果指定的ViewGroup下的所有子控件均未不可见，则直接隐藏该ViewGroup
    protected void invisibleViewWithAllParent(View view, int visibilityIfNot){
        if (view == null)  return;

        if (view instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) view;
            boolean isInvisible = true;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                if (viewGroup.getChildAt(i).getVisibility() == View.VISIBLE)
                    break;
                if (i == viewGroup.getChildCount()-1 && isInvisible)
                {
                    viewGroup.setVisibility(visibilityIfNot);
                    invisibleViewWithAllParent((ViewGroup) viewGroup.getParent(),visibilityIfNot);
                }
            }
        } else {
            view.setVisibility(visibilityIfNot);
        }
    }

    //将指定的ViewGroup以及以上所有parent全部设置为可见
    protected void visibleViewWithAllParent(View view){
        if (view.getParent() == null)  return;

        if (view instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getVisibility() != View.VISIBLE)
            {
                viewGroup.setVisibility(View.VISIBLE);
                visibleViewWithAllParent((ViewGroup) viewGroup.getParent());
            }
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    //指定控件具体类型，获取Container容器下所有该类型的控件
    protected <T extends View> List<T> getAllSomeView(View container,Class<T> someView) {
        List<T> list = new ArrayList<>();
        if (someView.isAssignableFrom(container.getClass())){
            list.add((T) container);
        }
        if (container instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) container;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                list.addAll(getAllSomeView(viewGroup.getChildAt(i),someView));
            }
        }
        return list;
    }

    //所有get

    public View getRootView() {
        return rootView;
    }

    public Object getTag() {
        return tag;
    }

    public boolean isShowing(){
        if (getDialog() == null){
            return false;
        }
        return getDialog().isShowing();
    }

    protected Dialog getDialog() {
        return dialog;
    }

    protected Context getContext() {
        return context;
    }

    protected int getAppScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    protected int getAppScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    protected boolean isStatusBarVisible() {
        int flags = ((Activity)getContext()).getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    protected int getStatusBarHeight() {
        Resources resources = getContext().getResources();
        @SuppressLint({"DiscouragedApi", "InternalInsetResource"})
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    //内部工具类或者监听

    public interface OnDialogShowListener {
        void onShow(BaseDialog<?> dialog);
    }

    public interface OnDialogDismissListener {
        void onDismiss(BaseDialog<?> dialog);
    }

    public interface OnDialogCancelListener {
        void onCancel(BaseDialog<?> dialog);
    }

    public interface OnDialogClickListener{
        void onClick(BaseDialog<?> dialog);
    }

}

package com.xq.fasterdialog.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.xq.fasterdialog.R;
import com.xq.worldbean.util.ImageLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public abstract class BaseDialog<T extends BaseDialog> implements DialogInterface{

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
    protected static int PROGRESS_ACCURACY = 3600;

    //Dialog
    private Dialog dialog;

    //上下文
    private Context context;

    //对用户自定义布局包裹后的顶层布局（某些情况下等于customView）
    private View rootView;
    //用户的自定义布局
    private View customView;

    //自定义相关属性
    protected int gravity = Gravity.CENTER;
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
    protected View attchView;
    //Dialog初始化相关属性
    protected int style = STYLE_BASE;
    protected int animate = ANIMATE_ALPHA;
    protected int layoutId;
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

        int reallyWidth = width > 0?width:width == MATCH_PARENT?ScreenUtils.getScreenWidth(getContext()):rootView.getMeasuredWidth();
        int reallyHeight = height > 0?height:height == MATCH_PARENT?ScreenUtils.getScreenHeight(getContext()):rootView.getMeasuredHeight();
        if (maxWidth > 0 && reallyWidth > maxWidth)
            lp.width = maxWidth;
        else
            lp.width = width;
        if (maxHeight > 0 && reallyHeight > maxHeight)
            lp.height = maxHeight;
        else
            lp.height = height;
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
            location[1] = location[1] - ScreenUtils.getStatusBarHeight();

            rootView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

            int mWidth = lp.width > 0?lp.width:lp.width == MATCH_PARENT?ScreenUtils.getScreenWidth(getContext()):rootView.getMeasuredWidth();
            int mHeight = lp.height > 0?lp.height:lp.height == MATCH_PARENT?ScreenUtils.getScreenHeight(getContext()):rootView.getMeasuredHeight();
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
            lp.x = location[0];
            lp.y = location[1];
        }
        else
        {
            int[] location = new int[]{x,y};
            window.setGravity(gravity);
            lp.x = location[0];
            lp.y = location[1];
        }
    }

    public void show() {
        if (((Activity)getContext()).isFinishing()) return;

        if (!isCreated) create();

        getDialog().show();

        if (autoDismissTime > 0) autoDismiss();
    }

    private boolean isCreated = false;
    public final T create(){
        if (isCreated) return (T) this;

        isCreated = true;

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

        if (customView == null) customView = getDialog().getLayoutInflater().inflate(layoutId,null);

        CardView cardView = new CardView(getContext());
        cardView.setCardBackgroundColor(Color.TRANSPARENT);
        cardView.setCardElevation(elevation);
        cardView.setUseCompatPadding(elevation != 0);

        ViewGroup target = customView.findViewById(getContext().getResources().getIdentifier("contentLayout", "id", getContext().getPackageName()));
        if (target == null || target.getParent() == null)
        {
            customView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            cardView.addView(customView);
            rootView = cardView;
        }
        else
        {
            ViewGroup targetParent=((ViewGroup)target.getParent());
            int index = targetParent.indexOfChild(target);
            targetParent.removeView(target);
            cardView.setLayoutParams(target.getLayoutParams());
            target.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            cardView.addView(target);
            targetParent.addView(cardView,index,cardView.getLayoutParams());
            rootView = customView;
        }
        
        getDialog().getWindow().setContentView(rootView,new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        getDialog().getWindow().setWindowAnimations(animate);
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
        return (T) this;
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

    protected  <T_VIEW extends View> T_VIEW findViewById(int id) {
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

    public T setAnimate(int animate) {
        this.animate = animate;
        return (T) this;
    }

    public T setCustomView(int layoutId){
        this.layoutId = layoutId;
        return (T) this;
    }

    public T setCustomView(View view){
        this.customView = view;
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
        this.width = MATCH_PARENT;
        return (T) this;
    }

    public T setHeightMatch() {
        this.height = MATCH_PARENT;
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

    public T setPopupFromScreen(){
        return setPopupFromScreen(Gravity.CENTER);
    }

    public T setPopupFromScreen(int gravity){
        setGravity(gravity);
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
        setPopupFromView(view,Gravity.BOTTOM|Gravity.RIGHT);
        return (T) this;
    }

    public T setPopupFromView(View view,int gravity){
        this.attchView = view;
        setGravity(gravity);
        if (gravity == (Gravity.BOTTOM|Gravity.RIGHT))
            ;
        else    if (gravity == Gravity.BOTTOM)
            setAnimate(ANIMATE_TOP);
        else    if (gravity == Gravity.TOP)
            setAnimate(ANIMATE_BOTTOM);
        else    if (gravity == Gravity.LEFT)
            setAnimate(ANIMATE_RIGHT);
        else    if (gravity == Gravity.RIGHT)
            setAnimate(ANIMATE_LEFT);
        return (T) this;
    }

    public T setPopupFromViewTouchLocation(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setX((int) (event.getRawX()));
                //注意这里获取的是屏幕的绝对坐标，其包含了状态栏的高度
                //因为dialog总是在状态栏下方，所以需要减去状态栏的高度
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

    public T setAutoDismissTime(int autoDismissTime) {
        this.autoDismissTime = autoDismissTime;
        return (T) this;
    }

    public T setTag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    //对Dialog内部可调用set方法
    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setGravity(int gravity) {
        this.gravity = gravity;
    }

    //以下通过自定义布局的控件Id快速设置一些常用的控件属性
    protected void setText(TextView view, CharSequence text,int visibilityIfNot){
        if (view == null) return;

        if (TextUtils.isEmpty(text))
        {
            invisibleView(view,visibilityIfNot);
        }
        else
        {
            view.setText(text);
            visibleView(view);
        }
    }

    protected void setImageRes(ImageView view, int id,int visibilityIfNot){
        if (view == null) return;

        if (id == 0)
        {
            invisibleView(view,visibilityIfNot);
        }
        else
        {
            view.setImageResource(id);
            visibleView(view);
        }
    }

    protected void setImageUrl(final ImageView view, final String url,int visibilityIfNot){
        if (view == null) return;

        if (TextUtils.isEmpty(url))
        {
            invisibleView(view,visibilityIfNot);
        }
        else
        {
            ImageLoader.loadImage(getContext(),url,view);
            visibleView(view);
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
                    if (listener.isDismiss()) dismiss();
                }
            }
        });
    }

    protected void invisibleView(View view,int visibilityIfNot){
        view.setVisibility(visibilityIfNot);
        invisibleLayout((ViewGroup) view.getParent(),visibilityIfNot);
    }

    protected void visibleView(View view){
        view.setVisibility(View.VISIBLE);
        visibleLayout((ViewGroup) view.getParent());
    }

    //如果指定的ViewGroup下的所有子控件均未不可见，则直接隐藏该ViewGroup
    protected void invisibleLayout(ViewGroup viewGroup, int visibilityIfNot){
        if (viewGroup.getParent() == null)  return;

        boolean isInvisible =true;
        for (int i = 0; i < viewGroup.getChildCount(); i++)
        {
            if (viewGroup.getChildAt(i).getVisibility() == View.VISIBLE)
                break;
            if (i == viewGroup.getChildCount()-1 && isInvisible)
            {
                viewGroup.setVisibility(visibilityIfNot);
                invisibleLayout((ViewGroup) viewGroup.getParent(),visibilityIfNot);
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



    //所有get
    public View getRootView() {
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



    //内部工具类或者监听
    public static interface OnDialogShowListener {
        public void onShow(BaseDialog dialog);
    }

    public static interface OnDialogDismissListener {
        public void onDismiss(BaseDialog dialog);
    }

    public static interface OnDialogCancelListener {
        public void onCancel(BaseDialog dialog);
    }

    public static abstract class BaseDialogListener {

        private boolean isDismiss;

        public BaseDialogListener() {
            this(true);
        }

        public BaseDialogListener(boolean isDismiss) {
            this.isDismiss = isDismiss;
        }

        public boolean isDismiss() {
            return isDismiss;
        }

        public void setDismiss(boolean dismiss) {
            isDismiss = dismiss;
        }
    }

    public static abstract class OnDialogClickListener extends BaseDialogListener {

        public OnDialogClickListener() {
        }

        public OnDialogClickListener(boolean isDismiss) {
            super(isDismiss);
        }

        public abstract void onClick(BaseDialog dialog);
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

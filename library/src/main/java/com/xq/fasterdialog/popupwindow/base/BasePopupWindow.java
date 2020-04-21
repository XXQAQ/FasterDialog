package com.xq.fasterdialog.popupwindow.base;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.xq.androidfaster.util.tools.ScreenUtils;
import com.xq.fasterdialog.R;
import com.xq.androidfaster.util.ImageLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public abstract class BasePopupWindow<T extends BasePopupWindow>{

    //弹出动画（包含进入与进出）
    public static int ANIMATE_ALPHA = R.style.Animate_Alpha;
    public static int ANIMATE_BOTTOM = R.style.Animate_Bottom;
    public static int ANIMATE_TOP = R.style.Animate_Top;
    public static int ANIMATE_LEFT = R.style.Animate_Left;
    public static int ANIMATE_RIGHT = R.style.Animate_Right;

    //进度精度(值越大精度越细，但是也不可以过大)
    protected static int PROGRESS_ACCURACY = 1000;

    //PopupWindow
    private PopupWindow popupWindow;

    //上下文
    private Context context;

    //对用户自定义布局包裹后的顶层布局（某些情况下等于customView）
    private View rootView;
    //用户的自定义布局
    private View customView;

    //自定义相关属性
    protected int gravity = Gravity.CENTER;
    protected int width;
    protected int height;
    protected int maxWidth;
    protected int maxHeight;
    protected int x;
    protected int y;
    protected float alpha = 1.0f;
    protected float elevation;
    protected int radius;
    protected int autoDismissTime;
    protected Object tag;
    protected View attchView;
    //PopupWindow初始化相关属性
    protected int animate = ANIMATE_ALPHA;
    protected int layoutId;
    protected boolean cancelable = false;
    protected List<OnPopupWindowDismissListener> list_dismissListener = new LinkedList<>();
    protected List<OnPopupWindowShowListener> list_showListener = new LinkedList<>();


    public BasePopupWindow(@NonNull Context context) {
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

    }

    public void onStop(){

    }

    //当PopupWindow需要动态调整宽高的时候，请调用此方法
    protected void measure() {

        rootView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

        int reallyWidth = width > 0?width:width == MATCH_PARENT?ScreenUtils.getScreenWidth():rootView.getMeasuredWidth();

        if (maxWidth > 0 && reallyWidth > maxWidth)
            getPopupWindow().setWidth(maxWidth);
        else
            getPopupWindow().setWidth(width);

        int reallyHeight = height > 0?height:height == MATCH_PARENT?ScreenUtils.getScreenHeight():rootView.getMeasuredHeight();
        if (maxHeight > 0 && reallyHeight > maxHeight)
            getPopupWindow().setHeight(maxHeight);
        else
            getPopupWindow().setHeight(height);
    }

    //当PopupWindow需要调整弹出位置的时候，请调用此方法
    protected void showAtLocation(){
        if (attchView != null)
        {
            View attachRootView = attchView.getRootView();

            //注意这里获取的是屏幕的绝对坐标
            int[] location = new int[2] ;
            int[] attachRootLocation = new int[2];
            int[] attachLocation = new int[2];

            attachRootView.getLocationOnScreen(attachRootLocation);
            attchView.getLocationOnScreen(attachLocation);

            location[0] = attachLocation[0] - attachRootLocation[0];
            location[1] = attachLocation[1] - attachRootLocation[1];

            rootView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

            int mWidth = getPopupWindow().getWidth() > 0?getPopupWindow().getWidth():getPopupWindow().getWidth() == MATCH_PARENT?ScreenUtils.getScreenWidth():rootView.getMeasuredWidth();
            int mHeight = getPopupWindow().getHeight() > 0?getPopupWindow().getHeight():getPopupWindow().getHeight() == MATCH_PARENT?ScreenUtils.getScreenHeight():rootView.getMeasuredHeight();
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
            getPopupWindow().showAtLocation(attachRootView,Gravity.TOP|Gravity.START,location[0],location[1]);
        }
        else
        {
            View attachRootView = ((Activity)getContext()).getWindow().getDecorView().findViewById(android.R.id.content);

            int[] location = new int[]{x,y};
            getPopupWindow().showAtLocation(attachRootView,gravity,location[0],location[1]);
        }
    }

    public void show() {
        if (((Activity)getContext()).isFinishing()) return;

        if (!isCreated) create();

        onStart();

        showAtLocation();

        for(OnPopupWindowShowListener l : list_showListener) l.onShow(BasePopupWindow.this);

        if (autoDismissTime > 0) autoDismiss();
    }

    private boolean isCreated = false;
    public T create(){
        if (isCreated) return (T) this;

        isCreated = true;

        popupWindow = new PopupWindow(getContext());

        if (customView == null) customView = inflate(layoutId);

        CardView cardView = new CardView(getContext());
        cardView.setRadius(radius);
        cardView.setCardBackgroundColor(Color.TRANSPARENT);
        cardView.setCardElevation(elevation);
        cardView.setUseCompatPadding(elevation != 0);

        ViewGroup targetView = customView.findViewById(getContext().getResources().getIdentifier("contentLayout", "id", getContext().getPackageName()));
        if (targetView == null || targetView.getParent() == null)
        {
            cardView.addView(customView,new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            rootView = cardView;
        }
        else
        {
            insertView(targetView,cardView);
            rootView = customView;
        }
        getPopupWindow().setContentView(rootView);

        rootView.setAlpha(alpha);
        getPopupWindow().setAnimationStyle(animate);
        getPopupWindow().setTouchable(true);
        getPopupWindow().setOutsideTouchable(cancelable);
        getPopupWindow().setFocusable(cancelable);
        getPopupWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                for (OnPopupWindowDismissListener l : list_dismissListener)
                    l.onDismiss(BasePopupWindow.this);
            }
        });
//        getPopupWindow().setInputMethodMode(INPUT_METHOD_NEEDED);
//        getPopupWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        getPopupWindow().setFocusable(true);

        onCreate(null);

        return (T) this;
    }

    public View inflate(int layoutId) {
        FrameLayout tempLayout = new FrameLayout(getContext());
        View result = LayoutInflater.from(getContext()).inflate(layoutId, tempLayout, false);
        ViewGroup.LayoutParams tempParams = result.getLayoutParams();

        if (width == 0){
            setWidth(tempParams.width);
        }
        if (height == 0){
            setHeight(tempParams.height);
        }
        return result;
    }

    //将insertView插至targetView的所在的节点位置
    protected void insertView(View targetView, ViewGroup insertView){
        ViewGroup targetParent=((ViewGroup)targetView.getParent());
        int index = targetParent.indexOfChild(targetView);
        targetParent.removeView(targetView);
        insertView.setLayoutParams(targetView.getLayoutParams());
        targetView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        insertView.addView(targetView);
        targetParent.addView(insertView,index,insertView.getLayoutParams());
    }

    public void dismiss() {
        if (((Activity)getContext()).isFinishing()) return;

        if (autoDismissTime > 0 && timer != null) timer.cancel();

        getPopupWindow().dismiss();

        onStop();
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
        new CountDownTimer(autoDismissTime, (long) ((float)autoDismissTime/(float)PROGRESS_ACCURACY)) {
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
        this.width = (int) (percent * ScreenUtils.getScreenWidth());
        return (T) this;
    }

    public T setHeightPercent(float percent) {
        this.height = (int) (percent * ScreenUtils.getScreenHeight());
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
        this.maxWidth = (int) (percent * ScreenUtils.getScreenWidth());
        return (T) this;
    }

    public T setMaxHeightPercent(float percent) {
        this.maxHeight = (int) (percent * ScreenUtils.getScreenHeight());
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

    public T setRadius(int radius) {
        this.radius = radius;
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
        setPopupFromView(view,Gravity.BOTTOM);
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
//        TODO
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                setX((int) (event.getRawX()));
//                //注意这里获取的是屏幕的绝对坐标，其包含了状态栏的高度
//                //因为PopupWindow总是在状态栏下方，所以需要减去状态栏的高度
//                setY((int) (event.getRawY()-(BarUtils.isStatusBarVisible((Activity) getContext())?BarUtils.getStatusBarHeight():0)));
//                setGravity(Gravity.TOP|Gravity.START);
//                return false;
//            }
//        });
        return (T) this;
    }

    public T setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return (T) this;
    }

    public T addOnDismissListener(@Nullable OnPopupWindowDismissListener listener) {
        list_dismissListener.add(listener);
        return (T) this;
    }

    public T addOnShowListener(@Nullable OnPopupWindowShowListener listener) {
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

    //对PopupWindow内部可调用set方法
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

    protected void setImageDrawable(ImageView view, Drawable drawable,int visibilityIfNot){
        if (view == null) return;

        if (drawable == null)
        {
            invisibleView(view,visibilityIfNot);
        }
        else
        {
            view.setImageDrawable(drawable);
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

    protected void setClickListener(View view, final OnPopupWindowClickListener listener){
        if (view == null) return;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                {
                    listener.onClick(BasePopupWindow.this);
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

    protected Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //所有get
    public View getRootView() {
        return rootView;
    }

    public View getCustomView(){
        return customView;
    }

    public Object getTag() {
        return tag;
    }

    public boolean isShowing(){
        if (getPopupWindow() == null){
            return false;
        }
        return getPopupWindow().isShowing();
    }

    protected PopupWindow getPopupWindow() {
        return popupWindow;
    }

    protected Context getContext() {
        return context;
    }



    //内部工具类或者监听
    public static interface OnPopupWindowShowListener {
        public void onShow(BasePopupWindow popupWindow);
    }

    public static interface OnPopupWindowDismissListener {
        public void onDismiss(BasePopupWindow popupWindow);
    }

    public static abstract class BasePopupWindowListener {

        private boolean isDismiss;

        public BasePopupWindowListener() {
            this(true);
        }

        public BasePopupWindowListener(boolean isDismiss) {
            this.isDismiss = isDismiss;
        }

        public boolean isDismiss() {
            return isDismiss;
        }

        public void setDismiss(boolean dismiss) {
            isDismiss = dismiss;
        }
    }

    public static abstract class OnPopupWindowClickListener extends BasePopupWindowListener {

        public OnPopupWindowClickListener() {
        }

        public OnPopupWindowClickListener(boolean isDismiss) {
            super(isDismiss);
        }

        public abstract void onClick(BasePopupWindow popupWindow);
    }
}

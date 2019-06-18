package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import com.xq.fasterdialog.R;
import com.xq.fasterdialog.dialog.base.BaseListDialog;

public class ListDialog extends BaseListDialog<ListDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERT;

    public static int LAYOUT_XQ = R.layout.layout_listdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_listdialog_meterail;
    public static int LAYOUT_BOTTOM = R.layout.layout_listdialog_bottom;
    public static int LAYOUT_QQMENU = R.layout.layout_listdialog_qqmenu;

    public static int ITEM_LAYOUT_CLASSICAL = R.layout.item_classical;
    public static int ITEM_LAYOUT_CLASSICAL_LARGE = R.layout.item_classical_large;
    public static int ITEM_LAYOUT_METERAIL_SINGLE = R.layout.item_meterail_single;
    public static int ITEM_LAYOUT_METERAIL_MULTI = R.layout.item_meterail_multi;

    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;
    protected static int ITEM_LAYOUT_DEFAULT = ITEM_LAYOUT_CLASSICAL;

    public ListDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId,int itemLayoutId){
        LAYOUT_DEFAULT = layoutId;
        ITEM_LAYOUT_DEFAULT = itemLayoutId;
    }

    public ListDialog setXQLayoutStyle(){
        setXQLayoutStyle(CHOOSE_MODE_SINGLE);
        return this;
    }

    public ListDialog setXQLayoutStyle(int chooseMode){
        setStyle(STYLE_ALERT);
        setCustomView(LAYOUT_XQ, ITEM_LAYOUT_CLASSICAL,chooseMode);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    public ListDialog setMeterailLayoutStyle(){
        setMeterailLayoutStyle(CHOOSE_MODE_SINGLE);
        return this;
    }

    public ListDialog setMeterailLayoutStyle(int chooseMode){
        setStyle(STYLE_ALERT);
        setCustomView(LAYOUT_METERAIL, chooseMode == CHOOSE_MODE_SINGLE?ITEM_LAYOUT_METERAIL_SINGLE:chooseMode == CHOOSE_MODE_MULTI?ITEM_LAYOUT_METERAIL_MULTI:0,chooseMode);
        setWidthMatch();
        setHeightWrap();
        return this;
    }

    public ListDialog setBottomLayoutStyle(){
        setBottomLayoutStyle(CHOOSE_MODE_SINGLE);
        return this;
    }

    public ListDialog setBottomLayoutStyle(int chooseMode){
        setStyle(STYLE_TRANSLUCENT);
        setCustomView(LAYOUT_BOTTOM, ITEM_LAYOUT_CLASSICAL,chooseMode);
        setWidthMatch();
        setHeightWrap();
        setPopupFromScreen(Gravity.BOTTOM);
        return this;
    }

    public ListDialog setQQMenuLayoutStyle(){
        setQQMenuLayoutStyle(CHOOSE_MODE_SINGLE);
        return this;
    }

    public ListDialog setQQMenuLayoutStyle(int chooseMode){
        setStyle(STYLE_TRANSLUCENT);
        setCustomView(LAYOUT_QQMENU, ITEM_LAYOUT_CLASSICAL_LARGE,chooseMode);
        setWidthMatch();
        setHeightWrap();
        setPopupFromScreen(Gravity.BOTTOM);
        setDividerDrawable(getContext().getResources().getDrawable(R.drawable.line_divider));
        return this;
    }

    public ListDialog setPopupMenuLayoutStyle(){
        setPopupMenuLayoutStyle(CHOOSE_MODE_SINGLE);
        return this;
    }

    public ListDialog setPopupMenuLayoutStyle(int chooseMode){
        setStyle(STYLE_BASE);
        setElevation(8.0f);
        setCustomView(LAYOUT_XQ, ITEM_LAYOUT_CLASSICAL,chooseMode);
        setWidthWrap();
        setHeightWrap();
        return this;
    }

    private void init() {
        setStyle(STYLE_DEFAULT);
        setCustomView(LAYOUT_DEFAULT, ITEM_LAYOUT_DEFAULT,CHOOSE_MODE_SINGLE);
    }

}

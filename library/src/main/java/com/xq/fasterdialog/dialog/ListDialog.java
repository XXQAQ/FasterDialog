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

    public static int ITEMLAYOUT_CLASSICAL_MULTI = R.layout.item_classical_multi;
    public static int ITEMLAYOUT_METERAIL_MULTI = R.layout.item_meterail_multi;
    public static int ITEMLAYOUT_CLASSICAL_LARGE_MULTI = R.layout.item_classical_large_multi;

    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;
    protected static int ITEMLAYOUT_DEFAULT = ITEMLAYOUT_CLASSICAL_MULTI;

    public ListDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public static void setDefaultStyle(int style){
        STYLE_DEFAULT = style;
    }

    public static void setDefaultLayout(int layoutId,int itemLayoutId){
        LAYOUT_DEFAULT = layoutId;
        ITEMLAYOUT_DEFAULT = itemLayoutId;
    }

    public ListDialog setXQLayoutStyle(){
        setXQLayoutStyle(CHOOSE_MODE_SINGLE);
        return this;
    }

    public ListDialog setXQLayoutStyle(int chooseMode){
        setStyle(STYLE_ALERT);
        setChooseMode(chooseMode,LAYOUT_XQ, ITEMLAYOUT_CLASSICAL_MULTI);
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
        setChooseMode(chooseMode,LAYOUT_METERAIL, ITEMLAYOUT_METERAIL_MULTI);
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
        setChooseMode(chooseMode,LAYOUT_BOTTOM,ITEMLAYOUT_CLASSICAL_MULTI);
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
        setChooseMode(chooseMode, LAYOUT_QQMENU, ITEMLAYOUT_CLASSICAL_LARGE_MULTI);
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
        setChooseMode(chooseMode,LAYOUT_XQ,ITEMLAYOUT_CLASSICAL_MULTI);
        setWidthWrap();
        setHeightWrap();
        return this;
    }

    private void init() {
        setStyle(STYLE_DEFAULT);
        setChooseMode(CHOOSE_MODE_SINGLE,LAYOUT_DEFAULT, ITEMLAYOUT_DEFAULT);
    }

}

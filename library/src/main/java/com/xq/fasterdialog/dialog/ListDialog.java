package com.xq.fasterdialog.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.xq.fasterdialog.R;
import com.xq.fasterdialog.base.BaseListDialog;

public class ListDialog extends BaseListDialog<ListDialog> {

    private static int STYLE_DEFAULT = STYLE_ALERTDIALOG;

    public static int LAYOUT_XQ = R.layout.layout_listdialog_xq;
    public static int LAYOUT_METERAIL = R.layout.layout_listdialog_meterail;
    public static int LAYOUT_BOTTOM = R.layout.layout_listdialog_bottom;
    public static int LAYOUT_QQ = R.layout.layout_listdialog_qq;

    public static int ITEMLAYOUT_CLASSICAL_SINGLE = R.layout.item_classical_single;
    public static int ITEMLAYOUT_CLASSICAL_MULTI = R.layout.item_classical_multi;
    public static int ITEMLAYOUT_METERAIL_SINGLE = R.layout.item_meterail_single;
    public static int ITEMLAYOUT_METERAIL_MULTI = R.layout.item_meterail_multi;
    public static int ITEMLAYOUT_CLASSICAL_DIVIDER_SINGLE = R.layout.item_classical_divider_single;
    public static int ITEMLAYOUT_CLASSICAL_DIVIDER_MULTI = R.layout.item_classical_divider_multi;

    protected static int LAYOUT_DEFAULT = LAYOUT_XQ;
    protected static int ITEMLAYOUT_DEFAULT = ITEMLAYOUT_CLASSICAL_SINGLE;

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
        setXQLayoutStyle(CHOOSEMODE_SINGLE);
        return this;
    }

    public ListDialog setXQLayoutStyle(int chooseMode){
        setStyle(STYLE_ALERTDIALOG);
        if (chooseMode == CHOOSEMODE_SINGLE)
            setChooseMode(chooseMode,LAYOUT_XQ, ITEMLAYOUT_CLASSICAL_SINGLE);
        else    if (chooseMode == CHOOSEMODE_MULTI)
            setChooseMode(chooseMode,LAYOUT_XQ, ITEMLAYOUT_CLASSICAL_MULTI);
        setWidthWrap();
        setHeightWrap();
        return this;
    }

    public ListDialog setMeterailLayoutStyle(){
        setMeterailLayoutStyle(CHOOSEMODE_SINGLE);
        return this;
    }

    public ListDialog setMeterailLayoutStyle(int chooseMode){
        setStyle(STYLE_ALERTDIALOG);
        if (chooseMode == CHOOSEMODE_SINGLE)
            setChooseMode(chooseMode,LAYOUT_METERAIL, ITEMLAYOUT_METERAIL_SINGLE);
        else    if (chooseMode == CHOOSEMODE_MULTI)
            setChooseMode(chooseMode,LAYOUT_METERAIL, ITEMLAYOUT_METERAIL_MULTI);
        setWidthWrap();
        setHeightWrap();
        return this;
    }

    public ListDialog setBottomLayoutStyle(){
        setBottomLayoutStyle(CHOOSEMODE_SINGLE);
        return this;
    }

    public ListDialog setBottomLayoutStyle(int chooseMode){
        setStyle(STYLE_TRANSLUCENTDIALOG);
        if (chooseMode == CHOOSEMODE_SINGLE)
            setChooseMode(chooseMode,LAYOUT_BOTTOM,ITEMLAYOUT_CLASSICAL_SINGLE);
        else    if (chooseMode == CHOOSEMODE_MULTI)
            setChooseMode(chooseMode,LAYOUT_BOTTOM,ITEMLAYOUT_CLASSICAL_MULTI);
        setWidthMatch();
        setHeightWrap();
        setPopupFromBottom();
        return this;
    }

    public ListDialog setQQLayoutStyle(){
        setQQLayoutStyle(CHOOSEMODE_SINGLE);
        return this;
    }

    public ListDialog setQQLayoutStyle(int chooseMode){
        setStyle(STYLE_TRANSLUCENTDIALOG);
        if (chooseMode == CHOOSEMODE_SINGLE)
            setChooseMode(chooseMode,LAYOUT_QQ,ITEMLAYOUT_CLASSICAL_DIVIDER_SINGLE);
        else    if (chooseMode == CHOOSEMODE_MULTI)
            setChooseMode(chooseMode,LAYOUT_QQ,ITEMLAYOUT_CLASSICAL_DIVIDER_MULTI);
        setWidthMatch();
        setHeightWrap();
        setPopupFromBottom();
        return this;
    }

    public ListDialog setPopupMenuLayoutStyle(View view){
        setPopupMenuLayoutStyle(view,ATTCHGRAVITY_DEFAULT);
        return this;
    }

    public ListDialog setPopupMenuLayoutStyle(View view,int attchGravity){
        setStyle(STYLE_TRANSLUCENTDIALOG);
        setChooseMode(CHOOSEMODE_SINGLE,LAYOUT_XQ,ITEMLAYOUT_CLASSICAL_SINGLE);
        setWidthWrap();
        setHeightWrap();
        setPopupFromView(view,attchGravity);
        return this;
    }

    private void init() {
        setStyle(STYLE_DEFAULT);
        setChooseMode(CHOOSEMODE_SINGLE,LAYOUT_DEFAULT, ITEMLAYOUT_DEFAULT);
    }

}

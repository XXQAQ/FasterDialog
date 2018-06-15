package com.xq.fasterdialog;

import android.content.DialogInterface;

import com.xq.fasterdialog.base.BaseDialog;
import com.xq.fasterdialog.base.BaseNormalDialog;
import com.xq.fasterdialog.dialog.NormalDialog;

public class DialogManager {

    private static BaseDialog dialog;

    public static void showDialog(BaseDialog dialog){

        DialogManager.dialog = dialog;

        dialog.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DialogManager.dialog = null;
            }
        });
    }

    public static void dismissDialog(){
        DialogManager.dialog.dismiss();
    }

}

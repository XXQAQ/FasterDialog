package com.xq.fasterdialog.dialog;

import android.content.DialogInterface;

public class DialogManager {

    private static NormalDialog dialog;

    public static void showDialog(NormalDialog dialog){

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

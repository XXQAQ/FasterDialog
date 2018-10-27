package com.xq.fasterdialog;

import com.xq.fasterdialog.base.BaseDialog;
import com.xq.fasterdialog.base.DialogDelegateActivity;

public class DialogManager {

    private static BaseDialog dialog;

    public static void anywhere(DialogDelegateActivity.DialogContextProvider contextProvider){
        DialogDelegateActivity.startActivity(contextProvider);
    }

    public static void showDialog(BaseDialog dialog){

        DialogManager.dialog = dialog;

        dialog.addOnDismissListener(new BaseDialog.OnDialogDismissListener() {
            @Override
            public void onDismiss(BaseDialog dialog) {
                DialogManager.dialog = null;
            }
        }).show();
    }

    public static void dismissDialog(){
        DialogManager.dialog.dismiss();
    }

    public static BaseDialog getDialog(){
        return dialog;
    }

}

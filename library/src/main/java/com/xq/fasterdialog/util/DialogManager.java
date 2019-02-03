package com.xq.fasterdialog.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.xq.fasterdialog.FasterDialog;
import com.xq.fasterdialog.base.BaseDialog;

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

    public static class DialogDelegateActivity extends AppCompatActivity {

        private static DialogContextProvider contextProvider;

        public static void startActivity(DialogContextProvider contextProvider){
            DialogDelegateActivity.contextProvider = contextProvider;
            Intent intent = new Intent(FasterDialog.getApp(), DialogDelegateActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            FasterDialog.getApp().startActivity(intent);
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            BaseDialog dialog = contextProvider.showDialig(this);
            dialog.addOnDismissListener(new BaseDialog.OnDialogDismissListener() {
                @Override
                public void onDismiss(BaseDialog dialog) {
                    finish();
                }
            });
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            contextProvider = null;
        }

        public static interface DialogContextProvider{

            public BaseDialog showDialig(Context context);

        }
    }

}

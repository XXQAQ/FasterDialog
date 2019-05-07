package com.xq.fasterdialog.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.xq.fasterdialog.FasterDialog;
import com.xq.fasterdialog.base.BaseDialog;
import java.util.LinkedHashMap;

public class DialogManager {

    private static LinkedHashMap<Integer,BaseDialog> map_dialog = new LinkedHashMap<>();

    public static void showAnywhere(DialogDelegateActivity.DialogContextProvider contextProvider){
        DialogDelegateActivity.startActivity(contextProvider);
    }

    public static void showDialog(BaseDialog dialog){

        DialogManager.map_dialog.put(dialog.hashCode()&0x0000ffff,dialog);

        dialog.addOnDismissListener(new BaseDialog.OnDialogDismissListener() {
            @Override
            public void onDismiss(BaseDialog dialog) {
                DialogManager.map_dialog.remove(dialog.hashCode()&0x0000ffff);
            }
        }).show();
    }

    public static void dismissDialog(){
        ((BaseDialog)DialogManager.map_dialog.keySet().toArray()[DialogManager.map_dialog.size() -1]).dismiss();
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
            BaseDialog dialog = contextProvider.createDialog(this);
            dialog.addOnDismissListener(new BaseDialog.OnDialogDismissListener() {
                @Override
                public void onDismiss(BaseDialog dialog) {
                    finish();
                }
            });
            dialog.show();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            contextProvider = null;
        }

        public static interface DialogContextProvider{

            public BaseDialog createDialog(Context context);

        }
    }

}

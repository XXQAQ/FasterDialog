package com.xq.fasterdialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xq.fasterdialog.base.BaseDialog;

public class DialogDelegateActivity extends AppCompatActivity {

    private static DialogContextProvider contextProvider;

    public static void startActivity(DialogContextProvider contextProvider){
        DialogDelegateActivity.contextProvider = contextProvider;
        Intent intent = new Intent(FasterDialogInterface.getApp(), DialogDelegateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FasterDialogInterface.getApp().startActivity(intent);
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

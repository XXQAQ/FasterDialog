package com.xq.fasterdialog.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xq.fasterdialog.FasterDialogInterface;

public class DialogDelegateActivity extends AppCompatActivity {

    private static DialogContextProvider contextProvider;

    public static void show(DialogContextProvider contextProvider){
        DialogDelegateActivity.contextProvider = contextProvider;
        Intent intent = new Intent(FasterDialogInterface.getApp(),DialogDelegateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FasterDialogInterface.getApp().startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseDialog dialog = contextProvider.showDialig(this);
        dialog.addDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
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

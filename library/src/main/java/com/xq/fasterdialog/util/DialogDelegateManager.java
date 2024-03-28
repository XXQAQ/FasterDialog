package com.xq.fasterdialog.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xq.fasterdialog.dialog.base.BaseDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DialogDelegateManager {

    private static final Map<String, DialogDelegateActivity.DialogContextProvider> dialogContextProviderMap = new HashMap<>();

    public static void startDialogDelegate(Application application,DialogDelegateActivity.DialogContextProvider contextProvider){
        String businessId = UUID.randomUUID().toString();
        dialogContextProviderMap.put(businessId,contextProvider);
        DialogDelegateActivity.startActivity(application,businessId);
    }

    public static class DialogDelegateActivity extends AppCompatActivity {

        private static final String KEY_BUSINESS_ID = "businessId";

        public static void startActivity(Application application,String businessId){
            Intent intent = new Intent(application, DialogDelegateActivity.class);
            intent.putExtra(KEY_BUSINESS_ID,businessId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (dialogContextProviderMap.containsKey(getBusinessId())){
                BaseDialog<?> dialog = dialogContextProviderMap.remove(getBusinessId()).createDialog(this);
                dialog.addOnDismissListener(new BaseDialog.OnDialogDismissListener() {
                    @Override
                    public void onDismiss(BaseDialog dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            dialogContextProviderMap.remove(getBusinessId());
        }

        private String getBusinessId(){
            return getIntent().getStringExtra(KEY_BUSINESS_ID);
        }

        public interface DialogContextProvider{
            BaseDialog<?> createDialog(Context context);
        }
    }

}

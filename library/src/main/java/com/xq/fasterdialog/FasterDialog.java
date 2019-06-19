package com.xq.fasterdialog;

import android.app.Application;

public class FasterDialog {

    private static Application app;

    public static void init(Application app){
        FasterDialog.app = app;
    }

    public static Application getApp() {
        return app;
    }

}

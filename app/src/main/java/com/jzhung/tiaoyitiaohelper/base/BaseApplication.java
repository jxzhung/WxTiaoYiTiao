package com.jzhung.tiaoyitiaohelper.base;

import android.app.Application;

import com.jzhung.tiaoyitiaohelper.util.CrashHandler;

/**
 * Created by jzhung on 2018/1/6.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}

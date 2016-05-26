package com.app.fan.pathview;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by fan on 2016/5/26.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}

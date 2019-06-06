package com.master.app;

import android.app.Application;

import com.master.app.util.ActivityStack;

/**
 * Create By Master
 * On 2019/5/7 19:47
 */
public class IApplication extends Application {

    private static IApplication app;

    private ActivityStack mActivityStack = new ActivityStack();


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        registerActivityLifecycleCallbacks(mActivityStack);
    }


    public static IApplication getInstance() {
        return app;
    }


    public ActivityStack getActivityStack() {
        return mActivityStack;
    }


}

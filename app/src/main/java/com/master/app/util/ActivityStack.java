package com.master.app.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Iterator;
import java.util.Stack;

/**
 * Create By Master
 * On 2018/11/27 18:05
 * 生命周期管理
 */
public class ActivityStack implements Application.ActivityLifecycleCallbacks {

    private final Stack<Activity> activityStack = new Stack<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        joinActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        popActivity(activity);
    }


    private void joinActivity(Activity activity) {
        activityStack.add(activity);
    }

    private void popActivity(Activity activity) {
        if (activity != null) {
            this.activityStack.remove(activity);
        }
    }


    public final void finishActivity() {
        if (activityStack.isEmpty()) {
            return;
        }
        Activity activity = activityStack.pop();
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }

    }

    public final void finishActivity(Activity activity) {
        if (activity != null) {
            this.activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public final void finishActivity(Class<? extends Activity> clas) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(clas)) {
                iterator.remove();
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    public final void finishAllActivity() {
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
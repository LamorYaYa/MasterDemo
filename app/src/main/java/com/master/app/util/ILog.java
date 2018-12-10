package com.master.app.util;

import android.text.TextUtils;
import android.util.Log;

import com.master.app.BuildConfig;

/**
 * Create By Master
 * On 2018/11/27 18:02
 */
public class ILog {

    private static final String customTag = "cp-log";

    public static void e(String content) {
        if (!BuildConfig.LOG_ON) {
            return;
        }
        String tag = generateTag();
        Log.e(tag, content);
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTag) ? tag : customTag + ":" + tag;
        return tag;
    }

}

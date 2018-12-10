package com.master.app.permission;

/**
 * Create By Master
 * On 2018/11/29 17:10
 */
public interface PermissionHelpCallBack {
    void onPermissionGranted(int requestCode, String... perms);
    void onPermissionDenied(int requestCode, String... perms);
}

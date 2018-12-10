package com.master.app.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.master.app.dialog.IDefaultDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2018/11/29 14:56
 */
public class PermissionHelp {

    public static final int SETTINGS_REQUEST_CODE = 12321;

    public interface PermissionCallback {
        void onPermissionGranted(int requestCode, String... perms);

        void onPermissionDenied(int requestCode, String... perms);
    }

    private Object object; // Activity or Fragment
    private String[] mPermissions; // 请求权限数组
    private String mRationale; // 描述内容==拒绝后跳转设置时Dialog展示
    private int mRequestCode; // 请求码

    private int mLeftButtonText = android.R.string.cancel;
    private int mRightButtonText = android.R.string.ok;

    private PermissionHelp(Object object) {
        this.object = object;
    }

    public static PermissionHelp with(Activity activity) {
        return new PermissionHelp(activity);
    }

    public static PermissionHelp with(Fragment fragment) {
        return new PermissionHelp(fragment);
    }

    public PermissionHelp permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionHelp rationale(String rationale) {
        this.mRationale = rationale;
        return this;
    }

    public PermissionHelp addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionHelp rightButtonText(@StringRes int rightButtonText) {
        this.mRightButtonText = rightButtonText;
        return this;
    }

    public PermissionHelp leftButtonText(@StringRes int leftButtonText) {
        this.mLeftButtonText = leftButtonText;
        return this;
    }

    public void request() {
        requestPermissions(object, mRationale, mLeftButtonText, mRightButtonText, mRequestCode, mPermissions);
    }

    private static void requestPermissions(final Object object, String rationale, @StringRes int leftButton, @StringRes int rightButton, final int requestCode, final String... permissions) {

        checkCallingObjectSuitability(object);
        PermissionCallback permissionCallback = (PermissionCallback) object;
        if (Build.VERSION.SDK_INT < 23) {
            permissionCallback.onPermissionGranted(requestCode, permissions);
            return;
        }

        // 遍历所有权限，确认是否有拒绝过的，有拒绝过的则需要弹框提示说明
        List<String> deniedPermissions = findDeniedPermissions(objectToActivity(object), permissions);
        if (deniedPermissions == null) {
            return;
        }

        if (deniedPermissions.isEmpty()) {
            permissionCallback.onPermissionGranted(requestCode, permissions);
        } else {

            final String[] deniedPermissionArray = deniedPermissions.toArray(new String[deniedPermissions.size()]);

            boolean shouldShowDialog = false;
            for (String permission : deniedPermissions) {
                shouldShowDialog = shouldShowDialog || shouldShowRequestPermissionRationale(object, permission);
            }

            if (shouldShowDialog) {
                Activity activity = objectToActivity(object);
                if (null == activity) {
                    return;
                }

                new IDefaultDialog(activity).builder()
                        .setGravity(Gravity.CENTER)
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setTitle("申请权限")
                        .setSubTitle(rationale)
                        .setLeftButton(activity.getString(leftButton), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((PermissionCallback) object).onPermissionDenied(requestCode, deniedPermissionArray);
                            }
                        })
                        .setRightButton(activity.getString(rightButton), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                executePermissionsRequest(object, deniedPermissionArray, requestCode);
                            }
                        })
                        .show();


            } else {
                executePermissionsRequest(object, deniedPermissionArray, requestCode);
            }
        }


    }


    /**
     * 判断条件是否满足
     *
     * @param object
     */
    private static void checkCallingObjectSuitability(Object object) {

        if (!(object instanceof Fragment || object instanceof Activity || object instanceof android.app.Fragment)) {
            throw new IllegalArgumentException("object is not Activity or Fragment.");
        }

        if (!(object instanceof PermissionCallback)) {
            throw new IllegalArgumentException("object is not implement PermissionCallback.");
        }
    }

    /**
     * Object 得到 Activity
     *
     * @param object
     * @return
     */
    private static Activity objectToActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }


    /**
     * 拒绝过的权限
     *
     * @param activity
     * @param permission
     * @return
     */
    private static List<String> findDeniedPermissions(Activity activity, String... permission) {
        if (activity == null) {
            return null;
        }
        List<String> denyPermissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23) {
            for (String value : permission) {
                if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                    denyPermissions.add(value);
                }
            }
        }
        return denyPermissions;
    }


    /**
     * return true 表明用户没有彻底禁止弹出权限请求
     * return false 表明用户彻底禁止弹出权限请求
     */
    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return object instanceof android.app.Fragment && Build.VERSION.SDK_INT >= 23
                    && ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        }
    }


    /**
     * 申请权限
     *
     * @param object
     * @param perms
     * @param requestCode
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(perms, requestCode);
        }
    }


    /**
     * 是否具有此权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (permissions == null || permissions.length == 0) {
            return true;
        }

        for (String permission : permissions) {
            boolean hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            if (!hasPermission) {
                return false;
            }
        }
        return true;
    }


    /**
     * 权限结果接收
     *
     * @param object
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Object object, int requestCode, String[] permissions, int[] grantResults) {
        checkCallingObjectSuitability(object);
        PermissionCallback permissionCallback = (PermissionCallback) object;
        List<String> deniedPermission = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermission.add(permissions[i]);
            }
        }
        if (deniedPermission.isEmpty()) {
            permissionCallback.onPermissionGranted(requestCode, permissions);
        } else {
            permissionCallback.onPermissionDenied(requestCode, deniedPermission.toArray(new String[deniedPermission.size()]));
        }
    }


    /**
     * 从设置界面回来检查是否已经授权
     *
     * @param object
     * @param rationale
     * @param leftButton
     * @param rightButton
     * @param rightButtonOnClickListener
     * @param deniedPerms
     * @return
     */
    public static boolean checkDeniedPermissionsNeverAskAgain(final Object object, String rationale, int leftButton, int rightButton, View.OnClickListener rightButtonOnClickListener, String... deniedPerms) {

        boolean shouldShowDialog;
        for (String permission : deniedPerms) {
            shouldShowDialog = shouldShowRequestPermissionRationale(object, permission);
            if (!shouldShowDialog) {
                final Activity activity = objectToActivity(object);
                if (activity == null) {
                    return true;
                }

                new IDefaultDialog(activity).builder()
                        .setGravity(Gravity.CENTER)
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setTitle("申请权限")
                        .setSubTitle(rationale)
                        .setLeftButton(activity.getString(leftButton), rightButtonOnClickListener)
                        .setRightButton(activity.getString(rightButton), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                intent.setData(uri);
                                activity.startActivityForResult(intent, SETTINGS_REQUEST_CODE);
                            }
                        })
                        .show();

                return true;
            }
        }
        return false;
    }


}

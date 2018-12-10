package com.master.app;

import android.Manifest;
import android.view.View;
import android.widget.Toast;

import com.master.app.base.BaseActivity;
import com.master.app.constant.MainActivityConstant;
import com.master.app.constant.impl.MainActivityPresenter;
import com.master.app.http.HttpService;
import com.master.app.http.IHttpManager;
import com.master.app.http.IRetrofit;
import com.master.app.permission.PermissionHelpCallBack;
import com.master.app.util.ILog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity<MainActivityConstant.MainActivityView, MainActivityPresenter> implements MainActivityConstant.MainActivityView, View.OnClickListener, PermissionHelpCallBack {


    public String[] cameraPermissions = {Manifest.permission.CAMERA};
    public String[] storagePermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected int createContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_cmaera).setOnClickListener(this);
        findViewById(R.id.btn_storage).setOnClickListener(this);
        findViewById(R.id.btn_image).setOnClickListener(this);
        findViewById(R.id.btn_images).setOnClickListener(this);
        findViewById(R.id.btn_interface).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_cmaera:
                super.requestPermission(123, cameraPermissions, "没有相机权限将不能拍照", new PermissionHelpCallBack() {
                    @Override
                    public void onPermissionGranted(int requestCode, String... perms) {
                        Toast.makeText(MainActivity.this, "获取到权限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(int requestCode, String... perms) {
                        Toast.makeText(MainActivity.this, "没有获取到权限", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_storage:
                super.requestPermission(234, storagePermissions, "没有读写存储卡权限,有问题有问题有问题有问题", new PermissionHelpCallBack() {
                    @Override
                    public void onPermissionGranted(int requestCode, String... perms) {
                        Toast.makeText(MainActivity.this, "获取到权限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(int requestCode, String... perms) {
                        Toast.makeText(MainActivity.this, "没有获取到权限", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_interface:
                Map<String, String> mHashMap = new HashMap<>();
                mHashMap.put("1", "1");
                mHashMap.put("2", "2");

                IHttpManager.doRequest(IRetrofit.createApi(HttpService.class).sendCode(), new IHttpManager.IResponseListener<Beans>() {
                    @Override
                    public void onSuccess(Beans data) {

                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
                break;
            case R.id.btn_image:

                File file = new File("/sdcard/test.png");
                if (!file.exists()) {
                    ILog.e("文件不存在...");
                    return;
                }

                IHttpManager.doUploadImage(file, new IHttpManager.IResponseListener<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody data) {
                        try {
                            ILog.e("成功" + data.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        ILog.e("失败" + e.toString());
                    }
                });
                break;
            case R.id.btn_images:
                File file1 = new File("/sdcard/test.png");
//                File file2 = new File("/sdcard/123.png");
//                File file3 = new File("/sdcard/234.png");
//                File[] files = new File[]{file1, file2, file3};
//                IHttpManager.doUploadImages(files, new IHttpManager.IResponseListener<ResponseBody>() {
//                    @Override
//                    public void onSuccess(ResponseBody data) {
//                        try {
//                            ILog.e("成功" + data.string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(Throwable e) {
//                        ILog.e("失败" + e.toString());
//                    }
//                });

                IHttpManager.doUploadImageParams(file1, new IHttpManager.IResponseListener<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody data) {
                        try {
                            ILog.e("成功" + data.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        ILog.e("失败" + e.toString());
                    }
                });











                break;
            default:
                break;
        }
    }


    @Override
    protected View provideEmptyView() {
        return null;
    }

    @Override
    protected View provideErrorView() {
        return null;
    }

}

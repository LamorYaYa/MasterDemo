package com.master.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.master.app.R;
import com.master.app.dialog.ICustomDialog;
import com.master.app.permission.PermissionHelp;
import com.master.app.permission.PermissionHelpCallBack;

/**
 * Create By Master
 * On 2018/11/27 18:08
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements PermissionHelp.PermissionCallback {

    private P presenter;
    private RelativeLayout iContainerView;
    private RelativeLayout iEmptyView;
    private RelativeLayout iErrorView;
    private ICustomDialog iCustomDialog;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }

        if (presenter == null) {
            throw new NullPointerException("presenter not null!");
        }

        presenter.attachView((V) this);
        setContentView(R.layout.base_container_layout);

        iContainerView = findViewById(R.id.container_view);
        iEmptyView = findViewById(R.id.empty_view);
        iErrorView = findViewById(R.id.error_view);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        try {
            View containerView = LayoutInflater.from(this).inflate(createContentView(), null);
            iContainerView.addView(containerView, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        View emptyView = provideEmptyView();
        if (emptyView != null) {
            iEmptyView.addView(emptyView, layoutParams);
        }


        View errorView = provideErrorView();
        if (errorView != null) {
            iErrorView.addView(errorView, layoutParams);
        }

        initView();
        initData();

    }


    public P getPresenter() {
        return presenter;
    }

    public void showContanterView() {
        if (this instanceof MultiStateLayout) {
            iContainerView.setVisibility(View.VISIBLE);
            iEmptyView.setVisibility(View.GONE);
            iErrorView.setVisibility(View.GONE);
        }
    }

    public void showEmptyView() {
        if (this instanceof MultiStateLayout) {
            iContainerView.setVisibility(View.GONE);
            iEmptyView.setVisibility(View.VISIBLE);
            iErrorView.setVisibility(View.GONE);
        }

    }

    public void showErrorView() {
        if (this instanceof MultiStateLayout) {
            iContainerView.setVisibility(View.GONE);
            iEmptyView.setVisibility(View.GONE);
            iErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            // 解绑防止内存泄漏
            presenter.detachView();
        }
    }

    //=======================抽象方法=========================
    protected abstract P createPresenter();

    protected abstract int createContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract View provideEmptyView();

    protected abstract View provideErrorView();

    // =======================权限相关============================

    private int iRequestCode;
    private String[] iPermissions;
    private PermissionHelpCallBack iPermissionHelpCallBack;

    protected final void requestPermission(int requestCode, String[] permissions, String rationale, PermissionHelpCallBack permissionHelpCallBack) {
        iRequestCode = requestCode;
        iPermissions = permissions;
        iPermissionHelpCallBack = permissionHelpCallBack;

        PermissionHelp.with(this)
                .addRequestCode(requestCode)
                .permissions(permissions)
                .leftButtonText(android.R.string.cancel)
                .rightButtonText(android.R.string.ok)
                .rationale(rationale)
                .request();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelp.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * 打开设置界面回调接收
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionHelp.SETTINGS_REQUEST_CODE) {
            if (PermissionHelp.hasPermissions(this, iPermissions)) {
                onPermissionGranted(iRequestCode, iPermissions);
            } else {
                onPermissionDenied(iRequestCode, iPermissions);
            }
        }

    }

    @Override
    public void onPermissionGranted(int requestCode, String... perms) {
        if (iPermissionHelpCallBack != null) {
            iPermissionHelpCallBack.onPermissionGranted(requestCode, perms);
        }
    }

    @Override
    public void onPermissionDenied(final int requestCode, final String... perms) {
        // TODO 检查一遍是否是禁止并且不再提醒这种，如果是这种就弹框去设置
        if (PermissionHelp.checkDeniedPermissionsNeverAskAgain(this, "授权啊,不授权没法用啊,去设置里授权啊,不会我教你啊.", android.R.string.cancel, android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPermissionHelpCallBack != null) {
                    iPermissionHelpCallBack.onPermissionDenied(requestCode, perms);
                }
            }
        }, perms)) {
            return;
        }

        if (iPermissionHelpCallBack != null) {
            iPermissionHelpCallBack.onPermissionDenied(requestCode, perms);
        }

    }


    public void showLoadingDialog() {
        if (iCustomDialog == null) {
            iCustomDialog = new ICustomDialog.Builder(this)
                    .setLayoutResId(R.layout.test_dialog_layout)
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
                    .build();
        }
        iCustomDialog.show();
    }

    public void hideLoadingDialog() {
        iCustomDialog.dismiss();
    }


}

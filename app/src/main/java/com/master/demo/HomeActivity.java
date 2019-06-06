package com.master.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.master.app.R;
import com.master.app.util.NetWorkUtils;
import com.master.app.view.DotView;
import com.master.app.view.LoginButton;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SwitchFragment switchFragment;
    private LoginButton loginButton;

    private BottomNavigationView bottomNavigationView;

//    private DotView[] dotViews = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        switchFragment = new SwitchFragment(getSupportFragmentManager());
        loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        initUnReadMessageViews();// 设置小红点








    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bottom_navigation_view_1:
                if (switchFragment != null) {
                    switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME_FRAGMENT_ONE);
                }

//                new ICustomDialog.Builder(this)
//                        // 设置布局
//                        .setLayoutResId(R.layout.network_request_loading)
//                        // 点击空白是否消失
//                        .setCanceledOnTouchOutside(false)
//                        // 点击返回键是否消失
//                        .setCancelable(false)
//                        // 设置Dialog的绝对位置
//                        .setDialogPosition(Gravity.CENTER)
//                        // 设置自定义动画
//                        .setAnimationResId(R.style.CenterAnimation)
//                        // 设置监听ID
//                        .setListenedItems(new int[]{R.id.loading})
//                        // 设置回掉
//                        .setOnDialogItemClickListener(new ICustomDialog.OnDialogItemClickListener() {
//                            @Override
//                            public void onDialogItemClick(ICustomDialog thisDialog, View clickView) {
//                                ((RotateLoading) clickView).stop();
//                                thisDialog.dismiss();
//
//                            }
//                        })
//                        .build()
//                        .show();


                return true;
            case R.id.bottom_navigation_view_2:
                if (switchFragment != null) {
                    switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME_FRAGMENT_TWO);
                }
                return true;
            case R.id.bottom_navigation_view_3:
                if (switchFragment != null) {
                    switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME_FRAGMENT_THREE);
                }
                return true;
            case R.id.bottom_navigation_view_4:
                if (switchFragment != null) {
                    switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME_FRAGMENT_FOUR);
                }
                return true;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        loginButton.startAnim();

        bottomNavigationView.getMenu().getItem(1).setChecked(true);// 设置底部选中
        onNavigationItemSelected(bottomNavigationView.getMenu().getItem(1));




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginButton.endAnim();
                loginButton.setText("是否走代理:" + NetWorkUtils.isWifiProxy(HomeActivity.this));
            }
        }, 3000);


    }


    /**
     * 添加小红点
     * 添加的位置还没有确定。
     */
    private void initUnReadMessageViews() {

        BottomNavigationMenuView menuView = null;

        for (int i = 0; i < bottomNavigationView.getChildCount(); i++) {
            View child = bottomNavigationView.getChildAt(i);
            if (child instanceof BottomNavigationMenuView) {
                menuView = (BottomNavigationMenuView) child;
                break;
            }
        }

        if (null != menuView) {
            View[] dotViews = new View[menuView.getChildCount()];
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView.LayoutParams params = new BottomNavigationItemView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.gravity = Gravity.RIGHT;
//                params.topMargin = 12;
//                params.rightMargin = 50;

                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);

                DotView dotView = new DotView(this);
                itemView.addView(dotView, params);

                if (i < menuView.getChildCount()) {
                    dotView.setShowNum(66);
                }
                dotViews[i] = dotView;
            }


        }


    }


}

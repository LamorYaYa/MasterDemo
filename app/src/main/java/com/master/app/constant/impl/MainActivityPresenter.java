package com.master.app.constant.impl;

import com.master.app.base.BasePresenter;
import com.master.app.constant.MainActivityConstant;

/**
 * Create By Master
 * On 2018/11/29 17:48
 */
public class MainActivityPresenter extends BasePresenter<MainActivityConstant.MainActivityView> implements MainActivityConstant.MainActivityPresenter {
    @Override
    public void userLogin(String userName, String passWord) {
        getView().loginFail(new NullPointerException("1111111111"));
    }
}

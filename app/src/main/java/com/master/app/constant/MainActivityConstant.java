package com.master.app.constant;

import com.master.app.base.BaseView;

/**
 * Create By Master
 * On 2018/11/29 17:47
 */
public interface MainActivityConstant {

    interface MainActivityView extends BaseView {

        void loginSuccess();
        void loginFail(Exception e);

    }

    interface MainActivityPresenter {

        void userLogin(String userName, String passWord);


    }

}

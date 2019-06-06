package com.master.demo;

import android.support.annotation.IntDef;
import android.support.v4.app.FragmentManager;

/**
 * Create By Master
 * On 2019/5/7 20:24
 */
public class SwitchFragment {

    @IntDef({
            FRAGMENT_TYPE.APP_HOME_FRAGMENT_ONE,
            FRAGMENT_TYPE.APP_HOME_FRAGMENT_TWO,
            FRAGMENT_TYPE.APP_HOME_FRAGMENT_THREE,
            FRAGMENT_TYPE.APP_HOME_FRAGMENT_FOUR})
    public @interface FRAGMENT_TYPE {
        int APP_HOME_FRAGMENT_ONE = 0x01;
        int APP_HOME_FRAGMENT_TWO = 0x02;
        int APP_HOME_FRAGMENT_THREE = 0x03;
        int APP_HOME_FRAGMENT_FOUR = 0x04;
    }


    public SwitchFragment(FragmentManager manager) {

    }

    public void chooseFragment(@FRAGMENT_TYPE int type) {

    }

}

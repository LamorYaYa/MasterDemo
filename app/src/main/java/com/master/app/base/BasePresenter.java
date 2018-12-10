package com.master.app.base;

/**
 * Create By Master
 * On 2018/11/27 18:09
 */
public abstract class BasePresenter<T extends BaseView> {

    private T iView;

    public void attachView(T view) {
        this.iView = view;
    }

    public void detachView() {
        this.iView = null;
    }

    public T getView() {
        return iView;
    }
}

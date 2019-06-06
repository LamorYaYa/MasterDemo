package com.master.demo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.master.app.IApplication;
import com.master.app.base.MultiStateLayout;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Create By Master
 * On 2019/5/7 18:18
 */
public class WelcomePageActivity extends AppCompatActivity implements MultiStateLayout {

    private static final int COUNT_DOWN = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(COUNT_DOWN)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return COUNT_DOWN - aLong;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        // TODO 是否需要更新页面数据  (3 2 1 跳过...)
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // 跳转主页面
                        Intent intent = new Intent(WelcomePageActivity.this, HomeActivity.class);
                        startActivity(intent);
                        IApplication.getInstance().getActivityStack().finishActivity(WelcomePageActivity.this);
                    }
                });


    }
}

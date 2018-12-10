package com.master.app.http;

import com.master.app.util.ILog;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Create By Master
 * On 2018/11/27 18:25
 */
public class IOkHttpClient {

    private static final int TIMEOUT = 10;
    private static OkHttpClient mOkHttpClient;

    private IOkHttpClient() {

    }

    private static final class IOkHttpClientHolder {
        private static final IOkHttpClient INSTANCE = new IOkHttpClient();
    }

    public static IOkHttpClient getInstance() {
        return IOkHttpClientHolder.INSTANCE;
    }


    public OkHttpClient getOkHttpClient() {

        if (mOkHttpClient != null) {
            return mOkHttpClient;
        }

        IHttpCommonInterceptor iHttpCommonInterceptor = new IHttpCommonInterceptor.Builder()
                .addHeaderParams("deviceId", UUID.randomUUID().toString())
                .addParams("Test", "TestParams")
                .build();


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                ILog.e(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(iHttpCommonInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        return mOkHttpClient;
    }

}

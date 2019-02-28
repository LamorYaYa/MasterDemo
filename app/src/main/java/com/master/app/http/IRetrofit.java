package com.master.app.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create By Master
 * On 2018/11/27 18:25
 */
public class IRetrofit {

    private static final String URL = "http://app.xizhongshop.com/";
//    private static final String URL = "https://api.bbbearmall.com/";

    public static <T> T createApi(Class<T> cls) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(IOkHttpClient.getInstance().getOkHttpClient())
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(cls);

    }
}
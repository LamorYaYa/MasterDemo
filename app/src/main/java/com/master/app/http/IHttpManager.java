package com.master.app.http;

import java.io.File;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Create By Master
 * On 2018/11/27 18:24
 */
public class IHttpManager {

    public static <T> void doRequest(Observable<T> observable, final IResponseListener<T> listener) {

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        if (listener != null) {
                            listener.onSuccess(t);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onFail(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static <T> void doRequestCommon(Observable<HttpReseult<T>> observable, final IResponseListener<T> listener) {

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<T>())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        if (listener != null) {
                            listener.onSuccess(t);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onFail(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void doUploadImage(File file, final IResponseListener<ResponseBody> listener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        doRequest(IRetrofit.createApi(HttpService.class).uploadImage(part), listener);
    }

    public static void doUploadImages(File[] files, final IResponseListener<ResponseBody> listener) {
        MultipartBody.Part[] parts = new MultipartBody.Part[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts[i] = part;
        }

        doRequest(IRetrofit.createApi(HttpService.class).uploadImages(parts), listener);
    }

    public static void doUploadImageParams(File file, final IResponseListener<ResponseBody> listener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", UUID.randomUUID().toString())
                .addFormDataPart("deviceId", UUID.randomUUID().toString())
                .addFormDataPart("file", file.getName(), requestBody)
                .build();

        doRequest(IRetrofit.createApi(HttpService.class).uploadImages(multipartBody), listener);
    }

    private static class HttpResultFunc<T> implements Function<HttpReseult<T>, T> {
        @Override
        public T apply(HttpReseult<T> httpReseult) throws Exception {
            if (httpReseult.getCode() != 1) {
                throw new ApiException("code == " + httpReseult.getCode() + "\n+message == " + httpReseult.getMessage());
            }
            return httpReseult.getData();
        }
    }

    private static class ApiException extends Exception {
        public ApiException(String message) {
            super(message);
        }
    }


    public interface IResponseListener<T> {
        void onSuccess(T data);

        void onFail(Throwable e);
    }
}

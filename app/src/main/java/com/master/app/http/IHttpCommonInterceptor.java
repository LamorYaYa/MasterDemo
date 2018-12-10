package com.master.app.http;

import com.master.app.util.ILog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Create By Master
 * On 2018/11/27 18:26
 */
public class IHttpCommonInterceptor implements Interceptor {

    private Map<String, String> mHeaderParamsMap = new HashMap<>();
    private Map<String, String> mParamsMap = new HashMap<>();

    public IHttpCommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        ILog.e("IHttpCommonInterceptor 添加统一参数");

        Request oldRequest = chain.request();
        String method = oldRequest.method();
        RequestBody requestBody = oldRequest.body();

        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(method, requestBody);
        // 添加统一消息头
        if (mHeaderParamsMap.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                requestBuilder.addHeader(params.getKey(), params.getValue());
            }
        }

        // 添加统一参数
        if (mParamsMap.size() > 0) {
            // TODO 屏蔽上传文件是添加参数
            if (method.equals("POST")) {

                if (requestBody instanceof MultipartBody) {
////                    requestBody.
//                    RequestBody rb = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("name", name)
//                            .addFormDataPart("psd", psd)
//                            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
//                            .build();
//
//                    requestBuilder.post(requestBody);

//                    MultipartBody.Builder builder = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("TTTTTTT", "TTTTTTT")
//                            .addFormDataPart("AAAAAAA", "AAAAAAA");
//
//                    requestBuilder.post(builder.build());


                } else {
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();

                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    String oldParamsJson = buffer.readUtf8();

                    Map<String, String> rootMap = stringToMap(oldParamsJson);

                    for (String key : rootMap.keySet()) {
                        formBodyBuilder.addEncoded(key, rootMap.get(key) + "");
                    }

                    for (Map.Entry<String, String> entry : mParamsMap.entrySet()) {
                        formBodyBuilder.add(entry.getKey(), entry.getValue());
                    }
                    RequestBody formBody = formBodyBuilder.build();
                    requestBuilder.post(formBody);
                }
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    private Map<String, String> stringToMap(String oldParamsJson) {
        Map<String, String> mHashMap = new HashMap();
        String[] mapData = null;
        String[] keyValue = null;

        if (null != oldParamsJson && oldParamsJson.length() > 0) {
            mapData = oldParamsJson.split("&");
            for (String content : mapData) {
                try {
                    keyValue = content.split("=");
                    mHashMap.put(keyValue[0], keyValue[1]);
                } catch (Exception e) {
                }
            }
        }
        return mHashMap;
    }

    public static class Builder {
        IHttpCommonInterceptor iHttpCommonInterceptor;

        public Builder() {
            iHttpCommonInterceptor = new IHttpCommonInterceptor();
        }

        // 添加公共消息头

        public Builder addHeaderParams(String key, String value) {
            iHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        // 添加公共参数

        public Builder addParams(String key, String value) {
            iHttpCommonInterceptor.mParamsMap.put(key, value);
            return this;
        }

        public Builder addParams(String key, int value) {
            return addParams(key, String.valueOf(value));
        }

        public Builder addParams(String key, float value) {
            return addParams(key, String.valueOf(value));
        }

        public Builder addParams(String key, long value) {
            return addParams(key, String.valueOf(value));
        }

        public Builder addParams(String key, double value) {
            return addParams(key, String.valueOf(value));
        }

        public IHttpCommonInterceptor build() {
            return iHttpCommonInterceptor;
        }

    }


}

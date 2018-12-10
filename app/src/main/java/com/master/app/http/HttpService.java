package com.master.app.http;

import com.master.app.Beans;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Create By Master
 * On 2018/11/27 18:25
 */
public interface HttpService {

    @FormUrlEncoded
    @POST("gginterface/api/sendVerifySMS.html")
    Observable<Beans> sendCode(@FieldMap() Map<String, String> params);

//    @FormUrlEncoded
//    @POST("gginterface/api/sendVerifySMS.html")
//    Observable<HttpReseult<Beans>> sendCode(@FieldMap() Map<String, String> params);


    @POST("gginterface/api/sendVerifySMS.html")
    Observable<Beans> sendCode();


    // 上传单张图片
    @Multipart
    @POST("api/member/updateMemberIcon")
    Observable<ResponseBody> uploadImage(@Part MultipartBody.Part file);


    // 上传多张图片
    @Multipart
    @POST("api/member/updateMemberIcon")
    Observable<ResponseBody> uploadImages(@Part MultipartBody.Part[] files);


    // 上传一张图片和参数
    @POST("api/member/updateMemberIcon")
    Observable<ResponseBody> uploadImages(@Body RequestBody body);


}

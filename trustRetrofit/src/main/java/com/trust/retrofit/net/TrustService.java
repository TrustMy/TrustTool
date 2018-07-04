package com.trust.retrofit.net;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Trust on 2018/6/28.
 * retrofit 请求接口
 */

public interface TrustService {

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String,Object> params);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String,Object> params);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String,Object> params);

    //上传
    @POST
    @Multipart
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);

    //下载直接到内存 需要@Streaming
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String,Object> params);

    //原始数据
    @POST
    Observable<String> postRaw(@Url String url,@Body RequestBody body);

    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<String> deleteRaw(@Url String url, @Body RequestBody body);
}

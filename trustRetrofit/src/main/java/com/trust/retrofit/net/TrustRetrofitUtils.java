package com.trust.retrofit.net;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Trust on 2018/6/28.
 */

public class TrustRetrofitUtils {
    private final HashMap<String,Object> PARAMS;
    private final String URL;
    private final RequestBody BODY;

    private final File File;
    public static final int POST_RAW = 0,PUT_RAW = 1,DELETE_RAW = 2;
    public static final int TRUST_RETROFIT = 0,TRUST_RETROFIT_SSL = 1,TRUST_RETROFIT_SSL_TRUST_ALL = 2;
    private int retrofitType = 0;
    public TrustRetrofitUtils(HashMap<String, Object> params,
                              String url,
                              RequestBody body,
                              java.io.File file,
                              int retrofitType) {
        PARAMS = params;
        URL = url;
        BODY = body;
        File = file;
        this.retrofitType = retrofitType;
    }

    public static RetrofitBuilder create(){
        return new RetrofitBuilder();
    }


    private Observable<String> request(HttpMethod method){
        TrustService trustService;
        switch (retrofitType) {
            case TRUST_RETROFIT:
                trustService = TrustRetrofitCreator.getTrustService();
                break;
            case TRUST_RETROFIT_SSL:
                trustService = TrustRetrofitCreator.getTrustServiceSSL();
                break;
            case TRUST_RETROFIT_SSL_TRUST_ALL:
                trustService = TrustRetrofitCreator.getTrustServiceSSLTrustAll();
                break;
                default:
                    trustService = TrustRetrofitCreator.getTrustService();
                    break;
        }
        Observable<String> observable = null;

        switch (method){
            case GET:
                observable = trustService.get(URL,PARAMS);
                break;
            case POST:
                observable = trustService.post(URL,PARAMS);
                break;
            case PUT:
                observable = trustService.put(URL,PARAMS);
                break;
            case DELETE:
                observable = trustService.delete(URL,PARAMS);
                break;
            case UPLOAD:
                observable = trustService.upload(URL,MultipartBody.Part.
                        createFormData("file",File.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"),File)));
                break;
            case POST_RAW:
                observable = trustService.postRaw(URL,BODY);
                break;
            case PUT_RAW:
                observable = trustService.putRaw(URL,BODY);
                break;
            case DELETE_RAW:
                observable = trustService.deleteRaw(URL,BODY);
                    break;
            default:
                    break;
        }
        return observable;
    }


    //各种请求
    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        return request(HttpMethod.POST);
    }

    public final Observable<String> put() {
        return request(HttpMethod.PUT);
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> putRaw() {
        return request(HttpMethod.PUT_RAW);
    }

    public final Observable<String> postRaw() {
        return request(HttpMethod.POST_RAW);
    }

    public final Observable<String> deleteRaw() {
        return request(HttpMethod.DELETE_RAW);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

}

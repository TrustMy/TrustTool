package com.trust.retrofit.net;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Trust on 2018/6/28.
 */

public class TrustRetrofitUtils {
    private final HashMap<String,Object> PARAMS;
    private final String URL;
    private final RequestBody BODY;

    private final File File;


    public TrustRetrofitUtils(HashMap<String, Object> params,
                              String url,
                              RequestBody body,
                              java.io.File file) {
        PARAMS = params;
        URL = url;
        BODY = body;
        File = file;
    }

    public static RetrofitBuilder create(){
        return new RetrofitBuilder();
    }


    private Observable<String> request(HttpMethod method){
        final  TrustService trustService = TrustRetrofitCreator.getTrustService();
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

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

}

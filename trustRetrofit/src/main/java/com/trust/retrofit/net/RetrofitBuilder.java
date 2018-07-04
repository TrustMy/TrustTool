package com.trust.retrofit.net;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.trust.retrofit.net.TrustRetrofitUtils.TRUST_RETROFIT;

/**
 * Created by Trust on 2018/6/28.
 */

public class RetrofitBuilder {
    private HashMap<String,Object> mParams;
    private  String mUrl;
    private RequestBody mBody;
    //上传下载
    private File mFile;


    RetrofitBuilder(){

    }
    public final RetrofitBuilder url(String url){
        this.mUrl=url;
        return this;
    }
    public final RetrofitBuilder params(HashMap<String,Object> params){
        this.mParams=params;
        return this;
    }

    public final RetrofitBuilder body(String raw){
        this.mBody=RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RetrofitBuilder body(String header,String raw){
        this.mBody=RequestBody.create(
                MediaType.parse(header),raw);
        return this;
    }
    //上传
    public final RetrofitBuilder file(File file){
        this.mFile=file;
        return this;
    }
    public final RetrofitBuilder file(String file){
        this.mFile=new File(file);
        return this;
    }

    public final TrustRetrofitUtils build(){
        return new TrustRetrofitUtils(mParams,mUrl,mBody,mFile,TRUST_RETROFIT);
    }

    public final TrustRetrofitUtils build(int type){
        return new TrustRetrofitUtils(mParams,mUrl,mBody,mFile,type);
    }
}

package com.trust.demo.basis.base.model;

import com.trust.demo.basis.trust.utils.TrustAnalysis;

import java.util.HashMap;

import static com.trust.retrofit.net.TrustRetrofitUtils.TRUST_RETROFIT_SSL;

/**
 * Created by Trust on 2018/6/29.
 *
 */

public abstract class TrustHttpRequestModel {
    protected int type = TRUST_RETROFIT_SSL;

    public abstract <T> void requestGet(String url, HashMap<String,Object> params,RequestResultListener<T> requestResultListener ,Class<T> clasz);
    public abstract <T> void requestPost(String url, HashMap<String,Object> params,RequestResultListener<T> requestResultListener ,Class<T> clasz);
    public abstract <T> void requestPut(String url, HashMap<String,Object> params,RequestResultListener<T> requestResultListener ,Class<T> clasz);
    public abstract <T> void requestDelete(String url, HashMap<String,Object> params,RequestResultListener<T> requestResultListener ,Class<T> clasz);
    public abstract <T> void requestJsonParams(String url,int paramsType ,HashMap<String,Object> params,RequestResultListener<T> requestResultListener ,Class<T> clasz);
    public abstract <T> void request(String url,String header,int paramsType ,HashMap<String,Object> params,RequestResultListener<T> requestResultListener ,Class<T> clasz);
    public abstract <T> void upload(String url, String filePath, RequestResultListener<T> requestResultListener , Class<T> clasz);

    protected <T> T getTrustAnalysis(String json,Class clasz){
        return TrustAnalysis.resultTrustBean(json,clasz);
    }

    protected void setType(int type){
        this.type  = type;
    }


}

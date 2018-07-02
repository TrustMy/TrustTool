package com.trust.demo.basis.base.model;

import com.trust.demo.basis.trust.utils.TrustAnalysis;

import java.util.HashMap;

/**
 * Created by Trust on 2018/6/29.
 */

public abstract class TrustHttpRequestModel {
    public abstract <T> void requestGet(String url, HashMap<String,Object> params,RequetResultListener<T> requetResultListener,Class<T> t);
    public abstract void requestPost(String url, HashMap<String,Object> params,RequetResultListener requetResultListener);
    public abstract void requestPut(String url, HashMap<String,Object> params,RequetResultListener requetResultListener);
    public abstract void requestDelete(String url, HashMap<String,Object> params,RequetResultListener requetResultListener);

    protected <T>  T  TrustAnalysis( String json,Class clasz){
        if (clasz.equals(String.class)) {
            return (T) json;
        }
        return TrustAnalysis.resultTrustBean(json,clasz);
    }
}

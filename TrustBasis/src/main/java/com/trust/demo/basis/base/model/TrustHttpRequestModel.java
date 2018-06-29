package com.trust.demo.basis.base.model;

import java.util.HashMap;

/**
 * Created by Trust on 2018/6/29.
 */

public abstract class TrustHttpRequestModel {
    public abstract void requestGet(String url, HashMap<String,Object> params);
    public abstract void requestPost(String url, HashMap<String,Object> params);
    public abstract void requestPut(String url, HashMap<String,Object> params);
    public abstract void requestDelete(String url, HashMap<String,Object> params);
}

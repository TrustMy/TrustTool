package com.trust.demo.basis.base.model;

/**
 * Created by Trust on 2018/6/29.
 */

public interface TrustRetrofitListener <V>{
    void requestSuccess(V v);
    void requestError(Throwable throwable ,String error);
}

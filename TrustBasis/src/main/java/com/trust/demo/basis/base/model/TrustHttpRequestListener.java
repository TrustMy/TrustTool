package com.trust.demo.basis.base.model;

/**
 * Created by Trust on 2018/6/29.
 */

public interface TrustHttpRequestListener <M extends TrustHttpRequestModel> {
    M getRequestModule();
    M createRequestModule();
    void setRequestMidule(M model);
}

package com.trust.demo.basis.base.model;

/**
 * Created by Trust on 2018/7/2.
 */

public interface RequetResultListener <T>{
    void resultSuccess(T bean);
    void resultError(Throwable throwable);
}

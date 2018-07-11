package com.trust.demo.basis.base.model;

/**
 * Created by Trust on 2018/7/3.
 */

public interface RequestResultListener <T>{
    void resultSuccess(T bean);
    void resultError(Throwable e);
    void netWorkError(String msg);
}

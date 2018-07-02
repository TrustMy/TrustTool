package com.trust.demo.trusttool.mvptest.testrequest;

import com.trust.demo.basis.base.model.RequetResultListener;
import com.trust.demo.basis.base.model.TrustModel;
import com.trust.demo.basis.trust.utils.TrustLogUtils;
import com.trust.demo.trusttool.mvptest.LoginModelInterface;
import com.trust.demo.trusttool.mvptest.OnLoginFinushedInterface;
import com.trust.retrofit.config.ProjectInit;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by Trust on 2018/6/29.
 */

public class RetrofitModuleTest extends TrustModel<TestRequest> implements LoginModelInterface{

    public RetrofitModuleTest() {
    }

    @NotNull
    @Override
    public TestRequest createRequestModule() {
        return new TestRequest();
    }

    @Override
    public void login(String name, String pwd, final OnLoginFinushedInterface onLoginFinushedInterface) {
        getRequestModule().requestGet("subscriptions#/subscriptions/12070983/user",
                null, new RequetResultListener<String>() {
                    @Override
                    public void resultSuccess(String bean) {
                        onLoginFinushedInterface.resultLogin(bean);
                    }

                    @Override
                    public void resultError(Throwable throwable) {
                        onLoginFinushedInterface.error(throwable.getMessage());
                    }
                },String.class
        );
    }




}

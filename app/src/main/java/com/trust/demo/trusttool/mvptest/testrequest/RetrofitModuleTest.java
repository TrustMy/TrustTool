package com.trust.demo.trusttool.mvptest.testrequest;

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
    public void login(String name, String pwd, OnLoginFinushedInterface onLoginFinushedInterface) {
        getRequestModule().requestGet("subscriptions#/subscriptions/12070983/user",null);
        TrustLogUtils.d("你点击了登陆:"+name+"|pwd:"+pwd);
        onLoginFinushedInterface.resultLogin("你点击了登陆o,在TestModule");
    }
}

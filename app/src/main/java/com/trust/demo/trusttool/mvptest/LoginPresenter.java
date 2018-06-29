package com.trust.demo.trusttool.mvptest;

import com.trust.demo.basis.base.presenter.TrustPresenters;
import com.trust.demo.trusttool.mvptest.testrequest.RetrofitModuleTest;

/**
 * Created by Trust on 2018/6/25.
 */

public class LoginPresenter extends TrustPresenters<LoginView> implements OnLoginFinushedInterface,LoginPresenterInterface{
    private LoginModelInterface loginMpde;

    public LoginPresenter() {
        this.loginMpde = new RetrofitModuleTest();
    }

    @Override
    public void login(String name, String pwd) {
        loginMpde.login(name,pwd,this);
    }

    @Override
    public void resultLogin(String msg) {
        getView().logingStatus(msg);
    }

    @Override
    public void error(String msg) {
        getView().logingStatus(msg);
    }
}

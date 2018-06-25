package com.trust.demo.trusttool.mvptest;

import com.trust.demo.basis.base.presenter.TrustPresenter;

/**
 * Created by Trust on 2018/6/25.
 */

public class LoginPresenter extends TrustPresenter<LoginView> implements OnLoginFinushedInterface,LoginPresenterInterface{
    private LoginView loginView;
    private LoginModelInterface loginMpde;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        this.loginMpde = new LoginMpde();
    }

    @Override
    public void login(String name, String pwd) {
        loginMpde.login(name,pwd,this);
    }

    @Override
    public void resultLogin(String msg) {
        loginView.logingStatus(msg);
    }

    @Override
    public void error(String msg) {
        loginView.logingStatus(msg);
    }
}

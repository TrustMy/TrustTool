package com.trust.demo.trusttool.mvptest;

import com.trust.demo.basis.base.presenter.TrustPresenters;
import com.trust.demo.trusttool.mvptest.testrequest.RetrofitModuleTest;

import java.util.HashMap;

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
        getView().showWaitDialog(null,true,"我是需要显示得tag");
        loginMpde.login(name,pwd,this);
    }

    @Override
    public void checkStatus(HashMap<String, Object> map) {
        getView().showWaitDialog("no msg",false,"我是不需要显示得tag");
        loginMpde.check(map,this);
    }

    @Override
    public void resultLogin(String msg) {
        getView().diassDialog();
        getView().logingStatus(msg);
        getView().showToast("resultLogin  结果已经返回");
    }

    @Override
    public void error(String msg) {
        getView().logingStatus(msg);
    }
}

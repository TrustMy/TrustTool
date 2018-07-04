package com.trust.demo.trusttool.mvptest;

import com.trust.modular.TrustRetrofit;

import java.util.HashMap;

/**
 * Created by Trust on 2018/6/25.
 */

public class LoginMpde implements LoginModelInterface {

    @Override
    public void login(String name, String pwd, OnLoginFinushedInterface onLoginFinushedInterface) {
        if (name != null && pwd != null) {


            onLoginFinushedInterface.resultLogin("登陆成功："+name+"|"+pwd);
        }else{
            onLoginFinushedInterface.error("登陆失败 用户名或密码错误");
        }

    }

    @Override
    public void check(HashMap<String, Object> map, OnLoginFinushedInterface onLoginFinushedInterface) {

    }
}

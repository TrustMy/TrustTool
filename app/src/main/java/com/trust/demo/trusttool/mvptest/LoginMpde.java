package com.trust.demo.trusttool.mvptest;

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
}

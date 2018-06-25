package com.trust.demo.trusttool.mvptest;

/**
 * Created by Trust on 2018/6/25.
 */

public interface LoginModelInterface
{
    void login(String name , String pwd,OnLoginFinushedInterface onLoginFinushedInterface);
}

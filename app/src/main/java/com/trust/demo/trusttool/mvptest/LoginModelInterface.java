package com.trust.demo.trusttool.mvptest;

import java.util.HashMap;

/**
 * Created by Trust on 2018/6/25.
 */

public interface LoginModelInterface
{
    void login(String name , String pwd,OnLoginFinushedInterface onLoginFinushedInterface);
    void check(HashMap<String,Object> map,OnLoginFinushedInterface onLoginFinushedInterface);
}

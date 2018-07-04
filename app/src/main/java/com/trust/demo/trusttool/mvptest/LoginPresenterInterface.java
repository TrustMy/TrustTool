package com.trust.demo.trusttool.mvptest;

import java.util.HashMap;

/**
 * Created by Trust on 2018/6/25.
 */

public interface LoginPresenterInterface {
    void login(String name,String pwd);
    void checkStatus(HashMap<String,Object> map);
}

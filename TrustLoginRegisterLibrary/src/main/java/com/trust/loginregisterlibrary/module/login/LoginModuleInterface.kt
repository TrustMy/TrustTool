package com.trust.loginregisterlibrary.module.login

import com.trust.loginregisterlibrary.bean.ResultBean
import com.trust.loginregisterlibrary.bean.ResultUserInfoBean

/**
 * Created by Trust on 2018/7/13.
 */
interface LoginModuleInterface {
    fun  login(map:HashMap<String,Any>,loginResultInterface: ModuleResultInterface<ResultBean>)
    fun  updateApp(loginResultInterface: ModuleResultInterface<String>)
    fun  userInfo(map:HashMap<String,Any>, loginResultInterface: ModuleResultInterface<ResultUserInfoBean>)
}
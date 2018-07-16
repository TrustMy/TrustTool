package com.trust.loginregisterlibrary.module.login

/**
 * Created by Trust on 2018/7/13.
 */
interface LoginModuleInterface <T>{
    fun login(map:HashMap<String,Any>,loginResultInterface: ModuleResultInterface<T>)
}
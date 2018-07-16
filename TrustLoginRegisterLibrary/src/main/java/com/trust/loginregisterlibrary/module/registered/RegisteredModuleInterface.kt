package com.trust.loginregisterlibrary.module.registered

import com.trust.loginregisterlibrary.module.login.ModuleResultInterface

/**
 * Created by Trust on 2018/7/16.
 */
interface RegisteredModuleInterface <T>{
    fun checkUserInfo(parameter:HashMap<String,Any>?,loginResultInterface: ModuleResultInterface<T>)
    fun registered(param:HashMap<String,Any>?,loginResultInterface: ModuleResultInterface<T>)
}
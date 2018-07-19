package com.trust.loginregisterlibrary.module.resetpwd

import com.trust.demo.basis.base.ModuleResultInterface

/**
 * Created by Trust on 2018/7/17.
 */
interface ResetPwdModuleInterface <T> {
    fun getVerificationCode(params:HashMap<String,Any>?,loginResultInterface: ModuleResultInterface<T>)
    fun resetPwd(params: HashMap<String, Any>?,loginResultInterface: ModuleResultInterface<T>)
}
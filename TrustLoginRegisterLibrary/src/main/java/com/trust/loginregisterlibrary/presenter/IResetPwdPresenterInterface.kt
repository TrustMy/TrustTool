package com.trust.loginregisterlibrary.presenter


/**
 * Created by Trust on 2018/7/17.
 */
interface IResetPwdPresenterInterface {
    fun getVerificationCode(params:HashMap<String,Any>?)
    fun resetPwd(params: HashMap<String, Any>?)
}
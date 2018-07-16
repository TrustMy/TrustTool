package com.trust.loginregisterlibrary.presenter

/**
 * Created by Trust on 2018/7/16.
 */
interface IRegisteredPresenterInterface {
    fun checkUserInfo(parameter:HashMap<String,Any>?)
    fun registered(param:HashMap<String,Any>?)
}
package com.trust.paylibrary.ipresenter

import android.app.Activity

/**
 * Created by Trust on 2018/7/20.
 */
interface IPayPresenterInterface {
    fun getPayDate(params:HashMap<String,Any>)

    fun getPayStatus(activity: Activity,result: MutableMap<String, String>)
}
package com.trust.demo.trusttool.mvptest

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.HashMap

/**
 * Created by Trust on 2018/6/5.
 */
 interface ApiServer {
    @GET("subscriptions#/subscriptions/12070983/user")
    fun login(@QueryMap param: HashMap<String, String>): Observable<ResponseBody>


    @GET("rest/payOrder")
    fun payOrder (@QueryMap param: HashMap<String, String>): Observable<ResponseBody>
}
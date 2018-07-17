package com.trust.loginregisterlibrary.callback

import com.trust.loginregisterlibrary.bean.ResultUserInfoBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * Created by Trust on 2018/7/17.
 */
interface AppServer {
    @GET("register/applySmsCode/{Phone}")
    fun getVerificationCode(@Path("Phone") phone:String): Observable<ResponseBody>
    @GET("rest/customer/{Phone}")
    fun getUserInfo(@Path("Phone") phone:String): Observable<ResultUserInfoBean>


}
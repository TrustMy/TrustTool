package com.trust.modular

import okhttp3.ResponseBody

/**
 * Created by Trust on 2018/4/10.
 */
abstract class TrustRetrofitCallBack <T>{

    abstract fun accept(t:T)

    abstract fun failure(error:Throwable)

    abstract fun failure(error: ResponseBody)


}
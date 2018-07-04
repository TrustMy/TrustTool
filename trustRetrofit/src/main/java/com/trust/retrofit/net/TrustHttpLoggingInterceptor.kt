package com.trust.modular

import android.util.Log
import com.trust.retrofit.net.TrustRetrofitCreator
import okhttp3.*


/**
 * Created by Trust on 2018/4/10.
 *
 */
class TrustHttpLoggingInterceptor : Interceptor {
    private val TAG = "TrustLoggingInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =  chain.request()
        val t1 = System.nanoTime()
        Log.d(TAG, String.format("发送请求: [%s] %s%n%s",
                request?.url(), chain?.connection(), request?.headers()))

        val t2 =System.nanoTime()
        val response  = chain?.proceed(request)
        val header = response?.header("Content-Type")
        var bodyString = ""
        var contentType: MediaType? = null
        if (response.body() != null) {
            var body = response.body()
            var contentType1 = body?.contentType()
            contentType = response.body()!!.contentType()
            if (header.equals("image/jpeg")) {

            }else{
                bodyString = response.body()!!.string()
            }
        }

        val time = ((t2 - t1) / 1e6)

        Log.d(TAG,String.format("接收返回: [%s] %s%n%s%s",response?.request(), time, response?.headers(),
                bodyString))
        addToken(response?.headers()!!)
        return if (response!!.body() != null && !header.equals("image/jpeg")) {
            // 深坑！
            // 打印body后原ResponseBody会被清空，需要重新设置body
            val body =if (contentType != null) {
                ResponseBody.create(contentType, bodyString)
            }else{
                ResponseBody.create(MediaType.parse(""), bodyString)
            }
            response.newBuilder().body(body).build()
        }else{
            response
        }
    }


    public fun addToken(headers: Headers){
        if (headers.get("Token") != null) {
            TrustRetrofitCreator.addToken(headers.get("Token"))
        }
    }

}
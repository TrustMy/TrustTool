package com.trust.demo.basis.trust.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI



/**
 * Created by Trust on 2018/5/28.
 * 网络检查工具类
 */
class TrustHttpUtils(context: Context) {
    private var context: Context = context
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var trustHttpUtils: TrustHttpUtils?=null
        fun getSingleton(context: Context) : TrustHttpUtils {
            if (trustHttpUtils == null) {
                trustHttpUtils = TrustHttpUtils(context)
            }
            return trustHttpUtils!!
        }
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    @SuppressLint("MissingPermission")
    fun isNetworkAvailable():Boolean{
        var connectivity  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null){
            var info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected){
                if (info.state == NetworkInfo.State.CONNECTED){
                    return  true
                }
            }
        }
        return false
    }

    @SuppressLint("MissingPermission")
            /**
     * 获取网络状态
     *
     * @param context
     * @return one of TYPE_NONE, TYPE_MOBILE, TYPE_WIFI
     * @permission android.permission.ACCESS_NETWORK_STATE
     */
    fun getNetWorkStates(): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
            return -1000//没网
        }

        val type = activeNetworkInfo.type
        when (type) {
            ConnectivityManager.TYPE_MOBILE -> return TYPE_MOBILE//移动数据
            ConnectivityManager.TYPE_WIFI -> return TYPE_WIFI//WIFI
            else -> {
            }
        }
        return -1000
    }



}
package com.trust.demo.basis.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Process
import com.trust.demo.basis.trust.utils.TrustLogUtils
import com.trust.modular.TrustRetrofit
import com.trust.retrofit.config.ProjectInit
import com.trust.svgmapdemo.dialog.CrashHandler

/**
 * Created by Trust on 2018/5/28.
 *
 */
 open class TrustApplication : Application(){

    companion object {
        private var context:Context?=null
        private var handler: Handler?= null
        private var mainThreadId:Int = 0
        private var trustApplication: TrustApplication?=null

        @Synchronized
         fun  getInstance() : TrustApplication {
            return trustApplication!!
        }

        fun getContexts():Context{ return context!! }

        fun getHandlers():Handler{return handler!!}

        fun getMainThreadIds():Int{return mainThreadId}
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        handler = Handler()
        mainThreadId = Process.myTid()
        CrashHandler(this)
//        TrustLogUtils.configLog("onCreate ---------SUCCESS---------")

    }



}
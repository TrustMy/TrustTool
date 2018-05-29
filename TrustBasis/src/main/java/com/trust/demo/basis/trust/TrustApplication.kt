package com.trust.demo.basis.trust

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.trust.demo.basis.trust.utils.TrustLogUtils

/**
 * Created by Trust on 2018/5/28.
 *
 */
open class TrustApplication : Application(){
    protected var context:Context?=null
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var trustApplication: TrustApplication?=null

        @Synchronized
         fun  getInstance() : TrustApplication {
            return trustApplication!!
        }
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        TrustLogUtils.configLog("onCreate ---------SUCCESS---------")
    }

    fun getContexts():Context{ return context!! }


}
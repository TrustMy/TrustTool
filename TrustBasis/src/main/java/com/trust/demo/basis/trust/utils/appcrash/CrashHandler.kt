package com.trust.svgmapdemo.dialog

import android.content.Context
import android.util.Log
import com.trust.demo.basis.base.TrustApplication
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by Trust on 2018/8/21.
 */
class CrashHandler :Thread.UncaughtExceptionHandler {
    private var appApplictaion:TrustApplication? = null
    private var crashHandler:CrashHandler?= null
    private var uncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    constructor(context: Context) {
        this.appApplictaion = context.applicationContext as TrustApplication
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    fun getInstance(context: Context):CrashHandler{
        if (crashHandler == null) {
            crashHandler = CrashHandler(context)
        }
        return  crashHandler!!
    }


    override fun uncaughtException(t: Thread?, e: Throwable?) {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        e!!.printStackTrace(printWriter)
        printWriter.close()
        val msg = stringWriter.toString()
        Log.d("lhh","详细错误日志：$msg")

        ErrorDialogActivity.startActivity(appApplictaion!!.applicationContext,msg)
        System.exit(0)
        uncaughtExceptionHandler!!.uncaughtException(t, e)
    }
}
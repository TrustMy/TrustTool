package com.trust.svgmapdemo.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.demo.basis.R

import kotlinx.android.synthetic.main.dialog_error.*

/**
 * Created by Trust on 2018/8/21.
 */
class ErrorDialog : DialogFragment() {
    private var error_info:String? = null
    companion object {
        var reStartApp:ReStartApp? = null

        fun getInstance(errorMsg:String):ErrorDialog{
            var dialog :ErrorDialog = ErrorDialog()
            val bd = Bundle()
            bd.putString("errorMsg",errorMsg)
            dialog.arguments = bd
            return  dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bd = arguments
        if (bd != null) {
            error_info = bd.getString("errorMsg",null)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater!!.inflate(R.layout.dialog_error,null,false)
        Log.d("lhh","onCreateView")
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_error_msg.text = error_info
        btn_finish.setOnClickListener { dismiss()
            reStartApp?.reStartApp(false) }
        btn_restart_app.setOnClickListener { reStartApp?.reStartApp(true) }

        Log.d("lhh","onViewCreated")
    }



    interface ReStartApp { fun reStartApp(isRestart:Boolean){}}
}
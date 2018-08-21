package com.trust.svgmapdemo.dialog

import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.trust.demo.basis.R

import kotlinx.android.synthetic.main.activity_toast.*

class ErrorDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)
        val stringExtra = intent.getStringExtra("errorMsg")
        val ft = supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        val dialog = ErrorDialog.getInstance(stringExtra)
        dialog.isCancelable = false
        ErrorDialog.reStartApp = object :ErrorDialog.ReStartApp{
            override fun reStartApp(isRestart:Boolean) {
                if (isRestart) {
                    startAPP()
                }else{
                    finish()
                }
            }
        }

//        dialog.show(fragmentManager,"")
        dialog.show(ft,"")
    }


    fun startAPP(){
        val intent  = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
        finish()
    }


    companion object {
        fun startActivity(context: Context,errorMsg:String){
            val intent = Intent(context,ErrorDialogActivity::class.java)
            intent.putExtra("errorMsg",errorMsg)
            context.startActivity(intent)
        }
    }




}

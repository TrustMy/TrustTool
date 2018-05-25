package com.trust.splashactivity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide


import kotlinx.android.synthetic.main.content_splash.*
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Message



class SplashActivity : AppCompatActivity() {
    private val splashHandler:SplashHandler = SplashHandler()
    private val TIME : Long= 10
    private var maxTimer : Int = 10*100//跳转得时间 10s
    private var context= this
    private var isClick:Boolean = false


    private var splashImgUrl:String = "https://b-ssl.duitang.com/uploads/item/201503/02/20150302171324_K3fBi.jpeg"
    private var splashUrl = "https://blog.csdn.net/lyltiger/article/details/51890206"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_splash)
        initData()
        initView()

        changeTimer()
    }

    private fun initView() {
        splash_img.setOnClickListener {
            val uri = Uri.parse(splashUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            isClick = true
        }

        splas_progress_view.setOnClickListener {
            isClick = true
            startActivity()
        }
    }

    private fun initData() {
        Glide.with(this)
                .load(R.drawable.bg1)
                .asBitmap()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(splash_img)
    }

    //改变时间
    private fun changeTimer() {
        Thread(Runnable {
            while (maxTimer >= -1 && !isClick){
                val obtain = Message.obtain()
                obtain.what = maxTimer
                splashHandler.sendMessage(obtain)
                Thread.sleep(TIME)
                maxTimer--
            }
        }).start()
    }


    @SuppressLint("HandlerLeak")
    inner  class SplashHandler :Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val what:Int = msg?.what!!
            context.runOnUiThread {
                if( what >= 0  ){
                    splas_progress_view.setProgress(msg.what.toFloat())
                }else{
                   if (!isClick){
                       startActivity()
                   }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isClick) {
            startActivity()
        }
    }


    private fun startActivity(){
        startActivity(Intent(context,TestActivity::class.java))
        finish()
    }

}

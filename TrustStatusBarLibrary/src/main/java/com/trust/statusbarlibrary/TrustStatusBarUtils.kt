package com.trust.statusbarlibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION






/**
 * Created by Trust on 2018/5/25.
 * 状态栏封装类
 */
class TrustStatusBarUtils (context: Context){
    private val context:Context = context

    companion object {
      @SuppressLint("StaticFieldLeak")
      private var trustStatusBarUtils:TrustStatusBarUtils?=null
        fun getSingleton(context: Context) :TrustStatusBarUtils{
            if (trustStatusBarUtils == null) {
                trustStatusBarUtils = TrustStatusBarUtils(context)
            }
            return trustStatusBarUtils!!
        }
    }


    /**
     * 设置状态栏颜色
     */
    fun setStatusBar(activity: Activity ,color:Int){
        var rootView = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        rootView.setPadding(0,getStatusBarHeight(),0,0)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.window.statusBarColor = color
        }else{
            var decorView:ViewGroup = activity.window.decorView as ViewGroup
            var statusBarView = View(activity)
            var lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight())
            statusBarView.setBackgroundColor(color)
            decorView.addView(statusBarView,lp)
        }
    }


    /**
     * 状态栏透明
     */
    fun setStatusBarTransparent(activity: Activity,titleView:View){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           val window = activity.window
           window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
           val statusBarHeight = getStatusBarHeight()
           titleView.setPadding(0, statusBarHeight, 0, 0)
       }
   }


    /**
     * 隐藏状态栏
     */
    fun hideStatusBar(activity: Activity){
        var window = activity.window
        var flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag,flag)
    }


    /**
     * 通过反射获取状态栏高度
     */
    private fun getStatusBarHeight() :Int{
        var result = 0
        var resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if(resourceId >0){
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
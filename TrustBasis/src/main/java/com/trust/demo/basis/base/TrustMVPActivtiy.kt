package com.trust.demo.basis.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.trust.demo.basis.base.delegate.TrustMvpActivityDelegateImpl
import com.trust.demo.basis.base.delegate.TrustMvpCallback
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView
import com.trust.statusbarlibrary.TrustStatusBarUtils
import java.util.concurrent.TimeUnit

/**
 * Created by Trust on 2018/6/25.
 * MVP Activity
 * 第一重代理 ：代理对象
 * 两个特点:
 * 1：实现目标接口 （可以不要）
 * 2：持有目标对象的引用 （必须）
 *
 * 第二重代理：目标对象
 * 实现目标接口
 */
 abstract class TrustMVPActivtiy <V : TrustView ,P : TrustPresenters<V>>:
        AppCompatActivity(),TrustView ,TrustMvpCallback<V,P>{
    protected var mActivity:Activity? = null
    protected var mContext:Context? = null

    protected abstract fun getLayoutId():Int
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun initData()

    private var mPresenter:P? = null
    private var delegateImpl:TrustMvpActivityDelegateImpl<V,P>? = null

    private fun getDelegateImpl():TrustMvpActivityDelegateImpl<V,P> {
        if (delegateImpl ==  null){
            delegateImpl = TrustMvpActivityDelegateImpl<V,P>(this)
        }
        return delegateImpl!!
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mActivity = this
        mContext = this
//        init()
        getDelegateImpl().onCreate(savedInstanceState)
        initView(savedInstanceState)
        initData()

    }

    override fun onStart() {
        super.onStart()
        getDelegateImpl().onStart()
    }

    override fun onRestart() {
        super.onRestart()
        getDelegateImpl().onRestart()
    }

    override fun onResume() {
        super.onResume()
        getDelegateImpl().onResume()
    }

    override fun onStop() {
        super.onStop()
        getDelegateImpl().onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        getDelegateImpl().onDestroy()
    }

    override fun onPause() {
        super.onPause()
        getDelegateImpl().onPause()
    }



    override fun createPresenter(): P {
        return this.mPresenter!!
    }

    override fun getMvpView(): V {
        return this as V
    }

    override fun getPresenter(): P? {
        return this.mPresenter
    }

    override fun setPresenter(prensent: P) {
        this.mPresenter = prensent
    }

    protected fun startActivityResult(activity: Activity,clasz:Class<*>,code:Int){
        val intent:Intent = Intent(activity,clasz)
        activity.startActivityForResult(intent,code)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onTrustViewActivityResult(requestCode, resultCode, data)
    }

    protected fun baseSetOnClick(v:View,seconds:Long){
        RxView
                .clicks(v)
                .throttleFirst(seconds,TimeUnit.SECONDS)
                .subscribe { baseResultOnClick(v) }
    }

    abstract fun baseResultOnClick(v:View)

    fun setStatusBar(color:Int){
        TrustStatusBarUtils.getSingleton(this).setStatusBarColor(this, color)
    }
}
package com.trust.demo.basis.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trust.demo.basis.base.delegate.TrustMvpActivityDelegate
import com.trust.demo.basis.base.delegate.TrustMvpActivityDelegateImpl
import com.trust.demo.basis.base.delegate.TrustMvpCallback
import com.trust.demo.basis.base.presenter.TrustPresenter
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

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
open abstract class TrustMVPActivtiy <V : TrustView ,P : TrustPresenters<V>>:
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

}
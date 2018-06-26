package com.trust.demo.basis.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trust.demo.basis.base.presenter.TrustPresenter
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/25.
 * MVP Activity
 */
open abstract class TrustMVPActivtiy <V : TrustView ,P : TrustPresenters<V>>: AppCompatActivity(),TrustView {
    protected var mActivity:Activity? = null
    protected var mContext:Context? = null
    protected abstract fun getLayoutId():Int
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun initData()
    //每个页面中间媒介
    protected var presenter:P? = null
    //交给子类实现返回子类自己的媒介
    protected abstract fun createPresenter():P


    fun getPresent():P{return presenter!!}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mActivity = this
        mContext = this
        init()
        initView(savedInstanceState)
        initData()

    }

    //绑定中间媒介和接口
    private fun init(){
        if (this.presenter == null) {
            this.presenter = createPresenter()
        }

        if (this.presenter != null){
            this.presenter!!.attachView((this)as V)
        }
    }

    //解绑
    override fun onDestroy() {
        super.onDestroy()
        if (this.presenter != null) {
            this.presenter!!.detachView()
        }
    }
}
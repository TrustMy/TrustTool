package com.trust.demo.basis.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.trust.demo.basis.R
import com.trust.demo.basis.base.presenter.TrustPresenter
import com.trust.demo.basis.base.presenter.TrustPresenterListener
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/25.
 */
open abstract class TrustMVPActivtiy <V : TrustView ,P : TrustPresenter<V>>: AppCompatActivity() {
    protected var mActivity:Activity? = null
    protected var mContext:Context? = null
    protected abstract fun getLayoutId():Int
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun initData()
    protected var presenter:P? = null
    protected var view:V? = null
    protected abstract fun createPresenter():P
    protected abstract fun createView():V

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

    private fun init(){
        if (this.presenter == null) {
            this.presenter = createPresenter()
        }

        if (this.view == null) {
            this.view = createView()
        }

        if (this.presenter != null && this.view != null){
            this.presenter!!.attachView(this.view!!)
        }
    }
}
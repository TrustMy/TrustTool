package com.trust.demo.basis.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.demo.basis.base.presenter.TrustPresenter
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/26.
 * MvpFragment
 */
abstract class TrustMVPFragment <V : TrustView,P : TrustPresenter<V>>:Fragment() {
    protected var mActivity: Activity? = null
    protected var mContext: Context? = null
    protected abstract fun getLayoutId():Int
    protected abstract fun initView(view:View,savedInstanceState: Bundle?)
    protected abstract fun initData()
    protected var presenter:P? = null
    protected abstract fun createPresenter():P

    fun getPresent():P{return presenter!!}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(getLayoutId(), container, false)
        init()
        initView(view,savedInstanceState)
        initData()
        return view
    }


    private fun init(){
        if (this.presenter == null) {
            this.presenter = createPresenter()
        }
        if (this.presenter != null){
            this.presenter!!.attachView((this)as V)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        this.presenter!!.detachView()
    }
}
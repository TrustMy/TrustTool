package com.trust.demo.basis.base.delegate

import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/27.
 * 第二重代理：代理对象
 * 两个特点
 * 1：实现目标接口
 * 2：持有目标对象引用
 */
class TrustProxyMvpCallback <V : TrustView,P : TrustPresenters<V>> :TrustMvpCallback<V,P>{
    var mTrustMvpCallback:TrustMvpCallback<V,P>? = null

    constructor(mTrustMvpCallback: TrustMvpCallback<V, P>?) {
        this.mTrustMvpCallback = mTrustMvpCallback
    }


    override fun createPresenter(): P {
        var presenter = mTrustMvpCallback!!.getPresenter()
        if (presenter == null){
            presenter = mTrustMvpCallback!!.createPresenter()
        }

        if (presenter == null){
            throw NullPointerException("prensenter 不能为空")
        }
        mTrustMvpCallback!!.setPresenter(presenter)
        return presenter
    }

    override fun getPresenter(): P {
        var presenter = mTrustMvpCallback!!.getPresenter()
        if (presenter == null){
            throw NullPointerException("prensenter 不能为空")
        }
        return  presenter
    }

    override fun setPresenter(prensent: P) {
        mTrustMvpCallback!!.setPresenter(prensent)
    }

    override fun getMvpView(): V {
        var mvpView = mTrustMvpCallback!!.getMvpView()
        if (mvpView == null){
            throw NullPointerException("mvpView 不能为空")
        }
        return  mvpView
    }


    fun attachView(){
        getPresenter().attachView(getMvpView())
    }

    fun detachView(){
        getPresenter().detachView()
    }
}
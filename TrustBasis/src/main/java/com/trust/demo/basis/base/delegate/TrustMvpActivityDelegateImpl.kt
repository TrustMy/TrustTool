package com.trust.demo.basis.base.delegate

import android.os.Bundle
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/27.
 * 第一重代理：目标对象
 * 特点：实现目标接口
 */
class TrustMvpActivityDelegateImpl <V : TrustView, P : TrustPresenters<V>>:TrustMvpActivityDelegate <V ,P>{
    private var mProxyMvpCallback:TrustProxyMvpCallback<V,P>? = null

    constructor(mMvpCallback: TrustMvpCallback<V,P>) {
        this.mProxyMvpCallback = TrustProxyMvpCallback<V,P>(mMvpCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.mProxyMvpCallback!!.createPresenter()
        //绑定
        this.mProxyMvpCallback!!.attachView()
    }

    override fun onStart() {
    }

    override fun onRestart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        //解绑
        this.mProxyMvpCallback!!.detachView()
    }
}
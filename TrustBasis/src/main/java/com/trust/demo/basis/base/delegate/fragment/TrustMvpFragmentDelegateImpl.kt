package com.trust.demo.basis.base.delegate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.demo.basis.base.delegate.TrustMvpCallback
import com.trust.demo.basis.base.delegate.TrustProxyMvpCallback
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/27.
 * 第一重代理 :目标对象
 */
class TrustMvpFragmentDelegateImpl <V : TrustView, P : TrustPresenters<V>>:TrustMvpFragmentDelegate<V,P> {
    private var mProxyMvpCallback: TrustProxyMvpCallback<V, P>? = null

    constructor(mMvpCallback: TrustMvpCallback<V,P>) {
        this.mProxyMvpCallback = TrustProxyMvpCallback<V,P>(mMvpCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        this.mProxyMvpCallback!!.createPresenter()
        //绑定
        this.mProxyMvpCallback!!.attachView()
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroyView() {
        //解绑
        this.mProxyMvpCallback!!.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
    }
}
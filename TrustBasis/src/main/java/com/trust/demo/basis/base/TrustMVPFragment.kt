package com.trust.demo.basis.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.demo.basis.base.delegate.TrustMvpCallback
import com.trust.demo.basis.base.delegate.fragment.TrustMvpFragmentDelegate
import com.trust.demo.basis.base.delegate.fragment.TrustMvpFragmentDelegateImpl
import com.trust.demo.basis.base.presenter.TrustPresenter
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/26.
 * MvpFragment
 * 第一重代理：代理对象
 * 第二重代理：目标对象
 */
abstract class TrustMVPFragment <V : TrustView,P : TrustPresenters<V>>:Fragment() ,TrustView,TrustMvpCallback<V,P>{
    protected var mActivity: Activity? = null
    protected var mContext: Context? = null
    protected abstract fun getLayoutId():Int
    protected abstract fun initView(view:View?,savedInstanceState: Bundle?)
    protected abstract fun initData()
    private var mTrustMvpFragmentDelegate: TrustMvpFragmentDelegate<V,P>? = null

    private var mPresenter:P? = null
    fun getMvpDelegate():TrustMvpFragmentDelegate<V,P>?{
        if (mTrustMvpFragmentDelegate == null){
            this.mTrustMvpFragmentDelegate = TrustMvpFragmentDelegateImpl<V,P>(this)
        }
        return  mTrustMvpFragmentDelegate
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getMvpDelegate()!!.onCreateView(inflater,container,savedInstanceState)
        return inflater!!.inflate(getLayoutId(), null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMvpDelegate()!!.onViewCreated(view,savedInstanceState)
        initView(view,savedInstanceState)
        initData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate()!!.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getMvpDelegate()!!.onStart()
    }

    override fun onResume() {
        super.onResume()
        getMvpDelegate()!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        getMvpDelegate()!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate()!!.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpDelegate()!!.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        getMvpDelegate()!!.onSaveInstanceState(outState)
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
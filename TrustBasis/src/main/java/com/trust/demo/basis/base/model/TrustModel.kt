package com.trust.demo.basis.base.model



/**
 * Created by Trust on 2018/6/25.
 *
 */
abstract class TrustModel  <M :TrustHttpRequestModel>:TrustModelListener ,TrustHttpRequestListener <M> {
    private var module :M? = null
    private var trustHttpRequestDelegate:TrustHttpRequestDelegate<M>? =null

    private fun getTrustHttpRequestDelegate():TrustHttpRequestDelegate<M>{
        if (trustHttpRequestDelegate == null) {
            trustHttpRequestDelegate = TrustHttpRequestDelegate<M>(this)
        }
        return trustHttpRequestDelegate!!
    }

    init {
        getTrustHttpRequestDelegate().onCreate()
    }


    override fun getRequestModule(): M? {
        return module
    }

    override fun createRequestModule(): M {
        return module!!
    }

    override fun setRequestMidule(model: M) {
        this.module = model
    }

}
package com.trust.demo.basis.base.presenter

import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/25.
 */
 abstract class TrustPresenter <V : TrustView>{
    private var mView:V? =null

    fun getView():V{return  mView!!}

    fun attachView(view: V){
        mView = view
    }

    fun detachView(){
        this.mView = null
    }
}
package com.trust.demo.basis.base.delegate

import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/27.
 * 第二重代理：目标接口
 * 定义关于MVP 方法 或规范
 */
interface TrustMvpCallback <V : TrustView,P : TrustPresenters<V>>{
    fun createPresenter():P

    fun getPresenter():P?

    fun setPresenter(prensent:P)

    fun getMvpView():V


}
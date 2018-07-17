package com.trust.loginregisterlibrary.mvpview

import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/7/17.
 */
interface IResetPwdView:TrustView {
    fun verififcationCodeCallBack(msg:String)
    fun resetPwdCallBack(msg:String)
}
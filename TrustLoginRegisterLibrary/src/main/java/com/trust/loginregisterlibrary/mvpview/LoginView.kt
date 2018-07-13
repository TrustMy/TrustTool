package com.trust.loginregisterlibrary.mvpview

import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/7/13.
 * 登陆回调接口
 */
interface LoginView :TrustView{
    fun resultSuccess(msg:String)
    fun resultError(msg:String)
}
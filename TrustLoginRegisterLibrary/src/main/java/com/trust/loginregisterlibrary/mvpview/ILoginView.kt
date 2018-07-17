package com.trust.loginregisterlibrary.mvpview

import com.trust.demo.basis.base.veiw.TrustView
import com.trust.loginregisterlibrary.bean.ResultBean
import com.trust.loginregisterlibrary.bean.ResultUserInfoBean

/**
 * Created by Trust on 2018/7/13.
 * 登陆回调接口
 */
interface ILoginView :TrustView{
    fun resultLogin(bean: ResultBean)
    fun resultUserInfo(bean: ResultUserInfoBean)
    fun resultUpdate(bean:String)
}
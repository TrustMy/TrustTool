package com.trust.loginregisterlibrary.mvpview

import com.trust.demo.basis.base.veiw.TrustView
import com.trust.loginregisterlibrary.bean.ResultBean

/**
 * Created by Trust on 2018/7/17.
 */
interface IResetPwdView:TrustView {
    fun verififcationCodeCallBack(bean: ResultBean)
    fun resetPwdCallBack(bean:ResultBean)
}
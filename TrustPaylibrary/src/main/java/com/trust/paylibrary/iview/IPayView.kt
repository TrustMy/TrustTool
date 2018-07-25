package com.trust.paylibrary.iview

import com.trust.demo.basis.base.veiw.TrustView
import com.trust.paylibrary.bean.PayBean

/**
 * Created by Trust on 2018/7/20.
 */
interface IPayView :TrustView{
    fun resultPayData(bean: PayBean)
}
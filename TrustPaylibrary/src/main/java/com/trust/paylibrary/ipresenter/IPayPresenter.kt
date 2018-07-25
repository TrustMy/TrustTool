package com.trust.paylibrary.ipresenter

import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.paylibrary.bean.PayBean
import com.trust.paylibrary.imodule.IPayModule
import com.trust.paylibrary.imodule.IPayModuleInterface
import com.trust.paylibrary.iview.IPayView

/**
 * Created by Trust on 2018/7/20.
 */
class IPayPresenter :TrustPresenters<IPayView>(),IPayPresenterInterface{
    private var payInterface:IPayModuleInterface? = null
    init {
        payInterface = IPayModule()
    }

    override fun getPayDate(params: HashMap<String, Any>) {
        payInterface!!.getPayData(params,object :ModuleResultInterface<PayBean>{
            override fun resultData(bean: PayBean) {
                view!!.resultPayData(bean)
            }

            override fun resultError(msg: String) {
                view!!.resultError(msg)
            }

        })
    }
}
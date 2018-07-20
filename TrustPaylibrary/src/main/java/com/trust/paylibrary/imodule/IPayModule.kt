package com.trust.paylibrary.imodule

import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.demo.basis.base.model.RequestResultListener
import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel

/**
 * Created by Trust on 2018/7/20.
 * 123
 */
class IPayModule :TrustModel<TrustRetrofitModel>(),IPayModuleInterface{
    override fun createRequestModule(): TrustRetrofitModel {
        return TrustRetrofitModel()
    }

    override fun getPayData(params: HashMap<String, Any>, moduleInterface: ModuleResultInterface<String>) {
        requestModule
                ?.requestGet("rest/getOrderInf",params,object :RequestResultListener<String>{
                    override fun resultSuccess(bean: String?) {
                        moduleInterface.resultData(bean!!)
                    }

                    override fun resultError(e: Throwable?) {
                        moduleInterface.resultError(e.toString())
                    }

                    override fun netWorkError(msg: String?) {
                    }

                },String::class.java)
    }
}
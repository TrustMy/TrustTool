package com.trust.loginregisterlibrary.module.registered

import com.trust.demo.basis.base.model.RequestResultListener
import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel
import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.retrofit.net.TrustRetrofitUtils

/**
 * Created by Trust on 2018/7/13.
 * 注册 数据处理模块
 */
class RegisteredModule : TrustModel<TrustRetrofitModel>(),RegisteredModuleInterface<String>{


    override fun createRequestModule(): TrustRetrofitModel {
        return TrustRetrofitModel()
    }

    override fun checkUserInfo(parameter: HashMap<String, Any>?,loginResultInterface: ModuleResultInterface<String>) {
        requestModule
                ?.requestJsonParams("register/customer/check"
                        ,TrustRetrofitUtils.POST_RAW
                        ,parameter
                        ,object : RequestResultListener<String>{

                            override fun resultSuccess(bean: String?) {
                                loginResultInterface.resultData(bean!!)
                            }

                            override fun resultError(e: Throwable?) {
                                loginResultInterface.resultError(e!!.toString())
                            }

                            override fun netWorkError(msg: String?) {
                            }

                },String::class.java)
    }

    override fun registered(param: HashMap<String, Any>?, loginResultInterface: ModuleResultInterface<String>) {
        requestModule
                ?.requestJsonParams("register/applySmsCode/"
                        ,1
                        ,param
                        ,object :RequestResultListener<String>{
                    override fun resultSuccess(bean: String) {
                        loginResultInterface.resultData(bean)
                    }

                    override fun resultError(e: Throwable?) {
                        loginResultInterface.resultError(e.toString())
                    }

                    override fun netWorkError(msg: String?) {
                    }

                },String::class.java)
    }


}
package com.trust.loginregisterlibrary.module.login

import com.trust.demo.basis.base.model.RequestResultListener
import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel
import com.trust.retrofit.net.TrustRetrofitUtils

/**
 * Created by Trust on 2018/7/13.
 * 登录页面业务处理模块
 */
class LoginModel :TrustModel<TrustRetrofitModel>(), LoginModuleInterface {


    override fun createRequestModule(): TrustRetrofitModel {
        return TrustRetrofitModel()
    }

    override fun login(map: HashMap<String, Any>, loginResultInterface: LoginResultInterface) {

        requestModule
                ?.requestJsonParams("rest/user/login"
                , TrustRetrofitUtils.POST_RAW
                ,map
                ,object : RequestResultListener<String>{
                    override fun resultSuccess(bean: String) {
                        loginResultInterface.resultData(bean)
                    }

                    override fun resultError(e: Throwable) {
                    }

                    override fun netWorkError(msg: String) {
                        loginResultInterface.resultError(msg)
                    }

                }
                ,String::class.java)


    }

}
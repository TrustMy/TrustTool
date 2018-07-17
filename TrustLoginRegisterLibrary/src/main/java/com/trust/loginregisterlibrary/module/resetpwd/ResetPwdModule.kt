package com.trust.loginregisterlibrary.module.resetpwd

import com.trust.demo.basis.base.model.RequestResultListener
import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel
import com.trust.loginregisterlibrary.callback.AppServer
import com.trust.loginregisterlibrary.module.login.ModuleResultInterface
import com.trust.modular.TrustRetrofit
import com.trust.modular.TrustRetrofitCallBack
import com.trust.retrofit.config.ProjectInit
import com.trust.retrofit.net.TrustRetrofitUtils
import okhttp3.ResponseBody

/**
 * Created by Trust on 2018/7/17.
 *
 */
class ResetPwdModule :TrustModel<TrustRetrofitModel>() ,ResetPwdModuleInterface<String>{
    private var mTrustRetrofit: TrustRetrofit? = null
    private var mRetrofit: AppServer? = null
    override fun createRequestModule(): TrustRetrofitModel {
        mTrustRetrofit = TrustRetrofit.create(ProjectInit.getApplicationContext(),  "https://syvehicle.cn/tomcat/",
                true,"cacerts_sy.bks","changeit")
        mRetrofit = mTrustRetrofit!!.getmRetrofit(AppServer::class.java!!)
        return TrustRetrofitModel()
    }

    override fun getVerificationCode(params: HashMap<String, Any>?, loginResultInterface: ModuleResultInterface<String>) {
        mTrustRetrofit!!
                .connection(
                        mRetrofit!!
                                .getVerificationCode(
                                        params!!["phone"].toString()),
                        object :TrustRetrofitCallBack<ResponseBody>(){
                            override fun accept(t: ResponseBody) {
                                loginResultInterface.resultData(t.string().toString())
                            }

                            override fun failure(error: Throwable) {
                                loginResultInterface.resultError(error.toString())
                            }

                            override fun failure(error: ResponseBody) {
                            }

                        }
                )
    }

    override fun resetPwd(params: HashMap<String, Any>?, loginResultInterface: ModuleResultInterface<String>) {
        requestModule!!.requestJsonParams("register/retrievePassword",
                TrustRetrofitUtils.PUT_RAW,
                params,
                object : RequestResultListener<String>{
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
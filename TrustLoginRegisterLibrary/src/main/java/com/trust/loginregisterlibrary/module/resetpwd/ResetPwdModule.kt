package com.trust.loginregisterlibrary.module.resetpwd

import com.trust.demo.basis.base.model.RequestResultListener
import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel
import com.trust.loginregisterlibrary.bean.ResultBean
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
class ResetPwdModule :TrustModel<TrustRetrofitModel>() ,ResetPwdModuleInterface<ResultBean>{
    private var mTrustRetrofit: TrustRetrofit? = null
    private var mRetrofit: AppServer? = null
    override fun createRequestModule(): TrustRetrofitModel {
        mTrustRetrofit = TrustRetrofit.create(ProjectInit.getApplicationContext(),  "https://syvehicle.cn/tomcat/",
                true,"cacerts_sy.bks","changeit")
        mRetrofit = mTrustRetrofit!!.getmRetrofit(AppServer::class.java!!)
        return TrustRetrofitModel()
    }

    override fun getVerificationCode(params: HashMap<String, Any>?, loginResultInterface: ModuleResultInterface<ResultBean>) {
        mTrustRetrofit!!
                .connection(
                        mRetrofit!!
                                .getVerificationCode(
                                        params!!["phone"].toString()),
                        object :TrustRetrofitCallBack<ResultBean>(){
                            override fun accept(t: ResultBean) {
                                loginResultInterface.resultData(t)
                            }

                            override fun failure(error: Throwable) {
                                loginResultInterface.resultError(error.toString())
                            }

                            override fun failure(error: ResponseBody) {
                            }

                        }
                )
    }

    override fun resetPwd(params: HashMap<String, Any>?, loginResultInterface: ModuleResultInterface<ResultBean>) {
        requestModule!!.requestJsonParams("register/retrievePassword",
                TrustRetrofitUtils.PUT_RAW,
                params,
                object : RequestResultListener<ResultBean>{
                    override fun resultSuccess(bean: ResultBean) {
                        loginResultInterface.resultData(bean)
                    }

                    override fun resultError(e: Throwable?) {
                        loginResultInterface.resultError(e.toString())
                    }

                    override fun netWorkError(msg: String?) {
                    }

                },ResultBean::class.java)
    }
}
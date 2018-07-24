package com.trust.loginregisterlibrary.module.login

import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.demo.basis.base.model.RequestResultListener
import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel
import com.trust.loginregisterlibrary.bean.ResultBean
import com.trust.loginregisterlibrary.bean.ResultUserInfoBean
import com.trust.loginregisterlibrary.callback.AppServer
import com.trust.modular.TrustRetrofit
import com.trust.modular.TrustRetrofitCallBack
import com.trust.retrofit.config.ProjectInit
import com.trust.retrofit.net.TrustRetrofitUtils
import okhttp3.ResponseBody
import java.util.*

/**
 * Created by Trust on 2018/7/13.
 * 登录页面业务处理模块
 */
class LoginModel :TrustModel<TrustRetrofitModel>(), LoginModuleInterface {

    private var mTrustRetrofit: TrustRetrofit? = null
    private var mRetrofit: AppServer? = null
    override fun createRequestModule(): TrustRetrofitModel {
        mTrustRetrofit = TrustRetrofit.create(ProjectInit.getApplicationContext(),  "https://syvehicle.cn/tomcat/",
                true,"cacerts_sy.bks","changeit")
        mRetrofit = mTrustRetrofit!!.getmRetrofit(AppServer::class.java!!)
        return TrustRetrofitModel()
    }

    override fun login(map: HashMap<String, Any>, loginResultInterface: ModuleResultInterface<ResultBean>) {

        requestModule
                ?.requestJsonParams("rest/user/login"
                , TrustRetrofitUtils.POST_RAW
                ,map
                ,object : RequestResultListener<ResultBean>{
                    override fun resultSuccess(bean: ResultBean) {
                        loginResultInterface.resultData(bean)
                    }

                    override fun resultError(e: Throwable) {
                    }

                    override fun netWorkError(msg: String) {
                        loginResultInterface.resultError(msg)
                    }

                }
                ,ResultBean::class.java)
    }

    override fun updateApp(loginResultInterface: ModuleResultInterface<String>) {
    }

    override fun userInfo(map: HashMap<String, Any>, loginResultInterface: ModuleResultInterface<ResultUserInfoBean>) {
        mTrustRetrofit!!
                .connection(
                        mRetrofit!!
                                .getUserInfo(
                                        map!!["phone"].toString()),
                        object : TrustRetrofitCallBack<ResultUserInfoBean>(){
                            override fun accept(t: ResultUserInfoBean) {
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

}
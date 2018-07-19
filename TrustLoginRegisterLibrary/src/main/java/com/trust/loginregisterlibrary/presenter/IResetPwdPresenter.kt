package com.trust.loginregisterlibrary.presenter

import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.loginregisterlibrary.bean.ResultBean
import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.loginregisterlibrary.module.resetpwd.ResetPwdModule
import com.trust.loginregisterlibrary.module.resetpwd.ResetPwdModuleInterface
import com.trust.loginregisterlibrary.mvpview.IResetPwdView

/**
 * Created by Trust on 2018/7/17.
 * 重置密码 Presenter
 */
class IResetPwdPresenter :TrustPresenters<IResetPwdView>(),IResetPwdPresenterInterface{

    private var resetPwdModuleInterface:ResetPwdModuleInterface<ResultBean>? = null

    init {
        resetPwdModuleInterface = ResetPwdModule()
    }


    override fun getVerificationCode(params: HashMap<String, Any>?) {
        resetPwdModuleInterface!!.getVerificationCode(params,object : ModuleResultInterface<ResultBean> {
            override fun resultData(bean: ResultBean) {
                view.verififcationCodeCallBack(bean)
            }

            override fun resultError(msg: String) {
                view.resultError(msg)
            }

        })
    }

    override fun resetPwd(params: HashMap<String, Any>?) {
        resetPwdModuleInterface!!.resetPwd(params,object : ModuleResultInterface<ResultBean> {
            override fun resultData(bean: ResultBean) {
                view.resetPwdCallBack(bean)
            }

            override fun resultError(msg: String) {
                view.resultError(msg)
            }

        })
    }

}
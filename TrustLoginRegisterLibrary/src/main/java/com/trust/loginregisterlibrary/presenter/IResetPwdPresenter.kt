package com.trust.loginregisterlibrary.presenter

import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.loginregisterlibrary.module.login.ModuleResultInterface
import com.trust.loginregisterlibrary.module.resetpwd.ResetPwdModule
import com.trust.loginregisterlibrary.module.resetpwd.ResetPwdModuleInterface
import com.trust.loginregisterlibrary.mvpview.IResetPwdView

/**
 * Created by Trust on 2018/7/17.
 * 重置密码 Presenter
 */
class IResetPwdPresenter :TrustPresenters<IResetPwdView>(),IResetPwdPresenterInterface{



    private var resetPwdModuleInterface:ResetPwdModuleInterface<String>? = null

    init {
        resetPwdModuleInterface = ResetPwdModule()
    }


    override fun getVerificationCode(params: HashMap<String, Any>?) {
        resetPwdModuleInterface!!.getVerificationCode(params,object :ModuleResultInterface<String>{
            override fun resultData(msg: String) {
                view.verififcationCodeCallBack(msg)
            }

            override fun resultError(msg: String) {
                view.resultError(msg)
            }

        })
    }

    override fun resetPwd(params: HashMap<String, Any>?) {
        resetPwdModuleInterface!!.resetPwd(params,object :ModuleResultInterface<String>{
            override fun resultData(msg: String) {
                view.resetPwdCallBack(msg)
            }

            override fun resultError(msg: String) {
                view.resultError(msg)
            }

        })
    }

}
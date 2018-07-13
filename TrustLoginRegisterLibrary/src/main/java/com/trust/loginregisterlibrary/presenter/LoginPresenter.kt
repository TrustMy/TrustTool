package com.trust.loginregisterlibrary.presenter

import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.loginregisterlibrary.module.LoginModel
import com.trust.loginregisterlibrary.module.LoginModuleInterface
import com.trust.loginregisterlibrary.module.LoginResultInterface
import com.trust.loginregisterlibrary.mvpview.LoginView

/**
 * Created by Trust on 2018/7/13.
 * 123
 */
class LoginPresenter :TrustPresenters<LoginView>() ,LoginPresenterInterface{
    private var loginModule: LoginModuleInterface? = null

    init {
        loginModule = LoginModel()
    }


    override fun login(map: HashMap<String, Any>) {
        view.showWaitDialog("",false,"")
        loginModule!!.login(map,object : LoginResultInterface{
            override fun resultData(msg: String) {
                view.diassDialog()
                view.resultSuccess(msg)
            }

            override fun resultError(msg: String) {
                view.diassDialog()
                view.resultError(msg)
            }
        })
    }
}
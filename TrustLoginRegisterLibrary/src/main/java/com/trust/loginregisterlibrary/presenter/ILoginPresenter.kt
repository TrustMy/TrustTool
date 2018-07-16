package com.trust.loginregisterlibrary.presenter

import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.loginregisterlibrary.module.login.LoginModel
import com.trust.loginregisterlibrary.module.login.LoginModuleInterface
import com.trust.loginregisterlibrary.module.login.ModuleResultInterface
import com.trust.loginregisterlibrary.mvpview.ILoginView

/**
 * Created by Trust on 2018/7/13.
 * 123
 */
class ILoginPresenter :TrustPresenters<ILoginView>() ,LoginPresenterInterface{
    private var loginModule: LoginModuleInterface<String>? = null

    init {
        loginModule = LoginModel()
    }


    override fun login(map: HashMap<String, Any>) {
        view.showWaitDialog("",false,"")
        loginModule!!.login(map,object : ModuleResultInterface<String> {
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
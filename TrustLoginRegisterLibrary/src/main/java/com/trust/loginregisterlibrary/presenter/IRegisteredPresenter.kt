package com.trust.loginregisterlibrary.presenter

import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView
import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.loginregisterlibrary.module.registered.RegisteredModule
import com.trust.loginregisterlibrary.module.registered.RegisteredModuleInterface

/**
 * Created by Trust on 2018/7/16.
 *
 */
class IRegisteredPresenter :TrustPresenters<TrustView>(),IRegisteredPresenterInterface{

    private var registeredModuleInterface:RegisteredModuleInterface<String>? = null

    init {
        registeredModuleInterface = RegisteredModule()
    }

    override fun checkUserInfo(parameter: HashMap<String, Any>?) {
        registeredModuleInterface!!.checkUserInfo(parameter,object : ModuleResultInterface<String> {
            override fun resultData(msg: String) {
                view.resultSuccess(msg)
            }

            override fun resultError(msg: String) {
                view.resultError(msg)
            }

        })
    }

    override fun registered(param: HashMap<String, Any>?) {
        registeredModuleInterface!!.registered(param,object : ModuleResultInterface<String> {
            override fun resultData(msg: String) {
            }

            override fun resultError(msg: String) {
            }

        })
    }

}
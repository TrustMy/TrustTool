package com.trust.maplibrary.ipresenter

import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.maplibrary.bean.TrajectoryBean
import com.trust.maplibrary.imodule.ITrajectoryModule
import com.trust.maplibrary.imodule.ITrajectoryModuleInterface
import com.trust.maplibrary.iview.ITrajectoryView

/**
 * Created by Trust on 2018/7/18.
 */
class ITrajectoryPresenter :TrustPresenters<ITrajectoryView>(),ITrajectoryPresenterInterface{


    private var iTrajectoryModuleInterface:ITrajectoryModuleInterface? = null

    init {
        iTrajectoryModuleInterface = ITrajectoryModule()
    }

    override fun getGpsData(params: HashMap<String, Any>) {
        iTrajectoryModuleInterface!!.getGpsData(params,object :ModuleResultInterface<TrajectoryBean>{
            override fun resultData(msg: TrajectoryBean) {
                view.resultGpsData(msg)
            }

            override fun resultError(msg: String) {
                view.resultError(msg)
            }
        })
    }

}
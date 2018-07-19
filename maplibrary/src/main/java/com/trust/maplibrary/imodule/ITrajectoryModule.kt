package com.trust.maplibrary.imodule

import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.demo.basis.base.model.RequestResultListener
import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel
import com.trust.maplibrary.bean.TrajectoryBean

/**
 * Created by Trust on 2018/7/18.
 *
 */
class ITrajectoryModule:TrustModel<TrustRetrofitModel>() , ITrajectoryModuleInterface {
    override fun createRequestModule(): TrustRetrofitModel {
        return TrustRetrofitModel()
    }

    override fun getGpsData(map: HashMap<String, Any>, moduleInterface: ModuleResultInterface<TrajectoryBean>) {
        requestModule!!
                .requestGet("rest/gps"
                        ,map
                        ,object :RequestResultListener<TrajectoryBean>{
                    override fun resultSuccess(bean: TrajectoryBean) {
                        moduleInterface.resultData(bean)
                    }

                    override fun resultError(e: Throwable?) {
                        moduleInterface.resultError(e.toString())
                    }

                    override fun netWorkError(msg: String) {
                        moduleInterface.resultError(msg)
                    }

                },TrajectoryBean::class.java)

    }
}
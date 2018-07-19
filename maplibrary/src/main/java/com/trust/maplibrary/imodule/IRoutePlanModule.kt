package com.trust.maplibrary.imodule

import com.trust.demo.basis.base.model.TrustModel
import com.trust.demo.basis.base.model.http.TrustRetrofitModel

/**
 * Created by Trust on 2018/7/19.
 */
class IRoutePlanModule :TrustModel<TrustRetrofitModel>() {

    override fun createRequestModule(): TrustRetrofitModel {
        return TrustRetrofitModel()
    }
}
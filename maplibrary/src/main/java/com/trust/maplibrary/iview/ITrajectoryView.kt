package com.trust.maplibrary.iview

import com.trust.demo.basis.base.veiw.TrustView
import com.trust.maplibrary.bean.TrajectoryBean

/**
 * Created by Trust on 2018/7/18.
 *
 */
interface ITrajectoryView :TrustView {
    fun resultGpsData(bean: TrajectoryBean)
}
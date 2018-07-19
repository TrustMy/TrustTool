package com.trust.maplibrary.imodule

import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.maplibrary.bean.TrajectoryBean

/**
 * Created by Trust on 2018/7/19.
 */
interface ITrajectoryModuleInterface {
    fun getGpsData(map:HashMap<String,Any>,moduleInterface: ModuleResultInterface<TrajectoryBean>)
}
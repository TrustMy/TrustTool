package com.trust.paylibrary.imodule

import com.trust.demo.basis.base.ModuleResultInterface

/**
 * Created by Trust on 2018/7/20.
 */
interface IPayModuleInterface {
    fun getPayData(params:HashMap<String,Any>,moduleInterface: ModuleResultInterface<String>)
}
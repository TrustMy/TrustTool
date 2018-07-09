package com.trust.demo.basis.base.veiw

/**
 * Created by Trust on 2018/6/25.
 */
interface TrustView {
    /**
     * @param msg  等待dialog的提示消息
     * @param isShow  是否显示dialog  有的接口不需要显示dialog
     * @param tag 由于是统一回调 需要一个tag 做标记
     */
    fun showWaitDialog(msg:String?,isShow:Boolean,tag:String?)

    fun diassDialog()

    fun showToast(msg:String)

}
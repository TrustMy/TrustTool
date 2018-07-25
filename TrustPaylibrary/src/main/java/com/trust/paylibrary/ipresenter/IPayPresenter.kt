package com.trust.paylibrary.ipresenter

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.trust.demo.basis.base.ModuleResultInterface
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.modular.TrustRetrofitCallBack
import com.trust.paylibrary.bean.PayBean
import com.trust.paylibrary.imodule.IPayModule
import com.trust.paylibrary.imodule.IPayModuleInterface
import com.trust.paylibrary.iview.IPayView
import com.trust.paylibrary.tool.PayResult
import okhttp3.ResponseBody

/**
 * Created by Trust on 2018/7/20.
 */
class IPayPresenter :TrustPresenters<IPayView>(),IPayPresenterInterface{


    private var payInterface:IPayModuleInterface? = null
    init {
        payInterface = IPayModule()
    }

    override fun getPayDate(params: HashMap<String, Any>) {
        payInterface!!.getPayData(params,object :ModuleResultInterface<PayBean>{
            override fun resultData(bean: PayBean) {
                view!!.resultPayData(bean)
            }

            override fun resultError(msg: String) {
                view!!.resultError(msg)
            }

        })
    }

    override fun getPayStatus(activity: Activity, result: MutableMap<String, String>) {
        activity.runOnUiThread( {
            val payResult = PayResult(result)
            /**
            对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            val resultInfo = payResult.result// 同步返回需要验证的信息
            val resultStatus = payResult.resultStatus
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show()

            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show()
            }

            Log.d("lhh", "支付状态码:$resultStatus"+"支付返回:"+payResult.result)
        })

    }

}
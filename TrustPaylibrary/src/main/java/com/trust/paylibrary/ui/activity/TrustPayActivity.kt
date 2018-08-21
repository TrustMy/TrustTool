package com.trust.paylibrary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alipay.sdk.app.PayTask
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.paylibrary.R
import com.trust.paylibrary.bean.PayBean
import com.trust.paylibrary.ipresenter.IPayPresenter
import com.trust.paylibrary.iview.IPayView
import kotlinx.android.synthetic.main.activity_trust_pay.*

@Route(path = "/pay/controll")
class TrustPayActivity : TrustMVPActivtiy<IPayView,IPayPresenter>() ,IPayView{


    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {

    }

    override fun diassDialog() {
    }

    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun resultSuccess(msg: String) {
    }

    override fun resultError(msg: String) {
        showToast(msg)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_trust_pay
    }

    override fun initView(savedInstanceState: Bundle?) {
        baseSetOnClick(btn_pay_order_info,0)
    }

    override fun initData() {
    }

    override fun baseResultOnClick(v: View) {
        when (v.id) {
            R.id.btn_pay_order_info -> {
                val params = hashMapOf<String,Any>()
                params["total_fee"] = "0.01"
                params["cellPhone"] = "13391315718"
                getPresenter()?.getPayDate(params)
            }else -> {
            }
        }
    }

    override fun createPresenter(): IPayPresenter {
        return IPayPresenter()
    }

    override fun resultPayData(bean: PayBean) {
        showToast(bean.info)
        getPayStatus(bean)
    }


    private fun getPayStatus(bean: PayBean){
        Thread({
            val alipay = PayTask(this)
            val result = alipay.payV2(bean.content.alipayResponse, true)
            getPresenter()?.getPayStatus(this,result)
        }).start()
    }


}

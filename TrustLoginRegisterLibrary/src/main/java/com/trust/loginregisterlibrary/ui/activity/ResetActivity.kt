package com.trust.loginregisterlibrary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.trust.TrustTools
import com.trust.demo.basis.trust.utils.TrustAppUtils
import com.trust.demo.basis.trust.utils.TrustStringUtils
import com.trust.loginregisterlibrary.R
import com.trust.loginregisterlibrary.mvpview.IResetPwdView
import com.trust.loginregisterlibrary.presenter.IResetPwdPresenter
import kotlinx.android.synthetic.main.activity_reset.*

class ResetActivity : TrustMVPActivtiy<IResetPwdView, IResetPwdPresenter>() ,IResetPwdView{
    override fun verififcationCodeCallBack(msg: String) {
        TrustTools<View>().Countdown(mActivity,btn_reset_verification_code,60,TrustAppUtils.getResourcesString(this,
                R.string.get_verififcation_code))
        showToast(msg)
    }

    override fun resetPwdCallBack(msg: String) {
        showToast(msg)
    }

    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {

    }

    override fun diassDialog() {
    }

    override fun showToast(msg: String) {
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show()
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun resultSuccess(msg: String) {
    }

    override fun resultError(msg: String) {
        showToast(msg)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reset
    }

    override fun initView(savedInstanceState: Bundle?) {
        baseSetOnClick(btn_reset_submint,0)
        baseSetOnClick(btn_reset_verification_code,0)
    }

    override fun initData() {
    }

    override fun baseResultOnClick(v: View) {
        when (v.id) {
            R.id.btn_reset_submint -> {
                requestResetPwd()
            }
            else -> {
                requestVerificationCode()
            }
        }
    }

    private fun requestVerificationCode() {
        val phone = ed_reset_phone.text.trim().toString()
        if (!TrustStringUtils.isEmpty(phone)
             && phone.length == 11) {
            val map = hashMapOf<String,Any>()
            map["phone"] = phone
            getPresenter()?.getVerificationCode(map)
        }else{
            showToast(TrustAppUtils.getResourcesString(this,R.string.error_phone))
        }
    }

    private fun requestResetPwd() {
        val phone = ed_reset_phone.text.trim().toString()
        val verificationCode = ed_reset_verification_code.text.trim().toString()
        val pwd = ed_reset_pwd.text.trim().toString()
        val pwd2 = ed_reset_pwd2.text.trim().toString()

        if (!TrustStringUtils.isEmpty(phone)
                && phone.length == 11
                && !TrustStringUtils.isEmpty(verificationCode)
                && verificationCode.length == 6
                && !TrustStringUtils.isEmpty(pwd)
                && !TrustStringUtils.isEmpty(pwd2)
                && pwd == pwd2) {
            val map = hashMapOf<String,Any>()
            map["cellPhone"] = phone
            map["password"] = pwd
            map["authCode"] = verificationCode.toInt()
            getPresenter()?.resetPwd(map)
        }else{
            showToast(TrustAppUtils.getResourcesString(this,R.string.error_register_info))
        }
    }

    override fun createPresenter(): IResetPwdPresenter {
        return IResetPwdPresenter()
    }
}

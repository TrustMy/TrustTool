package com.trust.loginregisterlibrary.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dn.tim.lib_permission.annotation.Permission
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.base.veiw.TrustView
import com.trust.demo.basis.trust.utils.TrustAppUtils
import com.trust.demo.basis.trust.utils.TrustEffective
import com.trust.demo.basis.trust.utils.TrustStringUtils
import com.trust.loginregisterlibrary.R
import com.trust.loginregisterlibrary.presenter.IRegisteredPresenter
import kotlinx.android.synthetic.main.activity_registered.*

class RegisteredActivity : TrustMVPActivtiy<TrustView, IRegisteredPresenter>() ,TrustView{
    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {
    }

    override fun diassDialog() {
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun resultSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun resultError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_registered
    }

    override fun initView(savedInstanceState: Bundle?) {
        btn_back.text = Html.fromHtml(resources.getString(R.string.back_login,"已有账号?"," 立即登录!"))
        baseSetOnClick(btn_registered,0)
        baseSetOnClick(btn_back,0)
    }

    override fun initData() {
    }


    override fun baseResultOnClick(v: View) {
        when (v.id) {
            R.id.btn_registered -> {
                checkUserInfo()
            }
            else -> {
                finish()
            }
        }
    }

    @Permission(Manifest.permission.READ_PHONE_STATE)
    private fun checkUserInfo() {
        val userName = TrustAppUtils.getEdString(ed_registered_user_name)
        val idCardNum = TrustAppUtils.getEdString(ed_registered_id_card_num)
        val carVin = TrustAppUtils.getEdString(ed_registered_car_vin)
        val authorizatCode = TrustAppUtils.getEdString(ed_registered_authorizat_code)
        val phone = TrustAppUtils.getEdString(ed_registered_phone)
        if (!TrustStringUtils.isEmpty(userName)
             && !TrustStringUtils.isEmpty(idCardNum)
             && !TrustStringUtils.isEmpty(carVin)
             && !TrustStringUtils.isEmpty(authorizatCode)
             && !TrustStringUtils.isEmpty(phone)
             && TrustStringUtils.isNumber(authorizatCode)
             && TrustEffective.IDCardValidate(idCardNum)) {

            val map = hashMapOf<String, Any>()
            map["cellPhone"] = phone
            map["imei"] = TrustAppUtils.getIMEI(this)
            map["name"] = userName
            map["idCard"] = idCardNum
            map["vin"] = carVin
            map["authorizationCode"] = authorizatCode.toInt()

            getPresenter()?.checkUserInfo(map)

        }else{
            showToast(TrustAppUtils.getResourcesString(this,R.string.error_register_info))
        }

    }


    override fun createPresenter(): IRegisteredPresenter {
        return IRegisteredPresenter()
    }


}

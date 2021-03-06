package com.trust.loginregisterlibrary.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.dn.tim.lib_permission.annotation.Permission
import com.dn.tim.lib_permission.annotation.PermissionDenied
//import com.dn.tim.lib_permission.annotation.Permission
//import com.dn.tim.lib_permission.annotation.PermissionCanceled
//import com.dn.tim.lib_permission.annotation.PermissionDenied

import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.trust.utils.TrustAppUtils
import com.trust.demo.basis.trust.utils.TrustLogUtils
import com.trust.demo.basis.trust.utils.TrustStringUtils
import com.trust.loginregisterlibrary.R
import com.trust.loginregisterlibrary.bean.ResultBean
import com.trust.loginregisterlibrary.bean.ResultUserInfoBean
import com.trust.loginregisterlibrary.mvpview.ILoginView
import com.trust.loginregisterlibrary.presenter.ILoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import java.util.HashMap

@Route(path = "/login/login")
class LoginActivity : TrustMVPActivtiy<ILoginView,ILoginPresenter>() , ILoginView{
    private var userName:String? = null

    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {
        TrustLogUtils.d("正在请求，请稍后...")
    }

    override fun diassDialog() {
        TrustLogUtils.d("请求成功.")
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView(savedInstanceState: Bundle?) {
        baseSetOnClick(btn_login,0)
        baseSetOnClick(btn_login_forget_pwd,0)
        baseSetOnClick(btn_login_registered,0)
    }

    override fun baseResultOnClick(v: View) {
        when(v.id){
            R.id.btn_login ->{submintLogin()}
            R.id.btn_login_forget_pwd ->{
                startActivity(Intent(mContext,ResetActivity::class.java))
            }
            R.id.btn_login_registered ->{
                startActivity(Intent(mContext,RegisteredActivity::class.java))
            }
        }
    }

    override fun initData() {

    }

    override fun createPresenter(): ILoginPresenter {
        return ILoginPresenter()
    }



    override fun resultSuccess(msg:String) {
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show()
        TrustLogUtils.d(msg)
    }

    override fun resultError(msg: String) {
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show()
    }

    @Permission(Manifest.permission.READ_PHONE_STATE)
    private fun submintLogin(){
        userName = ed_login_phone.text.trim().toString()
        val passWord = ed_login_pwd.text.trim().toString()

        if (!TrustStringUtils.isEmpty(userName) && !TrustStringUtils.isEmpty(passWord)
        && userName!!.length == 11
        && TrustStringUtils.isPhoneNumberValid(userName)) {
            val map = HashMap<String, Any>()
            map["userName"] = userName!!
            map["password"] = passWord
            map["userType"] = 1
            map["imsi"] = TrustAppUtils.getIMEI(this)
            map["appPlatform"] = "Android"
            map["appVersion"] = TrustAppUtils.getAppVersionCode(this)
            getPresenter()?.login(map)
        }else{
            showToast(TrustAppUtils.getResourcesString(this,R.string.error_user_pwd))
        }
    }


    fun getUserInfo(){
        val map = HashMap<String, Any>()
        map["phone"] = userName!!
        getPresenter()?.userInfo(map)
    }

//    @PermissionCanceled
    private fun cancel() {
        showToast("你拒绝了这个权限")
    }

    @PermissionDenied
    private fun denied() {
        showToast("没有这个权限手机无法正常使用")
    }


    override fun resultLogin(bean: ResultBean) {
        if (bean.status == 1) {
            getUserInfo()
        }else{
            showToast(bean.info!!)
        }
    }

    override fun resultUpdate(bean: String) {

    }

    override fun resultUserInfo(bean: ResultUserInfoBean) {
        if (bean.status ==1) {
            ARouter.getInstance().build("/map/controll").navigation()
            finish()
        }else{
            showToast(bean.info!!)
        }
    }

}

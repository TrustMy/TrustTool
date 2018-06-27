package com.trust.demo.trusttool.mvptest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.trusttool.R

class MVPTestActivity : TrustMVPActivtiy<LoginView,LoginPresenter>(),LoginView{
    override fun getLayoutId(): Int {
        return  R.layout.activity_mvptest
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
    }


    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }


    override fun logingStatus(status: String?) {
        Toast.makeText(mContext,status,Toast.LENGTH_LONG).show()
        Log.d("lhh,", "logingStatus:$status")
    }

    fun startLogin(v:View){
        getPresenter()?.login("动态代理后的mvp来自activitiy",null)
    }
}


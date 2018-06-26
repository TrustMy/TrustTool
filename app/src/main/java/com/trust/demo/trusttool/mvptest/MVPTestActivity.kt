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



    fun startLogin(v: View){
        getPresent().login("我是activity","12312312")
    }



    override fun logingStatus(status: String?) {
        Toast.makeText(mContext,status,Toast.LENGTH_LONG).show()
        Log.d("lhh,", "logingStatus:$status")
    }

}


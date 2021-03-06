package com.trust.demo.trusttool.mvptest

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.trust.utils.TrustLogUtils
import com.trust.demo.trusttool.R
import com.trust.demo.trusttool.activity.RecyclerViewActivity
import com.trust.retrofit.config.ProjectInit


import java.util.*

class MVPTestActivity : TrustMVPActivtiy<LoginView,LoginPresenter>(),LoginView{
    override fun resultSuccess(msg: String) {
    }

    override fun resultError(msg: String) {
    }

    override fun baseResultOnClick(v: View) {
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        test_framelayout.onActivityResult(requestCode,resultCode,data)
        showta("onTrustViewActivityResult：$requestCode  resultCode : $resultCode test ${data?.getStringExtra("test")} ")
    }

    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {
        if (msg != null) {
            showta("showWaitDialog：$isShow  tag : $tag   msg: $msg")
        }else{
            showta("showWaitDialog：$isShow  tag : $tag")
        }
    }

    override fun diassDialog() {
        showta("diassDialog")
    }

    override fun showToast(msg: String) {
        showta("showToast  $msg")
    }

    override fun getLayoutId(): Int {
        return  R.layout.activity_mvptest
    }

    override fun initView(savedInstanceState: Bundle?) {
        ProjectInit.init(this).setApiHost("https://syvehicle.cn/tomcat/")
                .setSSL("cacerts_sy.bks","changeit")
                .configure()
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
       /*
        val create = TrustRetrofit.create(this, "https://www.jianshu.com/")
        create.connection(create!!.getmRetrofit(ApiServer::class.java).login(HashMap<String, String>()),object :TrustRetrofitCallBack<ResponseBody>(){
            override fun failure(error: ResponseBody) {
                Log.d("lhh","failure ResponseBody:"+error.toString())
            }

            override fun accept(t: ResponseBody) {
                TrustLogUtils.d("这事网络i七年秋："+t.string().toString())
            }

            override fun failure(error: Throwable) {
                Log.d("lhh","failure Throwable:"+error.message)
            }
        })
*/

//        getPresenter()?.login("动态代理后的mvp来自activitiy",null)
//        startActivity(Intent(this,PermissActivity::class.java))
        testPermission()
    }

//    @Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun testPermission(){
        Toast.makeText(this, "请求两个权限成功（写和相机）", Toast.LENGTH_SHORT).show()
    }


//    @PermissionCanceled
    private fun cancel() {
        Log.i("lhh", "writeCancel: ")
        Toast.makeText(this, "PermissionCanceled", Toast.LENGTH_SHORT).show()
    }


//    @PermissionDenied
    private fun cancels() {
        Log.i("lhh", "PermissionDenied: ")
        Toast.makeText(this, "PermissionDenied", Toast.LENGTH_SHORT).show()
    }



    fun checkStatus(v:View){
        val map = HashMap<String, Any>(2)
        map["cellPhone"] = "13892929789"
        getPresenter()?.checkStatus(map)
    }


    fun showta(msg:String){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show()
    }
}


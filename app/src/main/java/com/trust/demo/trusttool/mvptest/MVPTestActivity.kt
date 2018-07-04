package com.trust.demo.trusttool.mvptest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.trust.utils.TrustLogUtils
import com.trust.demo.trusttool.R
import com.trust.retrofit.config.ProjectInit

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

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

        ProjectInit.init(this).setApiHost("https://syvehicle.cn/tomcat/")
                .setSSL("cacerts_sy.bks","changeit")
                .configure()

        getPresenter()?.login("动态代理后的mvp来自activitiy",null)
    }

    public fun checkStatus(v:View){
        val map = HashMap<String, Any>(2)
        map["cellPhone"] = "13892929789"
        getPresenter()?.checkStatus(map)
    }
}


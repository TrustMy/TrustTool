package com.trust.demo.trusttool.mvptest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.trust.utils.TrustLogUtils
import com.trust.demo.trusttool.R
import com.trust.modular.TrustRetrofit
import com.trust.modular.TrustRetrofitCallBack
import com.trust.retrofit.config.ProjectInit
import com.trust.retrofit.net.TrustRetrofitUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.HashMap

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
       /* //getPresenter()?.login("动态代理后的mvp来自activitiy",null)
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

        ProjectInit.init(this).setApiHost("https://www.jianshu.com/")
                .configure()

        TrustRetrofitUtils
                .create()
                .params(HashMap<String, Any>())
                .url("subscriptions#/subscriptions/12070983/user")
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(@NonNull d: Disposable) {

                    }

                    override fun onNext(@NonNull s: String) {
                        //响应结果
                        Toast.makeText(this@MVPTestActivity, s, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(@NonNull e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }
}


package com.trust.demo.basis.base.presenter

import com.trust.demo.basis.base.veiw.TrustView
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by Trust on 2018/6/25.
 * 之所以不用kt 这个版本是因为 kt 动态代理 method!!.invoke(this.view,args)  这个报错
 * IllegalArgumentException: method com.trust.demo.trusttool.mvptest.MVPTestActivity.
 * logingStatus argument 1 has type java.lang.String, got $Proxy0
 * 换成java版就可以了
 */
 abstract class TrustPresenter <V : TrustView>{
    private var mView:V? =null
    private var proxyView :V? = null

    fun isNull():Boolean{
        if (this.mView == null) {
            return true
        }
        return false
    }
    fun getView():V{return  proxyView!!}

    fun attachView(view: V){
        mView = view
        //动态代理
        //1：类加载器
        val classLoader = view.javaClass.classLoader
        //2：代理接口
        val interfaces = view.javaClass.interfaces
        //3：方法回调
        val handler = TrustViewInvocationHandler(mView)
        proxyView = Proxy.newProxyInstance(classLoader, interfaces, handler) as V
    }

    fun detachView(){
        this.mView = null
    }

  private inner class TrustViewInvocationHandler : InvocationHandler{
      private var view :TrustView ? = null

      constructor(view: TrustView?) {
          this.view = view
      }


      override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            //检测是否为null
          if (isNull()) {
              //不用回调
              return null
          }
          //执行回调
          return method!!.invoke(this.view,args)
      }


  }
}
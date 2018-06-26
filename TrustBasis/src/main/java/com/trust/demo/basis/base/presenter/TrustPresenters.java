package com.trust.demo.basis.base.presenter;

import com.trust.demo.basis.base.veiw.TrustView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Trust on 2018/6/26.
 *java 动态代理可行
 */

public abstract class TrustPresenters <V extends TrustView>{

    private V mView = null;
    private V mProxyView  = null;


    public boolean isNull(){
        if (this.mView == null) {
            return true;
        }
        return false;
    }

    public V getView(){return  mProxyView;}


    public void  attachView(V view){
        mView = view;
        //动态代理
        //1：类加载器
        ClassLoader classLoader = view.getClass().getClassLoader();
        //2：代理接口
        Class<?>[] interfaces = view.getClass().getInterfaces();
        //3：方法回调
        InvocationHandler handler =new TrustViewInvocationHandler(mView);
        mProxyView = (V) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }

    public void detachView(){
        this.mView = null;
    }

    class TrustViewInvocationHandler implements InvocationHandler {
        private V view;

        public TrustViewInvocationHandler(V view) {
            this.view = view;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isNull()){
                return null;
            }
            return method.invoke(this.view,args);
        }
    }
}



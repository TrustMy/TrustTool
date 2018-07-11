package com.trust.demo.trusttool.mvptest.testrequest;

import android.os.Environment;

import com.trust.demo.basis.base.model.RequestResultListener;
import com.trust.demo.basis.base.model.TrustModel;
import com.trust.demo.basis.base.model.http.TrustRetrofitModel;
import com.trust.demo.trusttool.mvptest.LoginModelInterface;
import com.trust.demo.trusttool.mvptest.OnLoginFinushedInterface;
import com.trust.retrofit.net.TrustRetrofitUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import static com.trust.demo.basis.base.model.http.TrustRetrofitModel.PUT_RAW;


/**
 * Created by Trust on 2018/6/29.
 */

public class RetrofitModuleTest extends TrustModel<TrustRetrofitModel> implements LoginModelInterface{

    public RetrofitModuleTest() {
    }

    @NotNull
    @Override
    public TrustRetrofitModel createRequestModule() {
        return new TrustRetrofitModel();
    }

    @Override
    public void login(String name, String pwd, final OnLoginFinushedInterface onLoginFinushedInterface) {

     /*   getRequestModule().upload("file", Environment.getExternalStorageDirectory()+"/like.png",
                 new RequestResultListener<String>() {
                    @Override
                    public void resultSuccess(String bean) {
                        onLoginFinushedInterface.resultLogin(bean);
                    }
                    @Override
                    public void resultError(Throwable e) {
                        onLoginFinushedInterface.error(e.getMessage());
                    }
                },String.class);*/
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("userName", "13892929789");
        map.put("password", "123456");
        map.put("userType", 1);
        map.put("imsi", "1234567890");
        map.put("appPlatform", "Android");
        map.put("appVersion", "1.0.0");

     getRequestModule().requestJsonParams(
             "rest/user/login",
             TrustRetrofitUtils.POST_RAW,
             map, new RequestResultListener<String>() {
         @Override
         public void resultSuccess(String bean) {
             onLoginFinushedInterface.resultLogin(bean);
         }

         @Override
         public void resultError(Throwable e) {
             onLoginFinushedInterface.error(e.toString());
         }

                 @Override
                 public void netWorkError(String msg) {
                     onLoginFinushedInterface.error(msg);
                 }
             },String.class);
    }

    @Override
    public void check(HashMap<String, Object> map, final OnLoginFinushedInterface onLoginFinushedInterface) {

        getRequestModule().requestGet("rest/vehicleLocation",
                map,
                new RequestResultListener<String>() {
                    @Override
                    public void resultSuccess(String bean) {
                        onLoginFinushedInterface.resultLogin(bean);
                    }

                    @Override
                    public void resultError(Throwable e) {
                        onLoginFinushedInterface.error(e.toString());
                    }

                    @Override
                    public void netWorkError(String msg) {
                        onLoginFinushedInterface.error(msg.toString());
                    }
                },String.class);
    }
}

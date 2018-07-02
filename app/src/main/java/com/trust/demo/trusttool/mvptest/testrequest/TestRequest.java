package com.trust.demo.trusttool.mvptest.testrequest;

import android.support.annotation.NonNull;

import com.trust.demo.basis.base.model.RequetResultListener;
import com.trust.demo.basis.base.model.TrustHttpRequestModel;
import com.trust.demo.basis.trust.utils.TrustLogUtils;
import com.trust.retrofit.net.TrustRetrofitUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Trust on 2018/6/29.
 */

public class TestRequest extends TrustHttpRequestModel {

    @Override
    public <T> void requestGet(String url, HashMap<String, Object> params, RequetResultListener<T> requetResultListener, Class<T> t) {
        Observable<String> observable = getBuild(url, params).get();
        request(observable,requetResultListener,t);
    }

    @Override
    public void requestPost(String url, HashMap<String,Object> params,RequetResultListener requetResultListener) {
        Observable<String> observable = getBuild(url, params).post();
//        request(observable,requetResultListener);
    }

    @Override
    public void requestPut(String url, HashMap<String,Object> params,RequetResultListener requetResultListener) {
        Observable<String> observable = getBuild(url, params).put();
//        request(observable,requetResultListener);
    }

    @Override
    public void requestDelete(String url, HashMap<String,Object> params,RequetResultListener requetResultListener) {
        Observable<String> observable = getBuild(url, params).delete();
//        request(observable,requetResultListener);
    }



    private <T> void request(Observable<String> observable, final RequetResultListener<T> requetResultListener, final Class<T> t) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        requetResultListener.resultSuccess((T) TrustAnalysis(s,t));
                        TrustLogUtils.d("这事返回得结果"+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        requetResultListener.resultError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @NonNull
    private TrustRetrofitUtils getBuild(String url, HashMap<String, Object> params) {
        return TrustRetrofitUtils
                .create()
                .url(url)
                .params(params == null?new HashMap<String, Object>():params)
                .build();
    }

}

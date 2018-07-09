package com.trust.demo.basis.base.model.http;

import android.support.annotation.NonNull;

import com.trust.demo.basis.base.model.RequestResultListener;
import com.trust.demo.basis.base.model.TrustHttpRequestModel;
import com.trust.demo.basis.trust.utils.TrustLogUtils;
import com.trust.retrofit.net.RetrofitBuilder;
import com.trust.retrofit.net.TrustRetrofitUtils;


import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



/**
 * Created by Trust on 2018/6/29.
 *Retrofit 网络请求 model
 *
 * 必须跟TrustRetrofit 一起使用 否则就屏蔽这个类
 */

public class TrustRetrofitModel extends TrustHttpRequestModel {
    public static final int POST_RAW = TrustRetrofitUtils.POST_RAW;
    public static final int PUT_RAW = TrustRetrofitUtils.PUT_RAW;
    public static final int DELETE_RAW = TrustRetrofitUtils.DELETE_RAW;
    @Override
    public <T> void requestGet(String url, HashMap<String, Object> params, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        Observable<String> observable = getBuilder(url,null, params).get();
        request(observable,requestResultListener,clasz);
    }

    @Override
    public <T> void requestPost(String url, HashMap<String, Object> params, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        Observable<String> observable = getBuilder(url,null, params).post();
        request(observable,requestResultListener,clasz);
    }

    @Override
    public <T> void requestPut(String url, HashMap<String, Object> params, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        Observable<String> observable = getBuilder(url,null, params).put();
        request(observable,requestResultListener,clasz);
    }

    @Override
    public <T> void requestDelete(String url, HashMap<String, Object> params, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        Observable<String> observable = getBuilder(url,null, params).delete();
        request(observable,requestResultListener,clasz);
    }

    @Override
    public <T> void requestJsonParams(String url, int paramsType, HashMap<String, Object> params, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        checkRequestType(url, null, paramsType, params, requestResultListener, clasz);
    }

    @Override
    public <T> void request(String url, String header, int paramsType, HashMap<String, Object> params, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        checkRequestType(url, header, paramsType, params, requestResultListener, clasz);
    }

    private <T> void checkRequestType(String url, String header, int paramsType, HashMap<String, Object> params, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        Observable<String> observable ;

        switch (paramsType) {
            case TrustRetrofitUtils.POST_RAW:
                observable = getBuild(url, header,params,type).postRaw();
                break;
            case TrustRetrofitUtils.PUT_RAW:
                observable = getBuild(url, header,params,type).putRaw();
                break;
            case TrustRetrofitUtils.DELETE_RAW:
                observable = getBuild(url, header,params,type).deleteRaw();
                break;
            default:
                observable = getBuild(url, header,params,type).postRaw();
                break;
        }
        request(observable,requestResultListener,clasz);
    }

    @Override
    public <T> void upload(String url, String filePath, RequestResultListener<T> requestResultListener, Class<T> clasz) {
        Observable<String> observable = getBuild(url, filePath,type).upload();
        request(observable,requestResultListener,clasz);
    }


    private <T> void request(Observable<String> observable, final RequestResultListener<T> requestResultListener, final Class<T> clasz) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        requestResultListener.resultSuccess((T) getTrustAnalysis(s,clasz));
                        TrustLogUtils.d("这事返回得结果"+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestResultListener.resultError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * https表单请求
     * @param url 地址
     * @param params  参数
     *
     */
    @NonNull
    private TrustRetrofitUtils getBuild(String url, HashMap<String, Object> params,int type) {
        return TrustRetrofitUtils
                .create()
                .url(url)
                .params(params == null?new HashMap<String, Object>():params)
                .build(type);
    }



    /**
     * 可选 http 或者https 指定请求头  请求
     * @param url 地址
     * @param headler  请求头
     * @param params  参数
     * @param type  请求类型
     *
     */
    @NonNull
    private TrustRetrofitUtils getBuild(String url, String headler, HashMap<String, Object> params , int type) {
        RetrofitBuilder builder = TrustRetrofitUtils
                .create()
                .url(url);
        if (headler != null) {
            builder.body(headler,params==null?"":new JSONObject(params).toString());
        }else{
            builder.body(params==null?"":new JSONObject(params).toString());
        }
        return builder.build(type);
    }




    /**
     * https  上传请求
     * @param url 地址
     * @param filePath 文件路径
     *
     */
    @NonNull
    private TrustRetrofitUtils getBuild(String url, String filePath,int type) {
        return TrustRetrofitUtils
                .create()
                .url(url)
                .file(filePath)
                .build(type);
    }



    private TrustRetrofitUtils getBuilder(String url ,  String headler, HashMap<String, Object> params){
        return  headler == null? getBuild(url,params,type):getBuild(url,headler,params,type);
    }

}

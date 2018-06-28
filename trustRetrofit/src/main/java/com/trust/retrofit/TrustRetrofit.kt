package com.trust.modular

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.trust.retrofit.ssl.TrustAllCerts
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import kotlin.collections.HashMap

/**
 * Created by Trust on 2018/4/9.
 *
 */
class TrustRetrofit (context: Context ,isSSL :Boolean , baseUrl: String){
    private val mContext:Context = context.applicationContext
    private var fileName:String?=null//服务器证书
    private var pwd:String?=null//证书密码
    private val mIsSSL :Boolean = isSSL
    private val mbaseUrl :String = baseUrl
    private var mRetrofit:Retrofit? = null
    private var mTimeOut : Long = 15
    private var mRequestBody:RequestBody? = null
    private var mToken:String? = null
    private var mHandlers :Headers.Builder=Headers.Builder()

    private val SERVER_ERROR = 500
    private val mTrustLoggingInterceptor :TrustLoggingInterceptor = TrustLoggingInterceptor(this)
    companion  object {
        @SuppressLint("StaticFieldLeak")
        var trustRetrofit:TrustRetrofit? = null

        fun create(context:Context,baseUrl: String) :TrustRetrofit{
            if (trustRetrofit == null) {
                trustRetrofit = TrustRetrofit(context,false,baseUrl)

            }
            trustRetrofit!!.initRetrofit()
            return trustRetrofit as TrustRetrofit
        }

        fun create(context:Context,baseUrl: String,isSSL: Boolean,fileName: String,pwd:String) : TrustRetrofit {
            if (trustRetrofit == null) {
                trustRetrofit = TrustRetrofit(context,isSSL,baseUrl)
            }
            trustRetrofit!!.fileName = fileName
            trustRetrofit!!.pwd = pwd
            trustRetrofit!!.initRetrofit()
            return trustRetrofit as TrustRetrofit
        }
    }

    /**
     * 初始化retrofit
     */
    private fun initRetrofit(){
        mRetrofit = Retrofit.Builder()
                .baseUrl(mbaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(iniOkhttp())
                .addConverterFactory(GsonConverterFactory.create()).build()
    }

    /**
     * 初始化okhttp
     */
    private fun iniOkhttp(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                .readTimeout(mTimeOut, TimeUnit.SECONDS)
                .addInterceptor(mTrustLoggingInterceptor)
                .addInterceptor(mTokenInterceptor)
        if (mIsSSL) {
            if (!fileName.equals("")) {
                okHttpClient.sslSocketFactory(getSSLSocketFactory()!!, TrustAllCerts())
                        .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
            }else{
                okHttpClient.sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), TrustAllCerts())
                        .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
            }

        }
        return okHttpClient.build()
    }

    /**
     * 获取证书流
     */
    fun getInputStream () : InputStream{
        return mContext.assets.open(fileName)
    }

    /**
     * 获取证书密码
     */
    fun getPwd() :CharArray{
        return  pwd!!.toCharArray()
    }

    /**
     * 添加SSL认证
     */
    private fun getSSLSocketFactory(): SSLSocketFactory? {

        try {
            //初始化keystore
            val clientKeyStore = KeyStore.getInstance("BKS")
            clientKeyStore.load(getInputStream(),getPwd())
            val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm())

            keyManagerFactory.init(clientKeyStore, "000000".toCharArray())

            val sslContext = SSLContext.getInstance("TLS")

            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())

            trustManagerFactory.init(clientKeyStore)

            /*   sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers()
                       , new SecureRandom());*/


            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom()
                    //第一个参数是验证服务器返回 第二个参数 是请求的时候带着 服务器的证书让服务器验证的
            )

            return sslContext.socketFactory

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun <E>getmRetrofit(clasz :Class<E>): E {
        return mRetrofit!!.create(clasz)
    }

    fun <T> connection (observable:Observable<T> ,callBack :TrustRetrofitCallBack<T>)   {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t->
                    callBack.accept(t)
                }, { t->
                    if (t is retrofit2.HttpException) {
                        val httpException = t as retrofit2.HttpException
                        if (httpException.code() == SERVER_ERROR) {
                            callBack.failure(httpException.response().errorBody()!!)
                        }
                    }else{
                        callBack.failure(t)
                    }

                })
    }

    fun <T> connection (observable:Observable<T>) :Observable<T> {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T,E> connections (observable:Observable<T>) :Observable<E>{
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) as Observable<E>
    }


    /**
     *  使用json格式请求
     */
    fun addJsonParms(map:Map<String,Any>) :RequestBody{
        mRequestBody = RequestBody.create(MediaType.
                parse("application/json; charset=utf-8"),JSONObject(map).toString())

        return  mRequestBody!!
    }

    /**
     * 使用表单格式请求
     */
    fun addFormsParms(map:Map<String,Any>) :HashMap<String,RequestBody>{
        if (map==null) {
            Throwable("addFormsParms map is null")
        }
        var maps :HashMap<String,RequestBody> = HashMap()
        for(x in map){
            maps[x.key] = RequestBody.create(MediaType.parse("text/plain"),x.value.toString())
        }
        Log.d("lh",maps.toString())
        return  maps
    }

    /**
     * 上传文件
     */

    fun addFile(file:File?):MultipartBody.Part{
        return MultipartBody.Part.
                createFormData("file",file?.name,
                        RequestBody.create(MediaType.parse("application/octet-stream"),file!!))
    }



    private val mTokenInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
            val authorised = originalRequest?.newBuilder()
                    ?.headers(mHandlers.build())
                    ?.build()
            chain.proceed(authorised!!)
    }

    /**
     *  添加token
     */
    fun addToken(name:String,value:String){
        if (mHandlers.get("Token")==null) {
            mHandlers.add(name,value)
        }
    }

    /**
     * 添加
     */
    fun addHeadlers(){

    }

}
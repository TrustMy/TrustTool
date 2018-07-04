package com.trust.retrofit.net;

import com.trust.modular.TrustHttpLoggingInterceptor;
import com.trust.retrofit.config.ConfigKeys;
import com.trust.retrofit.config.ProjectInit;
import com.trust.retrofit.ssl.TrustAllCerts;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.PUT;

/**
 * Created by Trust on 2018/6/28.
 */

public class TrustRetrofitCreator {
    public static Headers.Builder headers = new Headers.Builder();
    private static final class RetrofitHolder{
        private static final String BASE_URL = ProjectInit.getConfigurator(ConfigKeys.API_HOST);
        private static  Retrofit RETROFIT_CLIENT = getRetrofitClient();

        private static final Retrofit getRetrofitClient(){
            RETROFIT_CLIENT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttpHodler.OK_HTTP_CLIENT)
                    .build();
            return RETROFIT_CLIENT;
        }

        private static final Retrofit getRetrofitClient(boolean isTrustAll){
            RETROFIT_CLIENT = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttpHodler.getOkHtppClientSSL(isTrustAll))
                    .build();
            return RETROFIT_CLIENT;
        }

    }

    private static final class OkHttpHodler{
        private static final int TIME_OUT = 15;
        private static OkHttpClient OK_HTTP_CLIENT = getOkHtppClient();

        private static final OkHttpClient getOkHtppClient(){
            return OK_HTTP_CLIENT = new OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .addInterceptor(new TrustHttpLoggingInterceptor())
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request build = request.newBuilder().headers(headers.build()).build();
                            return chain.proceed(build);
                        }
                    })
                    .build();
        }

        private static final OkHttpClient getOkHtppClientSSL(boolean isTrustAll){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (!isTrustAll) {
                builder.sslSocketFactory(TrustSSLHodler.getSSLSocketFactory()==null?TrustAllCerts.createSSLSocketFactory()
                        :TrustSSLHodler.getSSLSocketFactory(), new TrustAllCerts());
            }else{
                builder.sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), new TrustAllCerts());
            }
            builder.hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier());
            return OK_HTTP_CLIENT = builder
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .addInterceptor(new TrustHttpLoggingInterceptor())
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request build = request.newBuilder().headers(headers.build()).build();
                            return chain.proceed(build);
                        }
                    })
                    .build();
        }



    }

    private static final class  TrustServiceHodler{
        private static final TrustService TRUST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(TrustService.class);
        private static final TrustService TRUST_SERVICE_SSL = RetrofitHolder.getRetrofitClient(false).create(TrustService.class);
        private static final TrustService TRUST_SERVICE_SSL_TRUST_ALL = RetrofitHolder.getRetrofitClient(true).create(TrustService.class);
    }

    public static TrustService getTrustService(){return TrustServiceHodler.TRUST_SERVICE;}
    public static TrustService getTrustServiceSSL(){return TrustServiceHodler.TRUST_SERVICE_SSL;}
    public static TrustService getTrustServiceSSLTrustAll(){return TrustServiceHodler.TRUST_SERVICE_SSL_TRUST_ALL;}


   private static final class TrustSSLHodler{
    private static String pwd = "132",fileName = "name";
       /**
        * 添加SSL认证
        */
       private static SSLSocketFactory getSSLSocketFactory() {
           try {
               KeyStore clientKeyStore = KeyStore.getInstance("BKS");
               clientKeyStore.load(getInputStream(),getPwd());
               KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
               keyManagerFactory.init(clientKeyStore,"000000".toCharArray());

               SSLContext sslContext = SSLContext.getInstance("TLS");

               TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

               trustManagerFactory.init(clientKeyStore);

               sslContext.init(null,trustManagerFactory.getTrustManagers(), new SecureRandom());

               return sslContext.getSocketFactory();
           } catch (KeyStoreException e) {
               e.printStackTrace();
           } catch (CertificateException e) {
               e.printStackTrace();
           } catch (NoSuchAlgorithmException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } catch (UnrecoverableKeyException e) {
               e.printStackTrace();
           } catch (KeyManagementException e) {
               e.printStackTrace();
           }


           return null;
       }

       /**
        * 获取证书密码
        */
       public static char[] getPwd(){
           return pwd.toCharArray();
       }
       /**
        * 获取证书流
        */
       public static InputStream getInputStream() throws IOException {
           return ProjectInit.getApplicationContext().getAssets().open(fileName);
       }
   }

   public static final void setSSL(String fileName,String pwd){
       TrustSSLHodler.fileName = fileName;
       TrustSSLHodler.pwd = pwd;
   }

   public static final void addToken(String token){
       if (token != null) {
           headers.add("token",token);
       }
   }
}

package com.trust.retrofit.net;

import com.trust.retrofit.config.ConfigKeys;
import com.trust.retrofit.config.ProjectInit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Trust on 2018/6/28.
 */

public class TrustRetrofitCreator {

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
    }

    private static final class OkHttpHodler{
        private static final int TIME_OUT = 60;
        private static OkHttpClient OK_HTTP_CLIENT = getOkHtppClient();

        private static final OkHttpClient getOkHtppClient(){
            return OK_HTTP_CLIENT = new OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }

    }

    private static final class  TrustServiceHodler{
        private static final TrustService TRUST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(TrustService.class);
    }

    public static TrustService getTrustService(){return TrustServiceHodler.TRUST_SERVICE;}


}

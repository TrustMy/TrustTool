package com.trust.retrofit.config;

import android.text.style.TtsSpan;

import com.trust.retrofit.net.TrustRetrofitCreator;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by Trust on 2018/6/28.
 */

public class Configurator {
    private static final HashMap<Object,Object> CONFIGS = new HashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private static class Hodler{
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance(){
        return Hodler.INSTANCE;
    }

    private Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(),false);
    }

    //获取配置信息
    final  HashMap<Object,Object> getConfigs(){
        return CONFIGS;
    }

    final  <T> T getConfiguration(Object key){
        checkConfiguration();
        final  Object value = CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + "is Null");
        }
        return (T)CONFIGS.get(key);
    }


    //配置APIHOST
    public final Configurator setApiHost(String host){
        CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

    //配置拦截器
    public final Configurator setInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }
//
    //检查配置是否完成
    private void checkConfiguration(){
        final boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configguration is not ready,call configure");
        }
    }

    //配置完成
    public final void  configure(){
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(),true);
    }

    public  Configurator setSSL(String fileName,String pwd){
        TrustRetrofitCreator.setSSL(fileName,pwd);
        return this;
    }


}

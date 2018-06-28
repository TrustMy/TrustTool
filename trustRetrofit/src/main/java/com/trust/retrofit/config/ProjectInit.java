package com.trust.retrofit.config;

import android.content.Context;

/**
 * Created by Trust on 2018/6/28.
 */

public class ProjectInit {
    public static Configurator init(Context context){
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static  Configurator getConfigurator(){return  Configurator.getInstance();}

    public static <T> T getConfigurator(Object key){
        return getConfigurator().getConfiguration(key);
    }


    public static Context getApplicationContext(){
        return getConfigurator(ConfigKeys.APPLICATION_CONTEXT.name());
    }
}

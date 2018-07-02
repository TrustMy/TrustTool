package com.trust.demo.basis.trust.utils;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Trust on 2017/8/28.
 */

public class TrustAnalysis implements Serializable {
    /**
     *
     * @param raw 需要得到的Bean
     * @param json 需要解析的json字符串
     * @param <T>
     * @return
     */
    private static Gson gson;
    public static <T> T resultTrustBean(String json, Class raw ){
        if (gson == null) {gson = new Gson();}
        Type objectType = type(raw,Object.class);
        return gson.fromJson(json, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args){
        return  new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }

            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}

package com.trust.demo.trusttool;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;
import com.trust.demo.basis.base.TrustApplication;
import com.trust.retrofit.config.ProjectInit;

/**
 * Created by Trust on 2017/11/3.
 */

public class APP extends TrustApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        /*http://192.168.1.139:8081
        https://syvehicle.cn/tomcat/*/
        ProjectInit.init(this).setApiHost("https://syvehicle.cn/tomcat/")
                .setSSL("cacerts_sy.bks","changeit")
                .configure();
    }
}

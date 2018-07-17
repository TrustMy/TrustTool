package com.trust.demo.trusttool;


import com.alibaba.android.arouter.launcher.ARouter;
import com.trust.demo.basis.base.TrustApplication;
import com.trust.loginregisterlibrary.callback.AppServer;
import com.trust.modular.TrustRetrofit;
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

        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}

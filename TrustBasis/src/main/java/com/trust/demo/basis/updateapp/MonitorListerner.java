package com.trust.demo.basis.updateapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Trust on 2017/10/30.
 */

public class MonitorListerner extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 安装
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
        }
        // 覆盖安装
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
            String packageName = intent.getDataString();
            startApp(context);
        }
        // 移除
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
        }
    }



    public void startApp(final Context context){
        try{

            Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            context.startActivity(intent);
        }catch(Exception e){
            Toast.makeText(context, "没有安装", Toast.LENGTH_LONG).show();
        }
    }
}

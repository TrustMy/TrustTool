package com.trust.demo.basis.updateapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Trust on 2017/7/3.
 */

public class APPVersion {
    public static String getVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();

        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
                0);
        return packInfo.versionName;
    }
}

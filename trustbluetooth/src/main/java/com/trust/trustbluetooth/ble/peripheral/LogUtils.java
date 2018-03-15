package com.trust.trustbluetooth.ble.peripheral;

import android.util.Log;

public class LogUtils {

	
	public static boolean open=true;
	
	
	public static void e(String TAG,String msg){
		if(open)
		Log.e(TAG, msg);
	}
	
}

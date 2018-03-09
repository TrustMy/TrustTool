package com.trust.demo.trusttool;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.trust.demo.basis.updateapp.CheckVersionTask;
import com.trust.demo.basis.updateapp.UpdataInfo;
import com.trust.trustbluetooth.BluetoothFactory;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BluetoothFactory.OnBluetoothFactoryCallBack {
    public static Context context ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
      /*  //:https://58.246.120.86/apk/20171113173443_SDCloudPlatform-1.0app-release.apk
        CheckVersionTask checkVersionTask = new CheckVersionTask(this,"","5.0", new UpdataInfo("1.0","https://58.246.120.86/apk/20171113181651_SDCloudPlatform-1.0test_0.1.1.apk","这是描述"));
        Thread thread = new Thread(checkVersionTask);
        thread.start();*/

        BluetoothFactory bluetoothFactory = new BluetoothFactory(this,this);
        bluetoothFactory.showBlue(this);
    }


    @Override
    public void onFoundSuccessBlueDevice(@NotNull BluetoothDevice bluetoothDevice) {

    }

    @Override
    public void onStartFoundBlueDevice() {

    }

    @Override
    public void onFinishedFoundBlueDevice() {

    }

    @Override
    public void onStandByBlue(boolean isStandBy) {

    }

    @Override
    public void onConnectStatus(boolean isConnect) {

    }

    @Override
    public void onDissConnect() {

    }

    @Override
    public void onReceiveData(@NotNull String data) {

    }
}

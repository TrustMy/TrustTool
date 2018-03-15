package com.trust.demo.trusttool;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.trust.trustbluetooth.BlueActivity;
import com.trust.trustbluetooth.ble.peripheral.PeripheralActivity;

public class MainActivity extends AppCompatActivity {
    public static Context context ;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;

    Handler handler = new Handler();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
      /*  //:https://58.246.120.86/apk/20171113173443_SDCloudPlatform-1.0app-release.apk
        CheckVersionTask checkVersionTask = new CheckVersionTask(this,"","5.0", new UpdataInfo("1.0","https://58.246.120.86/apk/20171113181651_SDCloudPlatform-1.0test_0.1.1.apk","这是描述"));
        Thread thread = new Thread(checkVersionTask);
        thread.start();*/
/*
        bluetoothFactory = new BluetoothFactory(this,this);
        bluetoothFactory.startFind();*/






      /*  bluetoothBleAccept = new BluetoothBleAccept(this,this);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothBleAccept.startFindDevice();


                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                    Toast.makeText(context, "不支持BLE", Toast.LENGTH_SHORT).show();

                }
            }
        });
*/

      findViewById(R.id.ble_btn).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(context, BlueActivity.class));
          }
      });
    }


}

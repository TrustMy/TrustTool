package com.trust.demo.trusttool;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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

import com.trust.live.TrustLiveActivity;
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


      findViewById(R.id.ble_btn).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(context, BlueActivity.class));
          }
      });

      findViewById(R.id.live_btn).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(context, TrustLiveActivity.class));
          }
      });

    }


}

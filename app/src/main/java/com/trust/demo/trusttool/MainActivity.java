package com.trust.demo.trusttool;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.trust.live.TrustLiveActivity;
import com.trust.maplibrary.MapUtils;
import com.trust.maplibrary.bean.LocationBean;
import com.trust.maplibrary.callback.MapUtilsCallBack;
import com.trust.maplibrary.dialog.MapDialog;
import com.trust.trustbluetooth.BlueActivity;

import org.jetbrains.annotations.NotNull;

import java.math.MathContext;


public class MainActivity extends AppCompatActivity {
    public static Context context ;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private  MapDialog mapDialog;
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

      findViewById(R.id.open_start_btn).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mapDialog = MapUtils.Companion.getSingleton(context, new MapUtilsCallBack() {
                  @Override
                  public void isUseSuccess(boolean isUse, @NotNull String msg) {
                      if (isUse) {
                          mapDialog.dismiss();
                      }
                      Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                  }
              }).showMapDialog(getFragmentManager(),new LocationBean(31.0998709364, 121.6226745098));
          }
      });
    }


}

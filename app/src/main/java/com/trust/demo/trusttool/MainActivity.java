package com.trust.demo.trusttool;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.trust.demo.basis.trust.utils.TrustLogUtils;
import com.trust.demo.basis.trust.utils.TrustHttpUtils;
import com.trust.live.TrustLiveActivity;
import com.trust.maplibrary.MapUtils;
import com.trust.maplibrary.bean.LocationBean;
import com.trust.maplibrary.callback.MapUtilsCallBack;
import com.trust.maplibrary.dialog.MapDialog;
import com.trust.statusbarlibrary.TrustStatusBarUtils;
import com.trust.statusbarlibrary.view.TrustStatusBarView;
import com.trust.trustbluetooth.BlueActivity;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    public static Context context ;
    public static Activity activity ;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private  MapDialog mapDialog;
    Handler handler = new Handler();
    int i = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;

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

        TrustHttpUtils httpCheckTool = new TrustHttpUtils(this);
        TrustLogUtils.d("网络状态:"+httpCheckTool.isNetworkAvailable());
        TrustLogUtils.d("网络类型:"+httpCheckTool.getNetWorkStates());

        final TrustStatusBarView view = findViewById(R.id.test_trust_bar);

        TrustStatusBarUtils.Companion.getSingleton(this)
                .setStatusBarGradient(this,null,
                        TrustStatusBarView.Companion.getRIGHT());

        
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setAngle(TrustStatusBarView.Companion.getLEFT());
            }
        });
        findViewById(R.id.test_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(context, TBSActivity.class));
            }
        });

    }



}

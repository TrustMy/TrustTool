package com.trust.demo.trusttool.mvptest;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dn.tim.lib_permission.annotation.Permission;
import com.dn.tim.lib_permission.annotation.PermissionCanceled;
import com.trust.demo.trusttool.R;

public class PermissActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permiss);
    }


    @Permission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void test(View view) {
        Toast.makeText(this, "请求两个权限成功（写和相机）", Toast.LENGTH_SHORT).show();
    }


    @PermissionCanceled()
    private void cancel() {
        Log.i("lhh", "writeCancel: " );
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
    }
}

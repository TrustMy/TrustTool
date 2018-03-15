package com.trust.trustbluetooth.ble

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.trust.trustbluetooth.R
import com.trust.trustbluetooth.ble.central.CentralActivity
import com.trust.trustbluetooth.ble.peripheral.PeripheralActivity
import kotlinx.android.synthetic.main.activity_ble.*

class BleActivity : AppCompatActivity() ,View.OnClickListener{

    var context :Context?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ble)
        context = this
        ble_phone_btn.setOnClickListener(this)
        ble_peripheral_btn.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        var intent = Intent()
        when (v!!.id) {
            //蓝牙设备
            R.id.ble_peripheral_btn -> {
                intent.setClass(context, PeripheralActivity::class.java)
            }
            //手机蓝牙
            R.id.ble_phone_btn -> {
                intent.setClass(context, CentralActivity::class.java)
            }
        }
        startActivity(intent)
    }
}

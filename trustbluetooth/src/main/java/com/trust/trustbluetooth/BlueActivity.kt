package com.trust.trustbluetooth

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.trust.trustbluetooth.ble.BleActivity
import com.trust.trustbluetooth.ble.central.CentralActivity
import com.trust.trustbluetooth.blue.TraditionActivity
import kotlinx.android.synthetic.main.activity_blue.*
class BlueActivity : AppCompatActivity() ,View.OnClickListener{
    var context : Context?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blue)
        context = this
        bluetooth_btn.setOnClickListener(this)
        bluetooth_ble_btn.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        var intent = Intent()
        when (v!!.id) {
            R.id.bluetooth_btn -> {//
                intent.setClass(context, TraditionActivity::class.java)
            }
            R.id.bluetooth_ble_btn -> {//低功耗
                intent.setClass(context, BleActivity::class.java)
            }
        }
        startActivity(intent)
    }
}

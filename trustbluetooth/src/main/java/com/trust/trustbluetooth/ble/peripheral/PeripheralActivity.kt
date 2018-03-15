package com.trust.trustbluetooth.ble.peripheral

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.Toast
import com.trust.trustbluetooth.R
import com.trust.trustbluetooth.ble.peripheral.callback.PeripheralCallBack
import kotlinx.android.synthetic.main.activity_peripheral.*

class PeripheralActivity : AppCompatActivity() ,View.OnClickListener{
    var per :PeripheralFactory? = null
    var context:Context ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peripheral)
        context = this
        init()
        start_server_btn.setOnClickListener(this)
        send_btn.setOnClickListener(this)
        start_broadcast_btn.setOnClickListener(this)
    }

    private fun init() {
        per = PeripheralFactory()

    }


    override fun onClick(v: View?) {
        LogUtils.e("PeripheralActivity", "点击了")
        when (v!!.id) {
            R.id.start_server_btn -> {//开启服务
                var blueManager : BluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE)as BluetoothManager
                per!!.getPeripheral(this,blueManager,mPeripheralCallBack)
            }
            R.id.start_broadcast_btn ->{//开启广播
                per!!.startAdvertising()
            }
            R.id.send_btn -> {//发送数据
                var toString = send_data_ed.text.trim().toString()
                var sendData = per!!.sendData(toString)
                if (sendData) {
                    Toast.makeText(context,"发送成功!",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"发送失败，请确保有远程设备连接！",Toast.LENGTH_LONG).show()
                }
            }
            else -> {
            }
        }
    }


    var mPeripheralCallBack = object :PeripheralCallBack{
        override fun onStartPeripheralStatus(status: Boolean, errorCode: Int) {
            if (status) {
                server_status_tv.text = "当前广播：已经开启"
            }else{
                server_status_tv.text = "当前广播：开启失败:"+errorCode
            }
        }

        override fun onAddServiceStatus(status: Boolean) {
            if (status) {
                server_status_tv.text = "当前服务：已经开启"
            }else{
                server_status_tv.text = "当前服务：开启失败"
            }
        }

        override fun onConnectStatusChange(isConnect: Boolean, device: BluetoothDevice?, status: Int, newState: Int) {

            runOnUiThread(Runnable {
                if (isConnect) {
                    server_status_tv.text = "与"+device!!.name+"连接成功"
                }else{

                    server_status_tv.text = "与"+device!!.name+"连接失败"
                }
            })
        }

        override fun onResultData(device: BluetoothDevice?, requestId: Int, characteristic: BluetoothGattCharacteristic?, preparedWrite: Boolean, responseNeeded: Boolean, offset: Int, value: ByteArray?, msg: String) {
          runOnUiThread(Runnable {
              var msgString :String= result_data_string_tv.text.toString()
              msgString = msgString+"\n"+msg
              result_data_string_tv.text = msgString

              var msgByte : String = result_data_byte_tv.text.toString()
              msgByte = msgByte +"\n"+ OKBLEDataUtils.Bytes2HexString(value)
              result_data_byte_tv.text = msgByte

          })
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDestroy() {
        super.onDestroy()
        per!!.stopAdvertising()
    }
}

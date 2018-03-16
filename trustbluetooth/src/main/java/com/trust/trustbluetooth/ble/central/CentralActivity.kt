package com.trust.trustbluetooth.ble.central

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.trust.trustbluetooth.R
import com.trust.trustbluetooth.ble.central.`interface`.CentralBluetoothCallBack
import com.trust.trustbluetooth.ble.peripheral.LogUtils
import com.trust.trustbluetooth.ble.peripheral.OKBLEDataUtils
import kotlinx.android.synthetic.main.activity_central.*
import java.util.ArrayList

class CentralActivity : AppCompatActivity() ,View.OnClickListener , CentralBluetoothCallBack {


    val TAG = "CentralActivity"
    private var discoveryPro: ProgressBar? = null
    private var mCentralFactory:CentralFactory? = null
    private var mContext:Context? = null
    private var foundList: ListView? = null
    private var foundDevices: MutableList<BluetoothDevice>? = null
    private var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_central)

        central_connected_device_btn.setOnClickListener(this)
        central_show_blue_btn.setOnClickListener(this)
        central_send_data_btn.setOnClickListener(this)
        central_disconnection_btn.setOnClickListener(this)
        mContext = this
        mCentralFactory = CentralFactory()
        mCentralFactory!!.init(mContext!!,this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            //连接设备
            R.id.central_connected_device_btn -> {
                startFindDevice()
            }
            //蓝牙可见
            R.id.central_show_blue_btn -> {
                val intent = Intent(
                        BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivity(intent)
            }
            R.id.central_send_data_btn ->{
                mCentralFactory!!.writeData(central_send_data_ed.text.toString().toByteArray())
            }
            R.id.central_disconnection_btn ->{
                mCentralFactory!!.disConnction()
            }
        }
    }



    fun startFindDevice(){
        mCentralFactory!!.startFoundDevice()

        var inflater = layoutInflater
        var view :View= inflater.inflate(R.layout.dialog, null)
        discoveryPro = view.findViewById(R.id.discoveryPro)
        var bondedList = view.findViewById(R.id.foundList)as ListView

        foundList = view.findViewById(R.id.foundList)as ListView

        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // 将已配对的蓝牙设备显示到第一个ListView中
        val deviceSet = bluetoothAdapter.bondedDevices
        val bondedDevices = ArrayList<BluetoothDevice>()
        if(deviceSet.size > 0){
            val it = deviceSet.iterator()
            while (it.hasNext()) {
                val device = it.next() as BluetoothDevice
                bondedDevices.add(device)
            }
        }
        bondedList.adapter =MyAdapter(mContext!!,bondedDevices)

        foundDevices = ArrayList()
        foundList!!.adapter = MyAdapter(mContext, foundDevices)

        bondedList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var device = bondedDevices[position]
            //连接
            connectedDevice(device)
        }
        foundList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var device = (foundDevices as ArrayList<BluetoothDevice>)[position]
            //连接
            connectedDevice(device)
        }

        var builder = AlertDialog.Builder(mContext!!)
        builder.setMessage("请选择要连接的蓝牙设备").setPositiveButton("取消",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                mCentralFactory!!.stopFoundDevice()
            }
        })
        builder.setView(view)
        builder.create()
        dialog = builder.show()
    }

    fun connectedDevice(device: BluetoothDevice){
        mCentralFactory!!.stopFoundDevice()
        dialog!!.dismiss()
        Toast.makeText(this, "正在与 " + device.name + " 连接 .... ",
                Toast.LENGTH_LONG).show()

        mCentralFactory!!.connctionDevice(device)
    }



    override fun isStandByBleBlue(status: Boolean) {
        LogUtils.e(TAG, "isStandByBleBlue:$status")

        runOnUiThread({
            if (status) {
                central_blue_status_tv.text = "支持低功耗蓝牙"
            }else{
                central_blue_status_tv.text = "不支持低功耗蓝牙"
            }
        })
    }

    override fun onStartFindingDevice() {
        LogUtils.e(TAG, "onStartFindingDevice")
        runOnUiThread({
            discoveryPro!!.visibility = View.VISIBLE
        })
    }

    override fun onFinishedFoundBlueDevice() {
        LogUtils.e(TAG, "onFinishedFoundBlueDevice")
        runOnUiThread({
            discoveryPro!!.visibility = View.GONE
        })
    }

    override fun onFoundSuccessBlueDevice(bluetoothDevice: BluetoothDevice) {
        LogUtils.e(TAG, "onFoundSuccessBlueDevice:"+bluetoothDevice.name)
        runOnUiThread({
            foundDevices!!.add(bluetoothDevice)
            foundList!!.adapter = MyAdapter(mContext, foundDevices)
        })
    }

    override fun onDissConnect() {
        runOnUiThread({
            central_blue_status_tv.text = "与设备断开连接"
        })
        LogUtils.e(TAG, "onDissConnect")
    }

    override fun onConnectStatus(isConnect: Boolean, newState: Int) {
        LogUtils.e(TAG, "onConnectStatus$isConnect")
        runOnUiThread({
            if (isConnect) {//连接成功
                Toast.makeText(this,  " 连接成功 ",
                        Toast.LENGTH_LONG).show()
                central_blue_status_tv.text =  " 连接成功 "
            }else{
                Toast.makeText(this,  " 连接失败 ",
                        Toast.LENGTH_LONG).show()
                central_blue_status_tv.text = " 连接失败 "
            }
        })
    }

    override fun onGattConnectStatus(isConnect: Boolean, state: Int) {
        LogUtils.e(TAG, "onGattConnectStatus$isConnect")
        runOnUiThread({
            if (isConnect) {//连接成功
                central_blue_status_tv.text =  " GATT 连接成功 "
            }else{
                if (state== -100) {
                    central_blue_status_tv.text = " GATT 获取不到指定的uuid "

                }else{
                    central_blue_status_tv.text = " GATT 连接失败 "
                }
                mCentralFactory!!.disConnction()
            }
        })
    }


    override fun onDeviceResult(msg: String, value: ByteArray) {
        runOnUiThread({
            var msgString :String = central_result_string_tv.text.trim().toString()
            msgString = msgString + "\n"+msg
            central_result_string_tv.text = msgString

            var msgByte :String  = central_result_byte_tv.text.trim().toString()
            msgByte = msgByte + "\n" + OKBLEDataUtils.Bytes2HexString(value)
            central_result_byte_tv.text = msgByte
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mCentralFactory!!.unregisterReceiver()
        mCentralFactory!!.disConnction()
    }

}

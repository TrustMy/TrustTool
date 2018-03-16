package com.trust.trustbluetooth.ble.central

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import com.trust.trustbluetooth.ble.central.CentralConfig.*
import com.trust.trustbluetooth.ble.central.`interface`.CentralBluetoothCallBack
import com.trust.trustbluetooth.ble.central.`interface`.CentralBluetoothGattCallback
import java.io.IOException
import java.util.*

/**
 * Created by Trust on 2018/3/14.
 * （蓝牙手机）
 */
class CentralFactory {
    private var mContext:Context?=null
    private var mCentralBluetoothCallBack:CentralBluetoothCallBack? = null
    private var mBluetoothManager:BluetoothManager? = null
    private var mBluetoothAdapter:BluetoothAdapter? = null
    private var mCentralBluetoothGattCallback: CentralBluetoothGattCallback? = null
    private var mBluetoothGatt: BluetoothGatt? = null
    // 广播接收器，主要是接收蓝牙状态改变时发出的广播
    private var mReceiver: BroadcastReceiver? = null

    fun init( context:Context ,centralBluetoothCallBack : CentralBluetoothCallBack){
        mContext = context
        mCentralBluetoothCallBack = centralBluetoothCallBack

        mCentralBluetoothCallBack!!.isStandByBleBlue(mContext!!.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))

        mBluetoothManager = mContext!!.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        mBluetoothAdapter = mBluetoothManager!!.adapter

        mCentralBluetoothGattCallback = CentralBluetoothGattCallback()

        mReceiver = object : BroadcastReceiver(){
            @SuppressLint("MissingPermission")
            override fun onReceive(context: Context?, intent: Intent?) {
                when( intent!!.action){
                //找到设备
                    BluetoothDevice.ACTION_FOUND -> {
                        var device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        if (device != null) {
                            if (device.name != null) {
                                mCentralBluetoothCallBack!!.onFoundSuccessBlueDevice(device)
                            }
                        }
                    }
                //搜索中
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                        mCentralBluetoothCallBack!!.onStartFindingDevice()
                    }
                //搜索结束
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                        mCentralBluetoothCallBack!!.onFinishedFoundBlueDevice()
                    }
                    BluetoothDevice.ACTION_ACL_DISCONNECTED ->{
                        mCentralBluetoothCallBack!!.onDissConnect()
                    }
                }
            }

        }

        val filter1 = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val filter2 = IntentFilter(
                BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        val filter3 = IntentFilter(
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        val filter4 = IntentFilter(
                BluetoothDevice.ACTION_ACL_DISCONNECTED)
        val filter5 = IntentFilter(
                BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)

        mContext!!.registerReceiver(mReceiver, filter1)
        mContext!!.registerReceiver(mReceiver, filter2)
        mContext!!.registerReceiver(mReceiver, filter3)
        mContext!!.registerReceiver(mReceiver, filter4)
        mContext!!.registerReceiver(mReceiver, filter5)

    }

    //退出得时候清理广播
    fun unregisterReceiver(){
        mContext!!.unregisterReceiver(mReceiver)
    }


    /**
     * 开始搜索蓝牙
     */
    fun startFoundDevice(){
        if (mBluetoothAdapter!!.isDiscovering) {
            mBluetoothAdapter!!.cancelDiscovery()
        }
        mBluetoothAdapter!!.startDiscovery()
    }

    /**
     * 关闭搜索
     */
    fun stopFoundDevice(){
        mBluetoothAdapter!!.cancelDiscovery()
    }

    /**
     * 连接设备
     */
    fun connctionDevice(device: BluetoothDevice){
        if(device != null){
            mBluetoothGatt = device.connectGatt(mContext,false,mCentralBluetoothGattCallback)
            mCentralBluetoothGattCallback!!.setCentralBluetoothCallBack(mCentralBluetoothCallBack!!,mBluetoothGatt!!)
        }
    }


    /**
     * 断开连接
     *
     */
    fun disConnction(){
        mBluetoothGatt!!.disconnect()
        mBluetoothGatt!!.close()
    }

    /**
     * 写输出
     */
    fun writeData(data :ByteArray){
        mCentralBluetoothGattCallback!!.writeData(data)
    }

    /**
     * 修改 手机蓝牙 server  读写特征  uuid
     */
    fun setServerUUID(uuidString:String){
        if(uuidString == null){
            return
        }
        UUID_PHONE_SERVER = UUID.fromString(uuidString)
    }

    fun setServerUUID(uuid:UUID){
        if(uuid == null){
            return
        }
        UUID_PHONE_SERVER = uuid
    }

    fun setReadUUID(uuidString:String){
        if(uuidString == null){
            return
        }
        UUID_PHONE_READ = UUID.fromString(uuidString)
    }

    fun setReadUUID(uuid:UUID){
        if(uuid == null){
            return
        }
        UUID_PHONE_READ = uuid
    }



    fun setWriteUUID(uuidString:String){
        if(uuidString == null){
            return
        }
        UUID_PHONE_WRITE = UUID.fromString(uuidString)
    }

    fun setWriteUUID(uuid:UUID){
        if(uuid == null){
            return
        }
        UUID_PHONE_WRITE = uuid
    }
}
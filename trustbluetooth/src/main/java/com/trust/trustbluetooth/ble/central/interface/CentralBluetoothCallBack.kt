package com.trust.trustbluetooth.ble.central.`interface`

import android.bluetooth.BluetoothDevice

/**
 * Created by Trust on 2018/3/14.
 */
interface CentralBluetoothCallBack {
    /**
     * 是否支持蓝牙
     */
    fun isStandByBleBlue(status:Boolean)

    /**
     * 开始搜索蓝牙了
     */
    fun onStartFindingDevice()

    /**
     * 搜索周边蓝牙设备结束
     */
    fun onFinishedFoundBlueDevice()

    /**
     * 成功搜索到蓝牙
     */
    fun onFoundSuccessBlueDevice(bluetoothDevice: BluetoothDevice)

    /**
     * 蓝牙已经断开连接
     */
    fun onDissConnect()

    /**
     * 连接蓝牙是否成功
     */
    fun onConnectStatus(isConnect:Boolean,newState: Int)

    /**
     * 低功耗 GATT 连接状态
     */
    fun onGattConnectStatus(isConnect:Boolean,state: Int)

    /**
     * 接收设备 数据
     */
    fun onDeviceResult(msg:String,value:ByteArray)
}
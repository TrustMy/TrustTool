package com.trust.trustbluetooth.blue

/**
 * Created by Trust on 2018/3/1.
 */
interface BluetoothCallBack {
    //启动蓝牙搜索
    fun startFindDevice()
    //关闭蓝牙搜索
    fun closeFindDevice()
    //设备||对接蓝牙返回数据
    fun resultDate()
    //链接异常
    fun connectError()

}
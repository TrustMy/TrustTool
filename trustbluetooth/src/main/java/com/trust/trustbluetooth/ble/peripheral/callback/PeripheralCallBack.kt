package com.trust.trustbluetooth.ble.peripheral.callback

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic

/**
 * Created by Trust on 2018/3/14.
 */
interface PeripheralCallBack {
    /**
     * 开启设备 回调
     * status 状态
     * errorCode 开启失败时会返回errorCode
     */
    fun onStartPeripheralStatus(status: Boolean,errorCode:Int)

    /**
     * 添加指定得server  读写 uuid 是否成功
     */

    fun onAddServiceStatus(status:Boolean)

    /**
     * 远程设备连接得状态
     * isConnect  是连接 正常还是失败
     * newState 为系统返回得状态码 如果需要细致区分可以使用该参数
     */
    fun onConnectStatusChange(isConnect:Boolean,device: BluetoothDevice?, status: Int, newState: Int)

    /**
     * 接收远程设备 发送数据回调
     * msg 为转换后得String
     * value 为未转换后得byte数组 如需要自定义协议 可以使用这个参数来解析数据
     */
    fun onResultData(device: BluetoothDevice?, requestId: Int, characteristic: BluetoothGattCharacteristic?, preparedWrite: Boolean, responseNeeded: Boolean, offset: Int, value: ByteArray?,msg:String)
}
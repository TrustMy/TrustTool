package com.trust.trustbluetooth.ble.peripheral.callback

import android.bluetooth.*
import android.bluetooth.BluetoothAdapter.STATE_CONNECTED
import com.trust.trustbluetooth.ble.peripheral.LogUtils

/**
 * Created by Trust on 2018/3/14.
 */
class TrustGattServerCallBack (peripheralCallBack: PeripheralCallBack ):BluetoothGattServerCallback(){
    val TAG = "TrustGattServerCallBack"
    private var mPeripheralCallBack: PeripheralCallBack? = peripheralCallBack
    private var mDevice: BluetoothDevice? = null
    private var mRequestId: Int? = null
    private var mOffset: Int? = null
    private var mBluetoothGattServer: BluetoothGattServer? =null

    fun init(bluetoothGattServer: BluetoothGattServer?){
        mBluetoothGattServer = bluetoothGattServer
    }

    override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
        super.onConnectionStateChange(device, status, newState)

        if (newState == STATE_CONNECTED) {
            mPeripheralCallBack!!.onConnectStatusChange(true,device,status,newState)
        }else{
            mPeripheralCallBack!!.onConnectStatusChange(false,device,status,newState)
        }
        //这个device是中央设备， mac地址会 因为 中央（手机）蓝牙重启而变化
        LogUtils.e(TAG, " onConnectionStateChange:" + status + " newState:" + newState + " devicename:" + device!!.name + " mac:" + device!!.address)
    }

    override fun onServiceAdded(status: Int, service: BluetoothGattService?) {
        super.onServiceAdded(status, service)
        LogUtils.e(TAG, " onServiceAdded status:" + status + " service:" + service!!.uuid.toString())
    }

    //收到主机端 （蓝牙手机端发送读取请求后调用 ）
    override fun onCharacteristicReadRequest(device: BluetoothDevice?, requestId: Int, offset: Int, characteristic: BluetoothGattCharacteristic?) {
        super.onCharacteristicReadRequest(device, requestId, offset, characteristic)
        mDevice = device
        mRequestId = requestId
        mOffset = offset
        LogUtils.e(TAG, " onCharacteristicReadRequest requestId:" + requestId + " offset:" + offset + " characteristic:" + characteristic!!.uuid.toString())
    }
    //收到远程设备发送数据回调
    override fun onCharacteristicWriteRequest(device: BluetoothDevice?, requestId: Int, characteristic: BluetoothGattCharacteristic?, preparedWrite: Boolean, responseNeeded: Boolean, offset: Int, value: ByteArray?) {
        super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value)
        LogUtils.e(TAG, " onCharacteristicWriteRequest requestId:" + requestId + " preparedWrite:" + preparedWrite + " responseNeeded:" + responseNeeded + " offset:" + offset + " value:" + String(value!!) + " characteristic:" + characteristic!!.uuid.toString())
        mPeripheralCallBack!!.onResultData(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value,String(value))
        //通知远程设备  成功收到
        mBluetoothGattServer!!.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, "success".toByteArray())
    }

    override fun onDescriptorReadRequest(device: BluetoothDevice?, requestId: Int, offset: Int, descriptor: BluetoothGattDescriptor?) {
        super.onDescriptorReadRequest(device, requestId, offset, descriptor)
        LogUtils.e(TAG, " onDescriptorReadRequest requestId:" + requestId + " offset:" + offset + " descriptor:" + descriptor!!.uuid.toString())
    }

    override fun onDescriptorWriteRequest(device: BluetoothDevice?, requestId: Int, descriptor: BluetoothGattDescriptor?, preparedWrite: Boolean, responseNeeded: Boolean, offset: Int, value: ByteArray?) {
        super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value)
        LogUtils.e(TAG, " onDescriptorWriteRequest requestId:" + requestId + " preparedWrite:" + preparedWrite + " responseNeeded:" + responseNeeded + " offset:" + offset + " value:" + String(value!!) + " characteristic:" + descriptor!!.uuid.toString())
    }

    override fun onExecuteWrite(device: BluetoothDevice?, requestId: Int, execute: Boolean) {
        super.onExecuteWrite(device, requestId, execute)
        LogUtils.e(TAG, " onExecuteWrite requestId:$requestId execute:$execute")
    }

    override fun onNotificationSent(device: BluetoothDevice?, status: Int) {
        super.onNotificationSent(device, status)
        LogUtils.e(TAG, " onNotificationSent status:$status")
    }

    override fun onMtuChanged(device: BluetoothDevice?, mtu: Int) {
        super.onMtuChanged(device, mtu)
        LogUtils.e(TAG, " onMtuChanged mtu:$mtu")
    }

    fun sendData(msg:String):Boolean{
        return if (mDevice != null && msg !=null) {
            mBluetoothGattServer!!.sendResponse(mDevice, mRequestId!!, 0, mOffset!!, msg.toByteArray())
        }else{
             false
        }
    }

}
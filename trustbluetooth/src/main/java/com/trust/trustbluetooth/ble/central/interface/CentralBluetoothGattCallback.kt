package com.trust.trustbluetooth.ble.central.`interface`

import android.bluetooth.*
import com.trust.trustbluetooth.ble.central.CentralConfig.*
import com.trust.trustbluetooth.ble.peripheral.LogUtils
import java.util.*

/**
 * Created by Trust on 2018/3/14.
 */
class CentralBluetoothGattCallback (): BluetoothGattCallback() {
    private var mCentralBluetoothCallBack:CentralBluetoothCallBack ? = null
    private var mBluetoothGatt: BluetoothGatt? = null

    private var mNotifyCharacteristic: BluetoothGattCharacteristic? = null
    private var mWriteCharacteristic: BluetoothGattCharacteristic? = null
    fun setCentralBluetoothCallBack(centralBluetoothCallBack:CentralBluetoothCallBack,bluetoothGatt: BluetoothGatt){
        mCentralBluetoothCallBack = centralBluetoothCallBack
        mBluetoothGatt = bluetoothGatt
    }

    /**
     * 蓝牙连接状态回调
     */
    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)

        when (newState) {
            BluetoothProfile.STATE_CONNECTED -> {//连接成功
                mCentralBluetoothCallBack!!.onConnectStatus(true,newState)
                    LogUtils.e("lhh","isStatus:"+mBluetoothGatt!!.discoverServices())
            }
            BluetoothProfile.STATE_DISCONNECTED -> //连接失败
                mCentralBluetoothCallBack!!.onConnectStatus(false,newState)
            else -> mCentralBluetoothCallBack!!.onConnectStatus(false,newState)
        }
    }

    //连接成功后回调接口获取特征值
    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        if (status ==  BluetoothGatt.GATT_SUCCESS) {//
            mCentralBluetoothCallBack!!.onGattConnectStatus(true,status)

            var services = mBluetoothGatt!!.services
            for(x in services){
                LogUtils.e("lhh",x.uuid.toString())
                var characteristics = x.characteristics
                for (s in characteristics){
                    LogUtils.e("lhh",s.uuid.toString())
                }
            }
            var service =  mBluetoothGatt!!.getService(UUID_PHONE_SERVER)
            if (service != null) {
                //找到服务后，查找特征值
                mNotifyCharacteristic = service.getCharacteristic(UUID_PHONE_WRITE)
                mWriteCharacteristic = service.getCharacteristic(UUID_PHONE_READ)
            }

            //使能Notify
            if(mNotifyCharacteristic != null){
                setCharacteristicNotification(mNotifyCharacteristic!!,true)
            }

            if (service == null) {
                LogUtils.e("CentralBluetoothGattCallback","service is null")
                mCentralBluetoothCallBack!!.onGattConnectStatus(false,-100)
            }
        }else{
            mCentralBluetoothCallBack!!.onGattConnectStatus(false,status)
        }

    }


    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        super.onCharacteristicRead(gatt, characteristic, status)
        LogUtils.e("CentralBluetoothGattCallback","onCharacteristicRead:"+String(characteristic!!.value))
        mCentralBluetoothCallBack!!.onDeviceResult(String(characteristic!!.value),characteristic.value)
    }

    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        super.onCharacteristicWrite(gatt, characteristic, status)
        if (status == 0) {
            mBluetoothGatt!!.readCharacteristic(mNotifyCharacteristic)
        }
        LogUtils.e("CentralBluetoothGattCallback","onCharacteristicWrite:"+String(characteristic!!.value))
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
        super.onCharacteristicChanged(gatt, characteristic)
        LogUtils.e("CentralBluetoothGattCallback","onCharacteristicChanged:"+String(characteristic!!.value))
    }







    fun setCharacteristicNotification(characteristic: BluetoothGattCharacteristic,
                                      enabled: Boolean) {

        mBluetoothGatt!!.setCharacteristicNotification(characteristic, enabled)

        // This is specific to BLE SPP Notify.
        var descriptors = characteristic.descriptors

        for (descriptor in descriptors){
            descriptor.value= BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            mBluetoothGatt!!.writeDescriptor(descriptor)
        }

    }


    fun writeData(data :ByteArray){
        if (mWriteCharacteristic != null && data != null) {
            mWriteCharacteristic!!.value = data
            //mBluetoothLeService.writeC
            mBluetoothGatt!!.writeCharacteristic(mWriteCharacteristic)
        }
    }

}
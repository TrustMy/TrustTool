package com.trust.trustbluetooth.ble.peripheral

import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.content.Context
import android.media.audiofx.BassBoost
import android.os.Build
import android.os.ParcelUuid
import android.support.annotation.RequiresApi
import com.trust.trustbluetooth.ble.peripheral.PeripheralConfig.*
import com.trust.trustbluetooth.ble.peripheral.callback.PeripheralCallBack
import com.trust.trustbluetooth.ble.peripheral.callback.TrustGattServerCallBack
import java.util.*

/**
 * Created by Trust on 2018/3/14.
 * 设备    模拟蓝牙设备
 */
class PeripheralFactory {

    val TAG = "PeripheralFactory"
    private var mBluetoothManager: BluetoothManager? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothLeAdvertiser: BluetoothLeAdvertiser? = null
    private var mBluetoothGattServer: BluetoothGattServer? = null
    private var mTrustGattServerCallBack: TrustGattServerCallBack? = null
    private var mPeripheralCallBack: PeripheralCallBack? = null


    @SuppressLint("NewApi")
    fun getPeripheral(context :Context,bluetoothManager: BluetoothManager ,peripheralCallBack: PeripheralCallBack){
        mPeripheralCallBack =  peripheralCallBack
        mTrustGattServerCallBack = TrustGattServerCallBack(mPeripheralCallBack!!)
        mBluetoothManager =  bluetoothManager
        mBluetoothAdapter = mBluetoothManager!!.adapter
        mBluetoothLeAdvertiser = mBluetoothAdapter!!.bluetoothLeAdvertiser

        mBluetoothGattServer = mBluetoothManager!!.openGattServer(context,mTrustGattServerCallBack)

        mTrustGattServerCallBack!!.init(mBluetoothGattServer)

        var characteristic1 = BluetoothGattCharacteristic(UUID_DEVICE_WRITE,BluetoothGattCharacteristic.PROPERTY_WRITE,
                BluetoothGattCharacteristic.PERMISSION_WRITE)

        var characteristic12 = BluetoothGattCharacteristic(UUID_DEVICE_READ,BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ)

        var service1 = BluetoothGattService(UUID_DEVICE_SERVER,BluetoothGattService.SERVICE_TYPE_PRIMARY)

        service1.addCharacteristic(characteristic1)
        service1.addCharacteristic(characteristic12)
        var addService = mBluetoothGattServer!!.addService(service1)
        mPeripheralCallBack!!.onAddServiceStatus(addService)
    }


    @SuppressLint("NewApi")
    fun startAdvertising(){
        val broadcastData = byteArrayOf(0x34, 0x56)
        mBluetoothLeAdvertiser!!.startAdvertising(createAdvSettings(true,0)
        ,createAdvertiseData(broadcastData),mAdvertiseCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun stopAdvertising(){
        mBluetoothLeAdvertiser!!.stopAdvertising(mAdvertiseCallback)
    }


    @SuppressLint("NewApi")
    fun createAdvSettings(connectable: Boolean, timeoutMillis: Int): AdvertiseSettings {
        val mSettingsbuilder = AdvertiseSettings.Builder()
        mSettingsbuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
        mSettingsbuilder.setConnectable(connectable)
        mSettingsbuilder.setTimeout(timeoutMillis)
        return mSettingsbuilder.build()
    }

    @SuppressLint("NewApi")
    fun createAdvertiseData(data: ByteArray): AdvertiseData {
        val mDataBuilder = AdvertiseData.Builder()
        mDataBuilder.addManufacturerData(0x01AC, data)
        mDataBuilder.addServiceUuid(ParcelUuid.fromString("0000aaa0-0000-1000-8000-00805f9b34fb"))
        return mDataBuilder.build()
    }


    private var mAdvertiseCallback = object : AdvertiseCallback(){
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            super.onStartSuccess(settingsInEffect)
            LogUtils.e(TAG, "开启广播成功")
            mPeripheralCallBack!!.onStartPeripheralStatus(true,-1)
        }

        override fun onStartFailure(errorCode: Int) {
            super.onStartFailure(errorCode)
            LogUtils.e(TAG, "开启广播失败 errorCode:$errorCode")
            mPeripheralCallBack!!.onStartPeripheralStatus(false,errorCode)
        }
    }


    /**
     * 给远程设备 发送数据
     */
    fun sendData(msg:String):Boolean{
        return  mTrustGattServerCallBack!!.sendData(msg)
    }

    /**
     * 设置  设备端 uuid
     */
    fun setServerUUID(uuidString:String){
        if (uuidString == null) {
            return
        }
        UUID_DEVICE_SERVER = UUID.fromString(uuidString)
    }

    fun setServerUUID(uuid:UUID){
        if (uuid == null) {
            return
        }
        UUID_DEVICE_SERVER = uuid
    }

    fun setReadUUID(uuidString:String){
        if (uuidString == null) {
            return
        }
        UUID_DEVICE_READ = UUID.fromString(uuidString)
    }

    fun setReadUUID(uuid:UUID){
        if (uuid == null) {
            return
        }
        UUID_DEVICE_READ = uuid
    }


    fun setWriteUUID(uuidString:String){
        if (uuidString == null) {
            return
        }
        UUID_DEVICE_WRITE = UUID.fromString(uuidString)
    }

    fun setWriteUUID(uuid:UUID){
        if (uuid == null) {
            return
        }
        UUID_DEVICE_WRITE = uuid
    }
}
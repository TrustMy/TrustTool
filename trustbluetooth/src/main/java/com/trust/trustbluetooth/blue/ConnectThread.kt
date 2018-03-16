package com.trust.trustbluetooth.blue

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.BLUE_UUID
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.COM_UUID

import java.io.IOException

/**
 * Created by Trust on 2018/2/27.
 * 主动连接远程蓝牙的线程，当用户点击列表中某个蓝牙设备时，启动该线程
 */
class ConnectThread (d :BluetoothDevice,isChat : Boolean,connectCallBack : onConnectThread) :Thread(){
    private var NAME = "ConnectThread"


    private var device : BluetoothDevice? = null
    private var mSocket : BluetoothSocket? = null
    private var isChat = false
    private var mConnectCallBack : onConnectThread? =null
    interface onConnectThread {fun connectThread( isConnect : Boolean)}
    init {
        this.isChat = isChat
        device = d
        mConnectCallBack = connectCallBack
    }

    @SuppressLint("MissingPermission")
    override fun run() {
        Thread(Runnable {   try {
            mSocket = if (isChat) {
                device!!.createRfcommSocketToServiceRecord(BLUE_UUID)
            }else{
                device!!.createRfcommSocketToServiceRecord(COM_UUID)
            }
            mSocket!!.connect()
        }catch (e :IOException){
            e.printStackTrace()
            //链接失败
            mSocket = null
            mConnectCallBack!!.connectThread(false)
        }

            if(mSocket != null){
                Log.d(NAME,"device.getName():"+device!!.name)
                mConnectCallBack!!.connectThread(true)
            } }).start()

    }

    fun getSocket():BluetoothSocket{
        return mSocket!!
    }


    fun disconnction(){
        mSocket!!.close()
    }
}
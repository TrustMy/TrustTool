package com.trust.trustbluetooth.blue

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.BLUE_SOCKET_NAME
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.BLUE_UUID
import java.io.IOException

/**
 * Created by Trust on 2018/2/27.
 * 等待远程蓝牙链接
 */
class AcceptThread(adapter : BluetoothAdapter ,acceptCallBack: onBluetoothAcceptCallBack) :Thread() {
    private val NAME = "TrustBluetooth"
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothServerSocket: BluetoothServerSocket? = null
    private var mBluetoothSocket : BluetoothSocket? = null
    private var mAcceptCallBack : onBluetoothAcceptCallBack? = null
    interface onBluetoothAcceptCallBack{fun acceptCallBack(bluetoothName: String?)}


    init {
        mBluetoothAdapter = adapter
        mAcceptCallBack = acceptCallBack
    }

    @SuppressLint("MissingPermission")
    override fun run() {
        try {
            mBluetoothServerSocket = mBluetoothAdapter!!.listenUsingRfcommWithServiceRecord(BLUE_SOCKET_NAME,BLUE_UUID)
            Log.d(NAME,"开始等待蓝牙连接...")
            mBluetoothSocket = mBluetoothServerSocket!!.accept()
            Log.d(NAME,"远程蓝牙连接成功!!")

        }catch (e : IOException){
            e.printStackTrace()
            mBluetoothSocket = null
        }

        if (mBluetoothSocket != null) {
            mAcceptCallBack!!.acceptCallBack(mBluetoothSocket!!.remoteDevice.name)
        }else{
            mAcceptCallBack!!.acceptCallBack(null)
        }
    }

    fun getBlueSockt() : BluetoothSocket{
        return  mBluetoothSocket!!
    }

    fun disConnection(){
        mBluetoothServerSocket!!.close()
        mBluetoothSocket!!.close()
    }

}
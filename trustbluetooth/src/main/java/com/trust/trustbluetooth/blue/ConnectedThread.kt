package com.trust.trustbluetooth.blue

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.util.Log

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by Trust on 2018/2/27.
 */
class ConnectedThread(s : BluetoothSocket , callBack : onConnectedThread) : Thread() {
    private var NAME = "ConnectedThread"
    private var mBluetoothSocket:BluetoothSocket? = null
    private var inputStream : InputStream? = null
    private var outputStream : OutputStream? = null
    private var isStop : Boolean = false
    private var mCallBack : onConnectedThread? = null
    interface onConnectedThread{fun connectedThreadCallBack(bundle:Bundle)}

    init {
        mBluetoothSocket = s
        mCallBack = callBack
    }


    override fun run() {
        var buf : ByteArray
        var size :Int
        var msg : String? = null
        while (!isStop){
            size = 0
            buf = ByteArray(1024)
            try {
                inputStream = mBluetoothSocket!!.inputStream
                Log.d(NAME,"等待数据")
                size = inputStream!!.read(buf)
                msg = String(buf,0,size)
                Log.d(NAME,"读取了一次数据 :"+msg)
            }catch (e :IOException){
                e.printStackTrace()
                isStop = true
            }

            if(size>0){
                //把读取到的数据发送出去
                sendMessage(msg!!)
            }
        }

    }
    fun sendMessage(msg:String){
        var bundle :Bundle = Bundle()
        bundle.putString("str",msg)
        mCallBack!!.connectedThreadCallBack(bundle)
        Log.d(NAME,"sendMessage："+msg)
    }


    fun write(buf :ByteArray){
        try {
            outputStream = mBluetoothSocket!!.outputStream
            outputStream!!.write(buf)
        }catch (e:IOException){
            e.printStackTrace()
        }
        Log.d(NAME,buf.size.toString() + "---")
    }


    fun disConnection(){
        mBluetoothSocket!!.close()
    }
}
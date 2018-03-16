package com.trust.trustbluetooth.blue

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.BLUE_SOCKET_NAME
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.BLUE_UUID
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.COM_UUID
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.DEVICE_BLUETOOH
import com.trust.trustbluetooth.blue.BluetoothConfig.Companion.PHONE_BLUETOOH


import java.io.IOException
import java.util.*

/**
 * Created by Trust on 2018/3/1.
 */
 class  BluetoothFactory (context:Context ,onBluetoothFactoryCallBack : OnBluetoothFactoryCallBack){
    private var NAME = "BluetoothFactory"
    private var mBluetoothAdapter : BluetoothAdapter? = null
    private var mAcceptThread : AcceptThread? = null
    private var mBlueSockt : BluetoothSocket? = null
    private var mConnectThread : ConnectThread? = null
    private var mConnectedThread : ConnectedThread? = null
    private var mContext : Context? = null
    private var mIsChart :Boolean =true //默认手机蓝牙
    // 广播接收器，主要是接收蓝牙状态改变时发出的广播
    private var mReceiver: BroadcastReceiver? = null
    private var mOnBluetoothFactoryCallBack : OnBluetoothFactoryCallBack? = null

    interface OnBluetoothFactoryCallBack{
        //成功搜索到蓝牙
        fun onFoundSuccessBlueDevice(bluetoothDevice: BluetoothDevice)
        //开始搜索蓝牙设备
        fun onStartFoundBlueDevice()
        //搜索蓝牙完成
        fun onFinishedFoundBlueDevice()
        //是否支持蓝牙
        fun onStandByBlue(isStandBy:Boolean)
        //连接蓝牙是否成功
        fun onConnectStatus(isConnect:Boolean)
        //蓝牙已经断开连接
        fun onDissConnect()
        //接收配对蓝牙设备发送过来得数据
        fun onReceiveData(data:String)

    }

    //初始化
    init {
        mOnBluetoothFactoryCallBack = onBluetoothFactoryCallBack
        mContext = context.applicationContext
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(mBluetoothAdapter == null){
            mOnBluetoothFactoryCallBack!!.onStandByBlue(false)
        }else{
            initConfig()
        }
    }

    //退出得时候清理广播
    fun unregisterReceiver(){
        mContext!!.unregisterReceiver(mReceiver)
        if (mBlueSockt != null) {
            try {
                mBlueSockt!!.close()
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
        if (mConnectThread != null) {
            mConnectThread!!.interrupt()
        }
        if (mConnectedThread != null) {
            mConnectedThread!!.interrupt()
        }
        if (mAcceptThread != null) {
            mAcceptThread!!.interrupt()
        }
    }

    private var connectedThread = object : ConnectedThread.onConnectedThread {
        override fun connectedThreadCallBack(bundle: Bundle) {
            var chatStr = bundle.getString("str")
            mOnBluetoothFactoryCallBack!!.onReceiveData(chatStr)
        }

    }

    private var connectThread = object : ConnectThread.onConnectThread {
        override fun connectThread(isConnect : Boolean) {
            Log.d(NAME,"链接状态:"+isConnect)
            mOnBluetoothFactoryCallBack!!.onConnectStatus(isConnect)
            if (isConnect) {
                mBlueSockt = mConnectThread!!.getSocket()
                mConnectedThread = ConnectedThread(mBlueSockt!!, connectedThread)
                mConnectedThread!!.start()
            }
        }
    }

    //发送数据
     fun sendData(msg:String){
         if (mBlueSockt != null) {
             mConnectedThread!!.write(msg.toByteArray())
         }else{
             Toast.makeText(mContext,"请先连接蓝牙设备!",Toast.LENGTH_LONG).show()
         }
   }

    //连接指定得蓝牙设备
    @SuppressLint("MissingPermission")
    fun connect(device: BluetoothDevice){
        mBluetoothAdapter!!.cancelDiscovery()
        mConnectThread = ConnectThread(device, mIsChart, connectThread)
        mConnectThread!!.start()
    }

    fun initConfig(){
        mOnBluetoothFactoryCallBack!!.onStandByBlue(true)
        mAcceptThread = AcceptThread(mBluetoothAdapter!!, object : AcceptThread.onBluetoothAcceptCallBack {
            override fun acceptCallBack(bluetoothName: String?) {
                if (bluetoothName != null) {
                    mBlueSockt = mAcceptThread!!.getBlueSockt()
                    mConnectedThread = ConnectedThread(mBlueSockt!!, connectedThread)
                    mConnectedThread!!.start()
                    mOnBluetoothFactoryCallBack!!.onConnectStatus(true)
                } else {
                    mOnBluetoothFactoryCallBack!!.onConnectStatus(false)
                }
            }
        })
        mAcceptThread!!.start()


        mReceiver = object :BroadcastReceiver(){
            @SuppressLint("MissingPermission")
            override fun onReceive(context: Context?, intent: Intent?) {
                when( intent!!.action){
                //找到设备
                    BluetoothDevice.ACTION_FOUND -> {
                        var device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        if (device != null) {
                            if (device.name != null) {
                                mOnBluetoothFactoryCallBack!!.onFoundSuccessBlueDevice(device)
                            }
                        }
                        Log.d(NAME,"found device :" +device.name)
                    }
                //搜索中
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                        Log.d(NAME,"start found device ")
                        mOnBluetoothFactoryCallBack!!.onStartFoundBlueDevice()
                    }
                //搜索结束
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                        Log.d(NAME,"finished found device ")
                        mOnBluetoothFactoryCallBack!!.onFinishedFoundBlueDevice()
                    }
                    BluetoothDevice.ACTION_ACL_DISCONNECTED ->{
                        mBlueSockt = null
                        mOnBluetoothFactoryCallBack!!.onDissConnect()
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



    //开始搜索蓝牙
    @SuppressLint("MissingPermission")
    fun startFind(){
        //如果当前在搜索，就先取消搜索
        if (mBluetoothAdapter!!.isDiscovering) {
            mBluetoothAdapter!!.cancelDiscovery()
        }
        //开启搜索
        mBluetoothAdapter!!.startDiscovery()

        // 将已配对的蓝牙设备显示到第一个ListView中
        val deviceSets:Set<BluetoothDevice> = mBluetoothAdapter!!.bondedDevices
    }


    /**
     * 停止搜索
     */
    fun stopFind(){
        mBluetoothAdapter!!.cancelDiscovery()
    }

    fun disconnection(){
        if (mConnectThread != null) {
            mConnectThread!!.disconnction()
            mConnectThread!!.interrupt()
        }
        if (mConnectedThread != null) {
            mConnectedThread!!.disConnection()
            mConnectedThread!!.interrupt()
        }
        if (mAcceptThread != null) {
            mAcceptThread!!.disConnection()
            mAcceptThread!!.interrupt()
        }

        initConfig()
    }


    //开启蓝牙可见性
    fun showBlue(context: Context){
        val intent = Intent(
                BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        context!!.startActivity(intent)
    }

    //设置与手机端连接得uuid
    fun setPhoneBlueUUID(uuid:String){
        BLUE_UUID = UUID.fromString(uuid)
    }

    fun setPhoneBlueUUID(uuid: UUID){
        BLUE_UUID = uuid
    }

    //设置与硬件设备连接得uuid
    fun setDeviceBlueUUID(uuid:String){
        COM_UUID = UUID.fromString(uuid)
    }

    fun setDeviceBlueUUID(uuid:UUID){
        COM_UUID = uuid
    }

    //设置蓝牙连接得名称
    fun setBlueSocketName(blueSocketName:String){
        BLUE_SOCKET_NAME = blueSocketName
    }

    //设置连接方式 true 手机蓝牙对手机蓝牙 false  手机蓝牙对硬件设备
    fun setBlueDeviceType(blueType:Int){
        when (blueType) {
            PHONE_BLUETOOH -> {
                mIsChart = true
            }
            DEVICE_BLUETOOH -> {
                mIsChart = false
            }
        }
    }
}
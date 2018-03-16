package com.trust.trustbluetooth.blue

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.trust.trustbluetooth.R
import com.trust.trustbluetooth.ble.central.MyAdapter
import kotlinx.android.synthetic.main.activity_tradition.*
import java.util.ArrayList

class TraditionActivity : AppCompatActivity() ,View.OnClickListener , BluetoothFactory.OnBluetoothFactoryCallBack {

    private var discoveryPro: ProgressBar? = null
    private var foundList: ListView? = null
    private var foundDevices: MutableList<BluetoothDevice>? = null
    private var dialog: Dialog? = null

    private var mContext: Context? = null
    private var mBluetoothFactory : BluetoothFactory ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tradition)
        tradotion_blue_connection_btn.setOnClickListener(this)
        tradotion_blue_show_blue_btn.setOnClickListener(this)
        tradotion_blue_send_data_btn.setOnClickListener(this)
        tradotion_blue_disconnection_btn.setOnClickListener(this)

        init()
    }

    fun init(){
        mContext = this
        mBluetoothFactory = BluetoothFactory(mContext!!,this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            //开始搜索
             R.id.tradotion_blue_connection_btn-> {
                 startFinde()
            }
            //使蓝牙可见
            R.id.tradotion_blue_show_blue_btn->{

            }
            //发送数据
            R.id.tradotion_blue_send_data_btn->{
                var toString = tradotion_blue_send_data_ed.text.trim().toString()
                mBluetoothFactory!!.sendData(toString)
            }
            //断开当前蓝牙
            R.id.tradotion_blue_disconnection_btn->{
                mBluetoothFactory!!.disconnection()
            }

        }
    }



    override fun onFoundSuccessBlueDevice(bluetoothDevice: BluetoothDevice) {
        runOnUiThread({
            foundDevices!!.add(bluetoothDevice)
            foundList!!.adapter = MyAdapter(mContext, foundDevices)
        })
    }

    override fun onStartFoundBlueDevice() {
        runOnUiThread({
            discoveryPro!!.visibility = View.VISIBLE
        })
    }

    override fun onFinishedFoundBlueDevice() {
        runOnUiThread({
            discoveryPro!!.visibility = View.GONE
        })
    }

    override fun onStandByBlue(isStandBy: Boolean) {
        runOnUiThread({
            if (isStandBy) {
                tradotion_blue_status_tv.text = "支持蓝牙"
            }else{
                tradotion_blue_status_tv.text = "不支持蓝牙"
            }
        })

    }

    override fun onConnectStatus(isConnect: Boolean) {
        runOnUiThread({
            if (isConnect) {
                tradotion_blue_status_tv.text = "与设备连接成功"
            }else{
                tradotion_blue_status_tv.text = "与设备连接失败"
            }
        })

    }

    override fun onDissConnect() {
        runOnUiThread({
            tradotion_blue_status_tv.text = "与设备断开连接"

        })
    }

    override fun onReceiveData(data: String) {
        runOnUiThread({
            var msg:String = tradotion_blue_result_tv.text.trim().toString()
            msg = msg+"\n"+data
            tradotion_blue_result_tv.text = msg
        })

    }




    fun startFinde(){
        mBluetoothFactory!!.startFind()

        var inflater = layoutInflater
        var view :View= inflater.inflate(R.layout.dialog, null)
        discoveryPro = view.findViewById(R.id.discoveryPro)
        var bondedList = view.findViewById(R.id.foundList)as ListView

        foundList = view.findViewById(R.id.foundList)as ListView

        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        // 将已配对的蓝牙设备显示到第一个ListView中
        val deviceSet = bluetoothAdapter.bondedDevices
        val bondedDevices = ArrayList<BluetoothDevice>()
        if(deviceSet.size > 0){
            val it = deviceSet.iterator()
            while (it.hasNext()) {
                val device = it.next() as BluetoothDevice
                bondedDevices.add(device)
            }
        }
        bondedList.adapter = MyAdapter(mContext!!,bondedDevices)

        foundDevices = ArrayList()
        foundList!!.adapter = MyAdapter(mContext, foundDevices)

        bondedList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var device = bondedDevices[position]
            //连接
            connectedDevice(device)
        }
        foundList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var device = (foundDevices as ArrayList<BluetoothDevice>)[position]
            //连接
            connectedDevice(device)
        }

        var builder = AlertDialog.Builder(mContext!!)
        builder.setMessage("请选择要连接的蓝牙设备").setPositiveButton("取消",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                mBluetoothFactory!!.stopFind()
            }
        })
        builder.setView(view)
        builder.create()
        dialog = builder.show()

    }

    private fun connectedDevice(device: BluetoothDevice) {
        mBluetoothFactory!!.stopFind()
        dialog!!.dismiss()
        Toast.makeText(this, "正在与 " + device.name + " 连接 .... ",
                Toast.LENGTH_LONG).show()

        mBluetoothFactory!!.connect(device)
    }


}

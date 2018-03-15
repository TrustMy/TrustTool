package com.trust.trustbluetooth.blue

import java.util.*

/**
 * Created by Trust on 2018/3/9.
 */
class BluetoothConfig {
    companion object {
         var BLUE_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
         var COM_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
         var BLUE_SOCKET_NAME = "TurstBluetooth"
         var PHONE_BLUETOOH = 0
         var DEVICE_BLUETOOH = 1
    }
}
package com.shy.zlread.utils

import android.content.Context
import com.inuker.bluetooth.library.BluetoothClient


class BluetoothGloble private constructor() {
    companion object {
        val instance: BluetoothGloble by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BluetoothGloble()
        }

    }

    var mClient:BluetoothClient? = null
    fun getBluetoothClient(context: Context):BluetoothClient {

        if (mClient == null){
            mClient = BluetoothClient(context)
        }

        return mClient!!
    }
}
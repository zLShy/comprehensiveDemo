package com.zl.map.Utils

/**
 * Created by zhangli on 2018/7/27.
 */
class Constants {

    companion object {
        val BASE_URL = "http://192.168.0.79:9999/"
        val WRITE_READ_EXTERNAL_CODE = 0x01
        //照相
        val CAMERA_CODE = 0x02
        //电话
        val CALL_PHONE_CODE = 0x03
        //短信
        val SEM_MESSAGE_CODE = 0x04
        //手机信息
        val PHONE_STATE_CODE = 0x05
        //定位
        val PHONE_LOCATION_CODE = 0x06
        //混合权限
        val MIX_AUTHORITY_CODE = 0x07

    }
}
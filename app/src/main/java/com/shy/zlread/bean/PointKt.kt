package com.shy.zlread.bean

/**
 * Created by zhangli on 2019/4/4.
 */
class PointKt(var centerX:Int, var centerY: Int, var position:Int) {


    private val NORMOL_STATUS = 1
    private val PRESS_STATUS = 2
    private val ERROR_STATUS = 3

    private var status = NORMOL_STATUS

    fun setPressStatus() {
        this.status = PRESS_STATUS
    }

    fun setErrorStatus() {
        this.status = ERROR_STATUS
    }

    fun setNormalStatus() {
        this.status = NORMOL_STATUS
    }

    fun isStatusPress():Boolean{
        return status == PRESS_STATUS
    }

    fun isStatusError():Boolean{
        return status == ERROR_STATUS
    }

    fun isStatusNormal():Boolean{
        return status == NORMOL_STATUS
    }
}
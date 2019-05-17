package com.shy.zlread.model

import com.shy.zlread.httpRequest.CallBacks

/**
 * Created by zhangli on 2019/3/20.
 */
interface ILoginDao {
    fun checkUser(count:String,pass:String,mCallBack:CallBacks)
}
package com.shy.zlread.model

import android.util.Log
import com.shy.zlread.httpRequest.ApiMethods
import com.shy.zlread.httpRequest.CallBacks
import com.shy.zlread.httpRequest.ProgressObserver

/**
 * Created by zhangli on 2019/3/20.
 */
class LoginImp:ILoginDao {
    override fun checkUser(count: String, pass: String, mCallBack: CallBacks) {
        ApiMethods().getLoginInfo(ProgressObserver(object : CallBacks{
            override fun onSuccess(any: Any) {
                mCallBack.onSuccess(any)
                Log.e("TGA","Success")
            }

            override fun onFailure(any: Any) {
                mCallBack.onFailure(any)
                Log.e("TGA","Failure")

            }

        }))
    }

}
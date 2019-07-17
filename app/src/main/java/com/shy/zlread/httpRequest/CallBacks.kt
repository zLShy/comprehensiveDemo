package com.shy.zlread.httpRequest

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by zhangli on 2019/3/20.
 */
interface CallBacks {
    fun onSuccess(any: Response<ResponseBody>)
    fun onFailure(any: Any)
}
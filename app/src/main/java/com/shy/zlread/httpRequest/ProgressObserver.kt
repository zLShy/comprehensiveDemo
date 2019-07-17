package com.shy.zlread.httpRequest

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by zhangli on 2019/3/20.
 */
class ProgressObserver(callBacks: CallBacks):Observer<Response<ResponseBody>> {
    private var mCallbacks:CallBacks? = null
    init {
        this.mCallbacks = callBacks
    }
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable?) {

    }

    override fun onNext(t: Response<ResponseBody>?) {
        mCallbacks!!.onSuccess(t!!)
    }

    override fun onError(e: Throwable?) {
        mCallbacks!!.onFailure(e!!)
    }
}
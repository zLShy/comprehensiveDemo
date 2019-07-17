package com.shy.zlread.httpRequest

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by zhangli on 2019/3/20.
 */
class ApiMethods {
    fun ApiSubscribe(mObservable: Observable<Response<ResponseBody>>, mObserver: Observer<Response<ResponseBody>>) {
        mObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver)

    }


    fun getLoginInfo(mObserver: ProgressObserver) {
        ApiSubscribe(RetrifitUtils().getApiService().getMovies(0, 10), mObserver)
    }

    fun getRongToken(mObserver: ProgressObserver, appkey: String, timestamp: String, Nonce: String, Signature: String, content_type: String, userId: String, userName: String, portraitUri: String) {
        ApiSubscribe(RetrifitUtils().getApiService().getRongToken(appkey, timestamp, Nonce, Signature,content_type, userId, userName, portraitUri), mObserver)
    }

    fun getTest(mObserver: ProgressObserver) {
        ApiSubscribe(RetrifitUtils().getApiService().getLoginInfo(), mObserver)
    }

}
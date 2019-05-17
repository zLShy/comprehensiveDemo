package com.shy.zlread.httpRequest

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhangli on 2019/3/20.
 */
class ApiMethods {
    fun ApiSubscribe(mObservable:Observable<Any>,mObserver:Observer<Any>) {
        mObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver)

    }


    fun getLoginInfo(mObserver: ProgressObserver) {
        ApiSubscribe(RetrifitUtils().getApiService().getMovies(0,10),mObserver)
    }

}
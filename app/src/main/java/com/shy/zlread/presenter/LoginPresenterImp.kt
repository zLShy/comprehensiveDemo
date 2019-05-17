package com.shy.zlread.presenter

import android.util.Log
import com.shy.zlread.httpRequest.CallBacks
import com.shy.zlread.model.ILoginDao
import com.shy.zlread.model.LoginImp
import com.shy.zlread.view.ILoginView

/**
 * Created by zhangli on 2019/3/20.
 */
class LoginPresenterImp(iLoginView:ILoginView) : ILoginPresenter {
    private var mILoginDao:ILoginDao? = null
    private var mILoginView:ILoginView? = null
    init {
        mILoginDao = LoginImp()
        mILoginView = iLoginView
    }
    override fun doLogin() {

        mILoginView!!.showLoading()
        mILoginDao!!.checkUser(mILoginView!!.getCount(),mILoginView!!.getPass(),object : CallBacks{

            override fun onSuccess(any: Any) {
                mILoginView!!.hideLoading()
                mILoginView!!.loginSuccess()
            }

            override fun onFailure(any: Any) {
                mILoginView!!.hideLoading()
                mILoginView!!.showToast("账号或密码错误～～")
            }

        })
    }
}
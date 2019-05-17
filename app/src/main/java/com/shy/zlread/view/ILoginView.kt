package com.shy.zlread.view

/**
 * Created by zhangli on 2019/3/20.
 */
interface ILoginView {
    fun getCount():String
    fun getPass():String
    fun clearText()
    fun showLoading()
    fun hideLoading()
    fun loginSuccess()
    fun showToast(failReason: String)
}
package com.shy.zlread.bean

/**
 * Created by zhangli on 2019/7/11.
 */
class RongToken(code: Int, userId: String, token: String) {
    var code: Int
    var userId: String
    var token: String

    init {
        this.code = code
        this.userId = userId
        this.token = token
    }

    override fun toString(): String {
        return "RongToken(code=$code, userId='$userId', token='$token')"
    }


}
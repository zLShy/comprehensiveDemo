package com.shy.zlread

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.shy.baseadapterlibrary.ExceptionCarshHandler
import com.shy.zlread.presenter.ILoginPresenter
import com.shy.zlread.presenter.LoginPresenterImp
import com.shy.zlread.utils.statusBarUtil
import com.shy.zlread.view.ILoginView
import com.zl.map.Utils.BaseActicity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActicity(), ILoginView {
    private var mILoginPresenterImp: ILoginPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        ExceptionCarshHandler.getInstance().init(this)
//        var x = 2 / 0
        mILoginPresenterImp = LoginPresenterImp(this)
        login_btn.setOnClickListener { v: View? ->
            mILoginPresenterImp!!.doLogin()
        }

//        statusBarUtil.setStatusBar(this,Color.CYAN)
        statusBarUtil.setActivityFullScreen(this)

        Log.e("TAG", "model -> " + android.os.Build.MODEL)
        Log.e("TAG", "hardware -> " + android.os.Build.HARDWARE)
        Log.e("TAG", "board -> " + android.os.Build.BOARD)
        Log.e("TAG", "device ->" + android.os.Build.DEVICE)
        Log.e("TAG", "display ->" + android.os.Build.DISPLAY)
        val sum: (Int, Int) -> Int = { x, y -> x + y }
        Log.e("TAG", "lambed->${sum.invoke(1, 2)}")
//        Glide.with(this).load("https://sys.cegzm.com/adver/102.gif").asGif().into(image_iv)
    }

    override fun getCount(): String {

        return login_count_et.text.toString().trim()
    }

    override fun getPass(): String {
        return login_pass_et.text.toString().trim()
    }

    override fun clearText() {
        login_count_et.setText("")
        login_pass_et.setText("")
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {

        progress.visibility = View.INVISIBLE
    }

    override fun loginSuccess() {

        var mIntent = Intent(this@LoginActivity, LoadingActivity::class.java)
        startActivity(mIntent)
        finish()
    }

    override fun showToast(reason: String) {
//        Toast.makeText(this@LoginActivity, reason, Toast.LENGTH_SHORT).show()
        var mIntent = Intent(this@LoginActivity, LoadingActivity::class.java)
        startActivity(mIntent)
        finish()
    }
}

package com.zl.map.Utils

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.gson.Gson
import com.shy.framelibrary.skin.SkinActivity
import com.shy.zlread.MyApplication
import com.shy.zlread.bean.RongToken
import com.shy.zlread.httpRequest.ApiMethods
import com.shy.zlread.httpRequest.CallBacks
import com.shy.zlread.httpRequest.ProgressObserver
import com.shy.zlread.httpRequest.RetrifitUtils
import com.shy.zlread.utils.RongCloudeUtils
import io.rong.imkit.utilities.RongUtils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

/**
 * Created by zhangli on 2018/7/27.
 */
open abstract class BaseActicity : SkinActivity() {

    var TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        initViews()
        initDate()
    }

    /**
     * 判断是否有权限
     *
     * @param permissions
     * @return
     */
    fun hasPermission(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 申请权限方法
     *
     * @param code        返回码
     * @param permissions 具体权限
     */
    fun requestPermission(code: Int, vararg permissions: String) {
        ActivityCompat.requestPermissions(this, permissions, code)
    }

    /**
     * 申请权限方法
     *
     * @param code        返回码
     * @param permissions 具体权限
     */
    fun requestMorePermissions(code: Int, permissions: Array<String>) {
        ActivityCompat.requestPermissions(this, permissions, code)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.WRITE_READ_EXTERNAL_CODE -> doSDcardPermission()
            Constants.CAMERA_CODE -> takePhoto()
            Constants.CALL_PHONE_CODE -> callPhone()
            Constants.SEM_MESSAGE_CODE -> sendSEM()
            Constants.PHONE_LOCATION_CODE -> getLocation()
            Constants.PHONE_STATE_CODE -> readPhone()
            Constants.MIX_AUTHORITY_CODE -> doMixFunction()
            else -> {
            }
        }
    }

    open fun readPhone() {

    }

    open fun getLocation() {

    }

    open fun sendSEM() {

    }

    open fun callPhone() {

    }

    open fun takePhoto() {

    }

    open fun doSDcardPermission() {

    }

    open fun doMixFunction() {

    }

    override fun onResume() {
        super.onResume()
//        hideBottomUIMenu()
//        steepStatusBar()
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }

    }

    /**
     * [沉浸状态栏]
     */
    private fun steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 透明导航栏
            window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)


        }
    }

    fun fixBug() {
        var mPath = File(Environment.getExternalStorageDirectory(), "fix.aptach")

        if (mPath.exists()) {
            try {
                MyApplication.mPatchManager.addPatch(mPath.absolutePath)
                Toast.makeText(this, "fix success", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(this, "fix fails", Toast.LENGTH_SHORT).show()
            }

        } else {
        }
    }

    //初始化数据
    open abstract fun initDate()

    //初始化界面
    open abstract fun setContentView()

    //初始化view
    open abstract fun initViews()

}
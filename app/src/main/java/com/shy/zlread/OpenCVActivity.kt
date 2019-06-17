package com.shy.zlread

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import org.opencv.android.InstallCallbackInterface
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat
import java.io.File

class OpenCVActivity : AppCompatActivity() {
//    companion object {
//        val mBasePath = Environment.getExternalStorageDirectory().toString() + File.separator
//        val mImgName = "debug01_1526659262410.png"
//        var mSrcMat: Mat? = null
//        var mTargetMat: Mat? = null
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_cv)

//        mSrcMat = Mat()


//        var callback = object : LoaderCallbackInterface {
//            override fun onManagerConnected(status: Int) {
//
//                when (status) {
//                    LoaderCallbackInterface.SUCCESS -> {
//
//                        Log.e("TAG", "success-->init opencv")
//                    }
//                    else -> {
//                        Log.e("TAG", "fail-->init opencv")
//
//                    }
//                }
//            }
//
//            override fun onPackageInstall(operation: Int, callback: InstallCallbackInterface?) {
//            }
//
//        }
//
//        if (OpenCVLoader.initDebug()) {
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, applicationContext, callback)
//        } else {
//
//            callback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
//        }

    }


//    fun startActivity() {
//        var intent = Intent()
////        intent.setClass(this,)
//    }
}

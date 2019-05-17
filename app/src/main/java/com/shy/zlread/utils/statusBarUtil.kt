package com.shy.zlread.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.ViewGroup
import android.view.WindowManager

/**
 * Created by zhangli on 2019/4/15.
 */
class statusBarUtil {

    companion object {
        /**
         * 设置statusBar状态拦
         */
        public fun setStatusBar(activity: Activity,color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor = color
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                var mViewGroup = activity.window.decorView as ViewGroup
                var mView = View(activity)
                var lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusbarHeight(activity))
                mView.layoutParams = lp
                mView.setBackgroundColor(color)
                mViewGroup.addView(mView,0)

                //设置
                var mContentView = activity.findViewById(android.R.id.content) as ViewGroup
                var activityView = mContentView.getChildAt(0)
//                mContentView.setPadding(0, getStatusbarHeight(activity),0,0) //设置padding
                activityView.fitsSystemWindows = true //自适应window
            }
        }

            /**
             * 获取状态栏高度
             */
             fun getStatusbarHeight(activity: Activity): Int {

                var mResources = activity.resources
                var statusBarHeightId = mResources.getIdentifier("status_bar_height","dimen","android")
                return mResources.getDimensionPixelOffset(statusBarHeightId)
            }


        fun setActivityFullScreen(activity: Activity) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                var decorView = activity.window.decorView
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                activity.window.statusBarColor = Color.TRANSPARENT
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }
}
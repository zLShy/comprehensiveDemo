package com.shy.zlread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.shy.zlread.adapter.FruitAdapter
import com.shy.zlread.bean.Fruit
import com.shy.zlread.weight.MyScrolliew
import kotlinx.android.synthetic.main.activity_behavior.*
import kotlinx.android.synthetic.main.activity_md.*
import java.util.*

class MDBehaviorActivity : AppCompatActivity() {

    private var mImageHeight = 0
    private var mTitleHeight = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior)

        title_view.background.alpha = 0
        back_iv.post(object : Runnable {
            override fun run() {
                mImageHeight = back_iv.measuredHeight
            }

        })
        title_view.post(object : Runnable{
            override fun run() {
                mTitleHeight = title_view.measuredHeight
            }
        })
        md_scrollview.setOnScrollListener(object : MyScrolliew.onScrollChangeListener {
            override fun srollListener(l: Int, t: Int, oldl: Int, oldt: Int) {
                if (mImageHeight == 0) return
                var alpha = t / (mImageHeight - mTitleHeight).toFloat()
                if (alpha <= 0) {
                    alpha = 0F
                }
                if (alpha >= 1F) {
                    alpha = 1F
                }
                title_view.background.alpha = (alpha * 255).toInt()
            }

        })
    }
}

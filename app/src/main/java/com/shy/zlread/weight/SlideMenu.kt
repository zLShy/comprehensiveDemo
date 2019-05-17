package com.shy.zlread.weight

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.HorizontalScrollView
import android.widget.RelativeLayout
import com.shy.zlread.R
import java.lang.RuntimeException

/**
 * Created by zhangli on 2019/3/22.
 */
class SlideMenu @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleStr: Int = 0):HorizontalScrollView(context,attributeSet,defStyleStr) {


    private var mGestureDetector:GestureDetector? = null
    private var rigthMenu: Float
    private var mMenuWidth: Int = 0
    private var isOpen = false
    private var isIntercept = false
    private var mShadeView: View? = null

    init {
           var mGestureDetectorListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                if (velocityX < 0) {
                    closeMenu()
                    return true
                }else{
                    openMenu()
                    return true
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }

        }
        var arrays = context.obtainStyledAttributes(attributeSet, R.styleable.KGSlideMenu)
        rigthMenu = arrays.getDimension(R.styleable.KGSlideMenu_rightMagrin,dip2px(50F).toFloat())
        mMenuWidth = getScreenWidth(context) - rigthMenu.toInt()
        mShadeView = View(context)
        mGestureDetector = GestureDetector(context,mGestureDetectorListener)
        arrays.recycle()
    }


    private lateinit var mContentView: View

    private lateinit var menuView: View

    override fun onFinishInflate() {
        super.onFinishInflate()
        var vg = getChildAt(0) as ViewGroup
        if (vg.childCount != 2) {
            throw RuntimeException("布局中只能含有一个")
        }
        //设置宽度菜单
        menuView = vg.getChildAt(0) as View
        var  menuParams = vg.getChildAt(0).layoutParams as ViewGroup.LayoutParams
        menuParams.width = mMenuWidth
        menuView.layoutParams = menuParams

        //设置内容宽度
        mContentView = vg.getChildAt(1) as View
        var contentParams =  vg.getChildAt(1).layoutParams as ViewGroup.LayoutParams
        vg.removeView(mContentView)
        var contentContainer = RelativeLayout(context)
        contentContainer.addView(mContentView)
        contentParams.width = getScreenWidth(context)
        mShadeView!!.setBackgroundColor(Color.parseColor("#55000000"))
        contentContainer.addView(mShadeView)
        mShadeView!!.alpha = 0F
        contentContainer.layoutParams = contentParams
        vg.addView(contentContainer)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        scrollTo(mMenuWidth,0)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (isIntercept) {
            return true
        }
        if (mGestureDetector!!.onTouchEvent(ev)){
            return true
        }
        if (ev!!.action == MotionEvent.ACTION_UP) {
            if (scrollX > mMenuWidth / 2) {
                closeMenu()
            }else{
                openMenu()
            }

            return true
        }
        return super.onTouchEvent(ev)
    }

    /**
     * 打开菜单
     */
    private fun openMenu() {
        isOpen = true
        smoothScrollTo(0,0)
    }

    /**
     * 关闭菜单
     */
    private fun closeMenu() {
        isOpen = false
        smoothScrollTo(mMenuWidth,0)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        //设置主页面缩放
        var scale = 1F * l / mMenuWidth // 1-0
        var mShadeViewAlpha = 1 - scale
        mShadeView!!.alpha = mShadeViewAlpha
//        var rightScale = 0.7F + 0.3 * scale
//        ViewCompat.setPivotX(mContentView,0F)
//        ViewCompat.setPivotY(mContentView, (mContentView.measuredHeight /2).toFloat())
//        ViewCompat.setScaleX(mContentView, rightScale.toFloat())
//        ViewCompat.setScaleY(mContentView, rightScale.toFloat())
//
//        var menuScale = 0.7F + (1- scale) * 0.3
//        ViewCompat.setScaleX(menuView, menuScale.toFloat())
//        ViewCompat.setScaleY(menuView, menuScale.toFloat())

        ViewCompat.setTranslationX(menuView, 0.5f * l)
    }

    fun getScreenWidth(context: Context):Int {

        var wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return wm.defaultDisplay.width
    }

    fun dip2px(defValue:Float): Int {

       var dpm = resources.displayMetrics
        return (dpm.density * defValue + 0.5f).toInt()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        isIntercept = false
        if (isOpen) {
            if (ev!!.x > mMenuWidth && ev!!.y > height * 0.15 && ev!!.y < height * 0.85)
                isIntercept = true
                closeMenu()
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }
}
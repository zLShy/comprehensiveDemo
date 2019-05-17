package com.shy.zlread.weight

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ListViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView

/**
 * Created by zhangli on 2019/3/29.
 */
class CarListView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet? = null, defStyleStr: Int = 0):FrameLayout(context,attributeSet,defStyleStr) {

    private var mViewHelper: ViewDragHelper? = null
    private var mDragListView: View? = null
    private var mMenuHeight = 0
    private var mViewTop = 0
    private var mDownY = 0F
    private var mMenuIsOpen = false
    private var mCallBack = object : ViewDragHelper.Callback(){
        override fun tryCaptureView(child: View?, pointerId: Int): Boolean {

            //子View是否可拖动

            return mDragListView == child
        }

        override fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
            if (top <= 0) {
                mViewTop = 0
            }else if (top > mMenuHeight) {
                mViewTop = mMenuHeight
            }else {
                mViewTop = top
            }
            return mViewTop
        }

        override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
            if (mDragListView == releasedChild) {
                if (mDragListView!!.top > mMenuHeight /2) {
                    mViewHelper!!.settleCapturedViewAt(0,mMenuHeight)
                    mMenuIsOpen = true
                }else {
                    mViewHelper!!.settleCapturedViewAt(0,0)
                    mMenuIsOpen = false
                }

                invalidate()
            }
        }
    }

    init {

        mViewHelper = ViewDragHelper.create(this,mCallBack)
    }


    /**
     * xml 解析完毕调用
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        mDragListView = getChildAt(1)
    }

    /**
     * 子view摆放调用
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mMenuHeight = getChildAt(0).measuredHeight
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mViewHelper!!.processTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        if (mMenuIsOpen) {
            return true
        }
        when(ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mViewHelper!!.processTouchEvent(ev)
                mDownY = ev!!.y
            }
            MotionEvent.ACTION_MOVE -> {
                var moveY = ev!!.y
                if (moveY - mDownY > 0 && !canChildScrollUp()) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * 滚动辅助
     */
    override fun computeScroll() {
        super.computeScroll()
        if (mViewHelper!!.continueSettling(true)){
            invalidate()
        }
    }

    /**
     * 判断是否是第一条目
     */
    fun canChildScrollUp(): Boolean {
        return if (mDragListView is ListView) {
            ListViewCompat.canScrollList(mDragListView as ListView, -1)
        } else ViewCompat.canScrollVertically(mDragListView, -1)
    }
}
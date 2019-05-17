package com.shy.zlread.weight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.shy.zlread.adapter.ListDataAdapter
import com.shy.zlread.adapter.MenuObserver
import kotlin.math.sign

/**
 * Created by zhangli on 2019/5/5.
 */
class ListScreenView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attributeSet, defStyle), View.OnClickListener {

    private lateinit var mContext: Context
    private var mTabView: LinearLayout? = null
    private var mShadowView: View? = null
    private var mMiddleView: FrameLayout? = null
    private var mMenuContainerView: FrameLayout? = null
    private var mColor = 0x88888888
    private var mAdapter: ListDataAdapter? = null
    private var mContentHeight = 0
    private val ANIM_TIME = 350L
    private var mCurrentPosition = -1
    private var mAnimtorExector = false
    private var mObserval = MenuObserval()

    init {
        this.mContext = context
        initLayout()
    }

    private fun initLayout() {

        //1.1 初始化TabView
        orientation = VERTICAL
        mTabView = LinearLayout(mContext)
        mTabView!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(mTabView)
        //1.2 设置内容页和阴影父布局
        mMiddleView = FrameLayout(mContext)
        var mParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        mParams.weight = 1F
        mMiddleView!!.layoutParams = mParams
        addView(mMiddleView)

        //1.2.1 设置内容页和阴影
        mShadowView = View(mContext)
        mShadowView!!.setBackgroundColor(mColor.toInt())
        mShadowView!!.visibility = View.GONE
        mShadowView!!.setOnClickListener(this@ListScreenView)
        mMiddleView!!.addView(mShadowView)

        mMenuContainerView = FrameLayout(mContext)
        mMenuContainerView!!.setBackgroundColor(Color.WHITE)
        mMiddleView!!.addView(mMenuContainerView)
    }

    fun setAdapter(adapter: ListDataAdapter) {
        this.mAdapter = adapter
        if (mAdapter!= null && mObserval != null) {
            this.mAdapter!!.unregisterDataSetObserval()
            this.mAdapter!!.registerDataSetObserval(mObserval)
        }

        var mCount = mAdapter!!.count - 1
        for (i in 0..mCount) {
            var mView = mAdapter!!.getTabView(i, mTabView)
            var params = mView.layoutParams as LinearLayout.LayoutParams
            params.weight = 1F
            mTabView!!.addView(mView)
            setTabOnClick(mView, i)
            var mContentView = mAdapter!!.getMenuView(i, mMenuContainerView)
            mContentView.visibility = View.GONE
            mMenuContainerView!!.addView(mContentView)
        }

    }

    private fun setTabOnClick(tabView: View, position: Int) {

        tabView.setOnClickListener { v: View? ->
            if (mCurrentPosition == -1) {
                openMenu(tabView, position)
            } else {
                if (mCurrentPosition == position) {
                    closeMenu()
                } else {
                    mMenuContainerView!!.getChildAt(mCurrentPosition).visibility = View.GONE
                    mAdapter!!.close(mTabView!!.getChildAt(mCurrentPosition))
                    mCurrentPosition = position
                    mMenuContainerView!!.getChildAt(mCurrentPosition).visibility = View.VISIBLE
                    mAdapter!!.setTabView(mTabView!!.getChildAt(mCurrentPosition), position)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (mContentHeight == 0 && height > 0) {
            mContentHeight = (height * 75 / 100).toInt()
            var mParams = mMenuContainerView!!.layoutParams
            mParams.height = mContentHeight
            mMenuContainerView!!.layoutParams = mParams
            mMenuContainerView!!.translationY = (-mContentHeight).toFloat()
        }

    }

    override fun onClick(v: View) {
        closeMenu()
    }

    /**
     * 打开菜单
     */
    @SuppressLint("ObjectAnimatorBinding")
    fun openMenu(tabView: View, position: Int) {
        if (mAnimtorExector) return
        mShadowView!!.visibility = View.VISIBLE
        mMenuContainerView!!.getChildAt(position).visibility = View.VISIBLE
        var anim = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mContentHeight.toFloat(), 0F)
        anim.duration = ANIM_TIME
        anim.start()

        var alphaAnim = ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f)
        alphaAnim.duration = ANIM_TIME
        alphaAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                mAnimtorExector = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                mAnimtorExector = false
                mCurrentPosition = position

            }
        })
        alphaAnim.start()
        mAdapter!!.setTabView(tabView, position)
    }

    /**
     * 关闭菜单
     */
    @SuppressLint("ObjectAnimatorBinding")
    fun closeMenu() {
        if (mAnimtorExector) return
        var anim = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0F, -mContentHeight.toFloat())
        anim.duration = ANIM_TIME
        anim.start()

        var alphaAnim = ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f)
        alphaAnim.duration = ANIM_TIME
        alphaAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                mAnimtorExector = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                mAdapter!!.close(mTabView!!.getChildAt(mCurrentPosition))
                mAnimtorExector = false
                mShadowView!!.visibility = View.GONE
                mMenuContainerView!!.getChildAt(mCurrentPosition).visibility = View.GONE
                mCurrentPosition = -1
            }
        })
        alphaAnim.start()


    }

    inner class MenuObserval : MenuObserver() {
        override fun closeChange() {
            Log.e("TAG", "close")
            this@ListScreenView.closeMenu()
        }


    }
}
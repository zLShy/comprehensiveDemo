package com.shy.zlread.weight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.shy.zlread.R
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * Created by zhangli on 2019/5/14.
 */
class FlowerLoadingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : RelativeLayout(context, attributeSet, defStyle) {
    private var mLeftView: CircleView? = null
    private var mCenterView: CircleView? = null
    private var mRightView: CircleView? = null
    private var mTranslationDistance = dip2px(25F)
    private var ANIM_TIME = 350L
    private var ANIM_STOP = false

    init {

        View.inflate(context, R.layout.flower_loading_view, this)
        var container = this.getChildAt(0) as RelativeLayout
        mLeftView = container.getChildAt(0) as CircleView
        mCenterView = container.getChildAt(1) as CircleView
        mRightView = container.getChildAt(2) as CircleView

        post(object : Runnable {
            override fun run() {
                ExpandAnim()
            }

        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun ExpandAnim() {

        if (ANIM_STOP) return
        var mLeftAnim = ObjectAnimator.ofFloat(mLeftView, "translationX", 0F, -mTranslationDistance)
        var mRightAnim = ObjectAnimator.ofFloat(mRightView, "translationX", 0F, mTranslationDistance)

        var set = AnimatorSet()
        set.playTogether(mLeftAnim, mRightAnim)
        set.duration = ANIM_TIME
        set.interpolator = AccelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                innerAnim()
            }
        })
        set.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun innerAnim() {
        if (ANIM_STOP) return
        var mLeftAnim = ObjectAnimator.ofFloat(mLeftView, "translationX", -mTranslationDistance, 0F)
        var mRightAnim = ObjectAnimator.ofFloat(mRightView, "translationX", mTranslationDistance, 0F)

        var set = AnimatorSet()
        set.playTogether(mLeftAnim, mRightAnim)
        set.duration = ANIM_TIME
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                ExpandAnim()
            }
        })
        set.interpolator = DecelerateInterpolator()
        set.start()
    }

    fun dip2px(defalutValue: Float): Float {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defalutValue, resources.displayMetrics)
    }

    fun setAnimStop(stop:Boolean) {
        this.ANIM_STOP = stop
    }
}
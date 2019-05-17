package com.shy.zlread.weight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.shy.zlread.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.loadingview_layout.view.*

/**
 * Created by zhangli on 2019/4/28.
 */
class LoadingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attributeSet, defStyle) {

    var ANIMATOR_TIME = 350L
    var mTranslationY = 0F
    private var isStopAnim = false

    init {
        View.inflate(context, R.layout.loadingview_layout, this)
        mTranslationY = dip2px(80F)
        startFialAnimator()
    }

    /**
     * 下降组合动画
     */
    private fun startFialAnimator() {

        if (isStopAnim) return
        var mObjectAnimator = ObjectAnimator.ofFloat(shapview_loading, "translationY", 0F, mTranslationY)
        mObjectAnimator.interpolator = AccelerateInterpolator()
        var mShadowAnim = ObjectAnimator.ofFloat(shadow_view, "scaleX", 1F, 0.3F)

        var combin = AnimatorSet()
        combin.playTogether(mObjectAnimator, mShadowAnim)
        combin.setDuration(ANIMATOR_TIME)
        combin.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                shapview_loading.changeShaps()
                startUpAnim()
            }
        })
        combin.start()

    }

    /**
     * 上升组合动画
     */
    private fun startUpAnim() {

        if (isStopAnim) return
        var mObjectAnimator = ObjectAnimator.ofFloat(shapview_loading, "translationY", mTranslationY, 0F)
        mObjectAnimator.interpolator = DecelerateInterpolator()
        var mShadowAnim = ObjectAnimator.ofFloat(shadow_view, "scaleX", 0.3F, 1F)

        var combin = AnimatorSet()
        combin.playTogether(mObjectAnimator, mShadowAnim)
        combin.setDuration(ANIMATOR_TIME)
        combin.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                startFialAnimator()
            }

            override fun onAnimationStart(animation: Animator?) {
                startRotation()
            }
        })
        combin.start()

    }

    /**
     * 设置旋转动画
     */
    private fun startRotation() {
        var mRotationAnim: ObjectAnimator? = null
        when (shapview_loading.getCurrentView()) {
            ShapeView.Shapes.Circle, ShapeView.Shapes.Square -> {
                mRotationAnim = ObjectAnimator.ofFloat(shapview_loading, "rotation", 0F, -180F)
            }
            ShapeView.Shapes.Triangle -> {
                mRotationAnim = ObjectAnimator.ofFloat(shapview_loading, "rotation", 0F, -120F)

            }
        }
        mRotationAnim.setDuration(ANIMATOR_TIME)
        mRotationAnim.start()
    }


    fun dip2px(defValue: Float): Float {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defValue, resources.displayMetrics)
    }


    override fun setVisibility(visibility: Int) {
        super.setVisibility(View.INVISIBLE)
        var mParent = parent as ViewGroup
        if (mParent != null) {
            mParent.removeView(this)
            removeAllViews()
        }

        isStopAnim = true
    }
}
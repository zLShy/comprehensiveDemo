package com.shy.zlread.weight

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.widget.ImageView
import android.widget.RelativeLayout
import com.shy.zlread.R
import kotlinx.android.synthetic.main.activity_behavior.*
import java.util.*

/**
 * Created by zhangli on 2019/5/23.
 */
class FlowerLoveView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : RelativeLayout(context, attributeSet, defStyle) {

    private var mResources = arrayOf(R.drawable.pl_blue, R.drawable.pl_red, R.drawable.pl_yellow)
    private lateinit var mRandom: Random
    private var mWidth = 0
    private var mHeight = 0
    private var mImageHeight = 0
    private var mImageWidth = 0

    init {
        mRandom = Random()
    }

    fun addLoveView() {
        var mLoveIv = ImageView(context)
        mLoveIv.setImageResource(mResources[mRandom.nextInt(mResources.size)])
        var drable = ContextCompat.getDrawable(context, R.drawable.pl_blue)
        mImageHeight = drable.intrinsicHeight
        mImageWidth = drable.intrinsicWidth
        var mParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mParams.addRule(ALIGN_PARENT_BOTTOM)
        mParams.addRule(CENTER_HORIZONTAL)
        mLoveIv.layoutParams = mParams
        addView(mLoveIv)
        var set = getAnimSet(mLoveIv)
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                removeView(mLoveIv)
            }
        })
        set.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    private fun getAnimSet(mLoveIv: ImageView): AnimatorSet {
        var allset = AnimatorSet()
        var innerAnim = AnimatorSet()
        var alphaAnim = ObjectAnimator.ofFloat(mLoveIv, "alpha", 0.3f, 1f)
        var scaleXAnim = ObjectAnimator.ofFloat(mLoveIv, "scaleX", 0.3f, 1f)
        var scaleYAnim = ObjectAnimator.ofFloat(mLoveIv, "scaleY", 0.3f, 1f)
        innerAnim.playTogether(alphaAnim, scaleXAnim, scaleYAnim)
        innerAnim.setDuration(350)
        innerAnim.start()

        allset.playSequentially(innerAnim, getOutAnimSet(mLoveIv))
        return allset
    }

    private fun getOutAnimSet(mLoveIv: ImageView): ValueAnimator {
        var point0 = PointF((mWidth / 2).toFloat(), (mHeight - mImageHeight / 2).toFloat())
        var ponit1 = PointF(mRandom.nextInt(mWidth).toFloat(), (mRandom.nextInt(mHeight / 2) + mHeight / 2).toFloat())
        var point2 = PointF(mRandom.nextInt(mWidth).toFloat(), mRandom.nextInt(mHeight / 2).toFloat())
        var point3 = PointF(mRandom.nextInt(mWidth - mImageWidth / 2).toFloat(), 0F)
        var loveTypeEvaluator = LoveTypeEvaluator(ponit1, point2)
        var anim = ObjectAnimator.ofObject(loveTypeEvaluator, point0, point3)

        anim.duration = 3500
        anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {

                var pointF = animation.animatedValue as PointF
                mLoveIv.x = pointF.x
                mLoveIv.y = pointF.y
            }

        })
        return anim
    }
}
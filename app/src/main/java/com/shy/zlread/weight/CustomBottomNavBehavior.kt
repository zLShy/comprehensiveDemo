package com.shy.zlread.weight

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator

/**
 * Created by zhangli on 2019/4/17.
 */
class CustomBottomNavBehavior(context: Context, attributeSet: AttributeSet) : BottomSheetBehavior<View>(context, attributeSet) {

    private var isOut = false
    private var isFinished = true

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        var lp = child.layoutParams as CoordinatorLayout.LayoutParams
        var translationY = lp.bottomMargin + child.measuredHeight
        if (dyConsumed > 0) {
            if (!isOut) {
                if (isFinished) {
                    child.animate().translationY(translationY.toFloat()).setInterpolator(DecelerateInterpolator())
                            .setListener(object : Animator.AnimatorListener {
                                override fun onAnimationRepeat(animation: Animator?) {

                                }

                                override fun onAnimationEnd(animation: Animator?) {
                                    isFinished = true
                                }

                                override fun onAnimationCancel(animation: Animator?) {
                                    isFinished = true
                                }

                                override fun onAnimationStart(animation: Animator?) {
                                    isFinished = false
                                }


                            })
                            .setDuration(500).start()
                }
                isOut = true
            }
        } else if (dyConsumed < 0){
            if (isOut) {
                if (isFinished) {
                    child.animate().translationY(0F).setInterpolator(DecelerateInterpolator())
                            .setListener(object : Animator.AnimatorListener {
                                override fun onAnimationRepeat(animation: Animator?) {

                                }

                                override fun onAnimationEnd(animation: Animator?) {
                                    isFinished = true
                                }

                                override fun onAnimationCancel(animation: Animator?) {
                                    isFinished = true
                                }

                                override fun onAnimationStart(animation: Animator?) {
                                    isFinished = false
                                }

                            })
                            .setDuration(500).start()
                }
                isOut = false
            }
        }
    }
}
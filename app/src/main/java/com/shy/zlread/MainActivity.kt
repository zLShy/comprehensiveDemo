package com.shy.zlread

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        step_view.setTotleStep(4000F)

        var valueAnim = ValueAnimator.ofInt(3000)
        valueAnim.setDuration(2000)
        valueAnim.interpolator = DecelerateInterpolator()
        valueAnim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                var currentStep = animation!!.animatedValue as Int
                step_view.setCurrentStep(currentStep)
            }

        })
        valueAnim.start()

//        Thread(object : Runnable {
//            override fun run() {
//                while (true) {
//                    runOnUiThread(object : Runnable {
//                        override fun run() {
//                            shapview.changeShaps()
//                        }
//
//                    })
//
//                    try {
//                        Thread.sleep(1000)
//                    } catch (e: Exception) {
//                    }
//                }
//            }
//
//        })
        Thread(Runnable {
            run {
                while (true) {
                    runOnUiThread {
                        run {
                            shapview.changeShaps()
                            Log.e("TAG", "==========")
                        }
                    }
                    try {
                        Thread.sleep(1000)
                    } catch (e: Exception) {
                    }
                }
            }
        }).start()
    }
}

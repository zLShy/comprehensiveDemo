package com.shy.zlread.weight

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * Created by zhangli on 2019/5/24.
 */
class LoveTypeEvaluator(ponitF1: PointF, pointF2: PointF) : TypeEvaluator<PointF> {
    private lateinit var point1: PointF
    private lateinit var point2: PointF
    private lateinit var pointF: PointF

    init {
        this.point1 = ponitF1
        this.point2 = pointF2
        this.pointF = PointF()
    }

    override fun evaluate(t: Float, ponit0: PointF, point3: PointF): PointF {
        //P(t)=(1−t)3P1​+3t(1−t)2P2​+3t2(1−t)P3​+t3P4​
        pointF.x = ponit0.x * (1 - t) * (1 - t) * (1 - t)
        +3 * t * (1 - t) * (1 - t) * point1.x
        +3 * t * t * (1 - t) * point2.x
        +t * t * t * point3.x

        pointF.y = ponit0.y * (1 - t) * (1 - t) * (1 - t)
        +3 * t * (1 - t) * (1 - t) * point1.y
        +3 * t * t * (1 - t) * point2.y
        +t * t * t * point3.y
        return pointF
    }
}
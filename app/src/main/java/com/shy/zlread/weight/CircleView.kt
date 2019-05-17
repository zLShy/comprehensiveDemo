package com.shy.zlread.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.shy.zlread.R

/**
 * Created by zhangli on 2019/5/13.
 */
class CircleView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : View(context, attributeSet, defStyle) {
    private var mCirclePaint: Paint? = null

    private var mCircleColor = Color.BLUE

    init {
        var mTypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleView)
        mCircleColor = mTypedArray.getColor(R.styleable.CircleView_circleColor,mCircleColor)
        mCirclePaint = Paint()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mWith = MeasureSpec.getSize(widthMeasureSpec)
        var mHeight = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(if (mWith > mHeight) mHeight else mWith, if (mWith > mHeight) mHeight else mWith)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var mRadius = (width / 2).toFloat()
        mCirclePaint!!.color = mCircleColor
        mCirclePaint!!.style = Paint.Style.FILL
        mCirclePaint!!.strokeCap = Paint.Cap.ROUND
        canvas.drawCircle(mRadius, mRadius, mRadius, mCirclePaint)
    }
}
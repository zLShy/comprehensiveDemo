package com.shy.zlread.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.shy.zlread.R

/**
 * Created by zhangli on 2019/3/20.
 */
class StepProgress @JvmOverloads constructor(context: Context,attributeSet: AttributeSet? = null,defStyleStr: Int = 0):View(context,attributeSet,defStyleStr) {
    private var mOutColor:Int = Color.RED
    private var mInnerColor:Int = Color.BLUE
    private var mTextColor:Int = Color.BLUE
    private var mCircleWidth = 20
    private var mTextSize = 16
    private var mOutPaint:Paint? = null
    private var mInnerPaint:Paint? = null
    private var mTextPaint:Paint? = null
    private var mCurrentStep = 0
    private var mTotleStep = 0F
    init {
        var arrays = context.obtainStyledAttributes(attributeSet, R.styleable.StepProgress)
        mOutColor = arrays.getColor(R.styleable.StepProgress_outColor,mOutColor)
        mInnerColor = arrays.getColor(R.styleable.StepProgress_innerColor,mInnerColor)
        mTextColor = arrays.getColor(R.styleable.StepProgress_circleTextColor,mTextColor)
        mCircleWidth = arrays.getDimension(R.styleable.StepProgress_circleWidth, mCircleWidth.toFloat()).toInt()
        mTextSize = arrays.getDimensionPixelSize(R.styleable.StepProgress_circleTextSize,mTextSize)

        //外圆画笔设置
        mOutPaint = Paint()
        mOutPaint!!.color = mOutColor
        mOutPaint!!.strokeWidth = mCircleWidth.toFloat()
        mOutPaint!!.style = Paint.Style.STROKE
        //内圆画笔设置
        mInnerPaint = Paint()
        mInnerPaint!!.color = mInnerColor
        mInnerPaint!!.strokeWidth = mCircleWidth.toFloat()
        mInnerPaint!!.style = Paint.Style.STROKE
        mInnerPaint!!.strokeCap = Paint.Cap.ROUND
        //文字画笔设置
        mTextPaint = Paint();
        mTextPaint!!.color = mTextColor
        mTextPaint!!.textSize = mTextSize.toFloat()
        mTextPaint!!.style = Paint.Style.STROKE
        arrays.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heigthMode = MeasureSpec.getMode(heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST) {
            width = dp2px(80)
        }
        if (heigthMode == MeasureSpec.AT_MOST) {
            height = dp2px(80)
        }
        setMeasuredDimension(if (width>height) height else width,if (width>height) height else width)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var mRadius = width /2 - mCircleWidth/2
        canvas!!.drawCircle((width/2).toFloat(), (height/2).toFloat(), mRadius.toFloat(),mOutPaint)

        if (mTotleStep == 0F) return
        var sweepAngle = mCurrentStep / mTotleStep
        canvas!!.drawArc((mCircleWidth/2).toFloat(), (mCircleWidth/2).toFloat(), (width-mCircleWidth/2).toFloat(), (height-mCircleWidth/2).toFloat(),135F, 360F * sweepAngle,false,mInnerPaint)
        var mStep = mCurrentStep.toString()
        var bounds = Rect()
        mTextPaint!!.getTextBounds(mStep,0,mStep.length,bounds)
        var mFontMetrics = mTextPaint!!.fontMetricsInt
        var dx = width/2-bounds.width()/2
        var dy = (mFontMetrics.bottom - mFontMetrics.top) /2 - mFontMetrics.bottom
        var baseLine = height/2 + dy
        canvas!!.drawText(mStep, dx.toFloat(), baseLine.toFloat(),mTextPaint)
    }
    private fun px2sp(mTextSize: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize.toFloat(),null).toInt()
    }

    private fun dp2px(dpValue: Int): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun setTotleStep(totleStep: Float) {
        this.mTotleStep = totleStep
    }

    fun setCurrentStep(currentStep: Int) {

        this.mCurrentStep = currentStep
        invalidate()
    }
}
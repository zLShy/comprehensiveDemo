package com.shy.zlread.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

/**
 * Created by zhangli on 2019/5/15.
 */
class BubbleView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : View(context, attributeSet, defStyle) {
    private var mPaint: Paint? = null
    private var mRadius = dip2px(8F)
    private var mMaxRadius = dip2px(8F)
    private var mMinRadius = dip2px(3F)
    private var mDragRadius = 0F
    private var mDragPoint: PointF? = null
    private var mBottomPoint: PointF? = null

    private fun dip2px(defValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defValue, resources.displayMetrics)
    }

    init {

        mPaint = Paint()
        initPaint(mPaint!!)
    }

    private fun initPaint(paint: Paint) {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.isDither = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mDragPoint == null || mBottomPoint == null) return
        var mPath = getBeizerPath()
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint)
            canvas.drawCircle(mBottomPoint!!.x, mBottomPoint!!.y, mDragRadius, mPaint)
        }
        canvas.drawCircle(mDragPoint!!.x, mDragPoint!!.y, mRadius, mPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                mDragPoint!!.x = event.x
                mDragPoint!!.y = event.y

            }

            MotionEvent.ACTION_DOWN -> {
                initPoint(event)
            }

            MotionEvent.ACTION_UP -> {


            }
        }
        invalidate()
        return true

    }

    fun initPoint(event: MotionEvent) {
        mBottomPoint = PointF()
        mDragPoint = PointF()
        mDragPoint!!.x = event.x
        mDragPoint!!.y = event.y
        mBottomPoint!!.x = event.x
        mBottomPoint!!.y = event.y
    }

    fun lineDistance(pointF1: PointF, pointF2: PointF) {
        var distanceX = Math.pow(((pointF1.x - pointF2.x).toDouble()), 2.0)
        var distanceY = Math.pow((pointF1.y - pointF2.y).toDouble(), 2.0)
        var distance = Math.sqrt(distanceX + distanceY)
        mDragRadius = (mMaxRadius - distance / 14).toFloat()

    }


    fun getBeizerPath(): Path? {
        lineDistance(mDragPoint!!, mBottomPoint!!)
        if (mDragRadius < mMinRadius) {
            return null
        }
        var dy = mBottomPoint!!.y - mDragPoint!!.y
        var dx = mBottomPoint!!.x - mDragPoint!!.x
        var tanA = Math.abs(dy) / Math.abs(dx)
        var pointx1 = Math.sqrt((((mMaxRadius * mMaxRadius) / (1 + tanA * tanA)).toDouble()))
        var pointy1 = pointx1 / tanA
        var p0 = PointF((mBottomPoint!!.x + pointx1).toFloat(), (mBottomPoint!!.y - pointy1).toFloat())
        var p1 = PointF((mBottomPoint!!.x - pointx1).toFloat(), (mBottomPoint!!.y + pointy1).toFloat())

        var pointx2 = Math.sqrt(((mRadius * mRadius) / (1 + tanA * tanA)).toDouble())
        var pointy2 = pointx2 / tanA
        var p2 = PointF((mDragPoint!!.x + pointx2).toFloat(), (mDragPoint!!.y - pointy2).toFloat())
        var p3 = PointF((mDragPoint!!.x - pointx2).toFloat(), (mDragPoint!!.y + pointy2).toFloat())

        var a = Math.atan(tanA.toDouble())

        var centerX = (mDragPoint!!.x - mBottomPoint!!.x) / 2 + mBottomPoint!!.x
        var centerY = (mDragPoint!!.y - mBottomPoint!!.y) / 2 + mBottomPoint!!.y
        var mPath = Path()
        mPath.moveTo(p0.x, p0.y)
        mPath.quadTo(centerX, centerY, p2.x, p2.y)

        mPath.lineTo(p3.x, p3.y)
        mPath.quadTo(centerX, centerY, p1.x, p1.y)
        mPath.close()
        return mPath
    }

    fun attch(mView: View,disapperListener: disapperListener) {

    }

   open interface disapperListener {
        fun disMiss()
    }
}
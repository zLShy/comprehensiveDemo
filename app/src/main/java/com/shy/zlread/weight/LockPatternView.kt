package com.shy.zlread.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.shy.zlread.bean.PointKt
import com.shy.zlread.utils.MathUtil

/**
 * Created by zhangli on 2019/4/4.
 */
class LockPatternView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet? = null,defStyleInt: Int = 0): View(context,attributeSet,defStyleInt) {

    private lateinit var mNormalPaint:Paint
    private lateinit var mOutCicrclePaint:Paint
    private lateinit var mArrowPaint: Paint
    private lateinit var mPressPaint: Paint

    private var mNormalColor = Color.WHITE
    private var mPressColor = Color.BLUE
    private var mErrorColor = Color.RED
    private var isInit = false
    private var mWidth = 0
    private var mHeight = 0
    private var mPointKts: Array<Array<PointKt?>> = Array(3) { Array<PointKt?>(3, { null }) }
    private var mRadius = 0
    private var mMovingX = 0f
    private var mMovingY = 0f
    private var isTouchPoint = false
    private var mSelectPoint = ArrayList<PointKt>()
    init {
        mNormalPaint = Paint()
        mNormalPaint.style = Paint.Style.STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            isInit = true
            if (mWidth > mHeight) {
                var squaredHeight = width / 3
                var offsetX = (width - height) / 2
                mRadius = squaredHeight / 2
                mPointKts[0][0] = PointKt(offsetX + squaredHeight / 2, squaredHeight / 2, 0)
                mPointKts[0][1] = PointKt(offsetX + squaredHeight * 3 / 2, squaredHeight / 2, 1)
                mPointKts[0][2] = PointKt(offsetX + squaredHeight * 5 / 2, squaredHeight / 2, 2)
                mPointKts[1][0] = PointKt(offsetX + squaredHeight / 2, squaredHeight * 3 / 2, 3)
                mPointKts[1][1] = PointKt(offsetX + squaredHeight * 3 / 2, squaredHeight * 3 / 2, 4)
                mPointKts[1][2] = PointKt(offsetX + squaredHeight * 5 / 2, squaredHeight * 3 / 2, 5)
                mPointKts[2][0] = PointKt(offsetX + squaredHeight / 2, squaredHeight * 5 / 2, 6)
                mPointKts[2][1] = PointKt(offsetX + squaredHeight * 3 / 2, squaredHeight * 5 / 2, 7)
                mPointKts[2][2] = PointKt(offsetX + squaredHeight * 5 / 2, squaredHeight * 5 / 2, 8)

            }else{
                var squaredWidth = width / 3
                var offsetY = (height - width) / 2
                mRadius = squaredWidth / 2
                mPointKts[0][0] = PointKt(squaredWidth / 2, offsetY + squaredWidth / 2, 0)
                mPointKts[0][1] = PointKt(squaredWidth * 3 / 2, offsetY + squaredWidth / 2, 1)
                mPointKts[0][2] = PointKt(squaredWidth * 5 / 2, offsetY + squaredWidth / 2, 2)
                mPointKts[1][0] = PointKt(squaredWidth / 2, offsetY + squaredWidth * 3 / 2, 3)
                mPointKts[1][1] = PointKt(squaredWidth * 3 / 2, offsetY + squaredWidth  * 3 / 2, 4)
                mPointKts[1][2] = PointKt(squaredWidth * 5 / 2, offsetY + squaredWidth * 3 / 2, 5)
                mPointKts[2][0] = PointKt(squaredWidth / 2, offsetY + squaredWidth * 5 / 2, 6)
                mPointKts[2][1] = PointKt(squaredWidth * 3 / 2, offsetY + squaredWidth * 5 / 2, 7)
                mPointKts[2][2] = PointKt(squaredWidth * 5 / 2, offsetY + squaredWidth * 5 / 2, 8)
            }

        }

        for (mPoint in mPointKts) {
            for (point in mPoint) {
                if (point!!.isStatusNormal()) {
                    mNormalPaint.color = Color.RED
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point.centerY.toFloat(), (mRadius / 6).toFloat(),mNormalPaint)
                    mNormalPaint.color = Color.BLUE
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point.centerY.toFloat(), (mRadius / 2).toFloat(),mNormalPaint)
                }
                if(point!!.isStatusPress()) {
                    mNormalPaint.color = Color.BLUE
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point.centerY.toFloat(), (mRadius / 6).toFloat(),mNormalPaint)
                    mNormalPaint.color = Color.BLUE
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point.centerY.toFloat(), (mRadius / 2).toFloat(),mNormalPaint)
                }
                if (point!!.isStatusError()) {
                    mNormalPaint.color = Color.RED
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point.centerY.toFloat(), (mRadius / 6).toFloat(),mNormalPaint)
                    mNormalPaint.color = Color.RED
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point.centerY.toFloat(), (mRadius / 2).toFloat(),mNormalPaint)
                }

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        mMovingX = event.x
        mMovingY = event.y
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

                var point = pointKt
                if (point != null) {
                    isTouchPoint = true
                    mSelectPoint.add(point)
                    point.setPressStatus()
                }
            }
            MotionEvent.ACTION_MOVE -> {

            }

            MotionEvent.ACTION_UP -> {
                isTouchPoint = false
            }
        }
        invalidate()
        return true
    }

    /**
     * 获取按下的点
     * @return 当前按下的点
     */
    private val pointKt: PointKt? get() {
            for (i in 0..2) {
                for (point in mPointKts[i]) {
                    // for 循环九个点，判断手指位置是否在这个九个点里面
                    if (MathUtil.checkInRound(point!!.centerX.toFloat(), point.centerY.toFloat(),
                                    mRadius.toFloat(), mMovingX, mMovingY)) {
                        return point
                    }
                }
            }
            return null
        }

}
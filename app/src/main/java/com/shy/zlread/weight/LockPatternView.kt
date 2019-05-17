package com.shy.zlread.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.shy.zlread.bean.Point
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
    private var mPoints: Array<Array<Point?>> = Array(3) { Array<Point?>(3, { null }) }
    private var mRadius = 0
    private var mMovingX = 0f
    private var mMovingY = 0f
    private var isTouchPoint = false
    private var mSelectPoint = ArrayList<Point>()
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
                mPoints[0][0] = Point(offsetX + squaredHeight / 2, squaredHeight / 2, 0)
                mPoints[0][1] = Point(offsetX + squaredHeight * 3 / 2, squaredHeight / 2, 1)
                mPoints[0][2] = Point(offsetX + squaredHeight * 5 / 2, squaredHeight / 2, 2)
                mPoints[1][0] = Point(offsetX + squaredHeight / 2, squaredHeight * 3 / 2, 3)
                mPoints[1][1] = Point(offsetX + squaredHeight * 3 / 2, squaredHeight * 3 / 2, 4)
                mPoints[1][2] = Point(offsetX + squaredHeight * 5 / 2, squaredHeight * 3 / 2, 5)
                mPoints[2][0] = Point(offsetX + squaredHeight / 2, squaredHeight * 5 / 2, 6)
                mPoints[2][1] = Point(offsetX + squaredHeight * 3 / 2, squaredHeight * 5 / 2, 7)
                mPoints[2][2] = Point(offsetX + squaredHeight * 5 / 2, squaredHeight * 5 / 2, 8)

            }else{
                var squaredWidth = width / 3
                var offsetY = (height - width) / 2
                mRadius = squaredWidth / 2
                mPoints[0][0] = Point(squaredWidth / 2, offsetY + squaredWidth / 2, 0)
                mPoints[0][1] = Point(squaredWidth * 3 / 2, offsetY + squaredWidth / 2, 1)
                mPoints[0][2] = Point(squaredWidth * 5 / 2, offsetY + squaredWidth / 2, 2)
                mPoints[1][0] = Point(squaredWidth / 2, offsetY + squaredWidth * 3 / 2, 3)
                mPoints[1][1] = Point(squaredWidth * 3 / 2, offsetY + squaredWidth  * 3 / 2, 4)
                mPoints[1][2] = Point(squaredWidth * 5 / 2, offsetY + squaredWidth * 3 / 2, 5)
                mPoints[2][0] = Point(squaredWidth / 2, offsetY + squaredWidth * 5 / 2, 6)
                mPoints[2][1] = Point(squaredWidth * 3 / 2, offsetY + squaredWidth * 5 / 2, 7)
                mPoints[2][2] = Point(squaredWidth * 5 / 2, offsetY + squaredWidth * 5 / 2, 8)
            }

        }

        for (mPoint in mPoints) {
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

                var point = point
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
    private val point: Point? get() {
            for (i in 0..2) {
                for (point in mPoints[i]) {
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
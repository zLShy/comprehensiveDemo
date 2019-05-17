package com.shy.zlread.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Binder
import android.support.v4.content.ContextCompat
import android.text.style.CharacterStyle
import android.util.AttributeSet
import android.view.View
import com.shy.zlread.R

/**
 * Created by zhangli on 2019/4/26.
 */
class ShapeView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : View(context, attributeSet, defStyle) {

    var mPaint: Paint? = null
    var mCurrentShapes = Shapes.Triangle



    enum class Shapes {
        Circle, Square, Triangle
    }

    init {
        mPaint = Paint()
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.isAntiAlias = true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(dp2px(40F), dp2px(40F))
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mCurrentShapes) {
            Shapes.Square -> {
                mPaint!!.color = ContextCompat.getColor(context, R.color.rect)
                canvas.drawRect(0F,0F,width.toFloat(),height.toFloat(),mPaint)
            }
            Shapes.Circle -> {
                mPaint!!.color = ContextCompat.getColor(context,R.color.circle)
                canvas.drawCircle((width/2).toFloat(), (width/2).toFloat(), (width/2).toFloat(),mPaint)
            }
            Shapes.Triangle -> {
                mPaint!!.color = ContextCompat.getColor(context,R.color.triangle)
                var mPath = Path()
                mPath.moveTo((width/2).toFloat(), 0F)
                mPath.lineTo(0F, (width / 2 * Math.sqrt(3.0)).toFloat())
                mPath.lineTo(width.toFloat(), (Math.sqrt(3.0) * width / 2).toFloat())
                mPath.close()
                canvas.drawPath(mPath,mPaint)
            }
        }
    }

    fun changeShaps():Unit {
        when(mCurrentShapes) {
            Shapes.Triangle -> {
                mCurrentShapes = Shapes.Square
            }

            Shapes.Square -> {
                mCurrentShapes = Shapes.Circle
            }

            Shapes.Circle -> {
                mCurrentShapes = Shapes.Triangle
            }
        }

        invalidate()
    }

    fun getCurrentView(): Shapes {
        return mCurrentShapes
    }
    fun dp2px(defValue: Float): Int {

        return (resources.displayMetrics.density * defValue + 0.5F).toInt()
    }
}
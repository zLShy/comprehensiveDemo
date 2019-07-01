package com.shy.zlread.weight;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.shy.zlread.bean.Point;

/**
 * Created by zhangli on 2019/6/25.
 */

public class BezierPath extends android.support.v7.widget.AppCompatImageView {
    //    private Paint mPaint;
    private int mWidth;
    private int mheigth;
    private PointF mCenterPoint;
    private PointF startPoint;
    private PointF endPoint;
    private Bitmap mBitmap;

    public BezierPath(Context context) {
        this(context, null);
    }

    public BezierPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setColor(Color.GRAY);
//        mPaint.setStyle(Paint.Style.STROKE);
        mWidth = getResources().getDisplayMetrics().widthPixels;
        mheigth = getResources().getDisplayMetrics().heightPixels;
        startPoint = new PointF(0, 0);
        endPoint = new PointF(mWidth, mheigth);
        mCenterPoint = new PointF(mWidth, 0);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        mheigth = MeasureSpec.getSize(heightMeasureSpec);
//        mCenterPoint = new PointF(mWidth, 0);
//        startPoint = new PointF(0, 0);
//        endPoint = new PointF(mWidth, mheigth);
//    }

//    @Override
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//        Path path = new Path();
//        path.moveTo(0, 0);
//        path.quadTo(mWidth, 0, mWidth, mheigth);
//        canvas.drawPath(path, mPaint);
//    }

    public void startAnim() {
//        setPosition(startPoint);
        ShopTypeEvaluator typeEvaluator = new ShopTypeEvaluator(mCenterPoint);
        ValueAnimator animator = ObjectAnimator.ofObject(typeEvaluator, startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                setPosition(pointF);
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    private void setPosition(PointF pointF) {

//            "translationY"
        setTranslationX(pointF.x);
        setTranslationY(pointF.y);


    }
}

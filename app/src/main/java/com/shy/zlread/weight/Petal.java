package com.shy.zlread.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shy.zlread.bean.Point;
import com.shy.zlread.utils.MathUtil;

import java.util.ArrayList;

/**
 * Created by zhangli on 2019/6/12.
 */

public class Petal {

    private float mStretchA;//第一个控制点延长线倍数
    private float mStretchB;//第二个控制点延长线倍数
    private float mStartAngle;//起始旋转角，用于确定第一个端点
    private float mAngle;//两条线之间夹角，由起始旋转角和夹角可以确定第二个端点
    private int mRadius = 2;//花芯的半径
    private float mGrowFactor;//增长因子，花瓣是有开放的动画效果，这个参数决定花瓣展开速度
    private int mColor;//花瓣颜色
    private boolean isFinished = false;//花瓣是否绽放完成
    private Path mPath = new Path();//用于保存三次贝塞尔曲线
    private Paint mPaint = new Paint();//画笔

    public Petal(float stretchA, float stretchB, int beginAngle, float angle, int mColor, float growFactor) {

        this.mStretchA = stretchA;
        this.mStretchB = stretchB;
        this.mStartAngle = beginAngle;
        this.mAngle = angle;
        this.mColor = mColor;
        this.mGrowFactor = growFactor;
        mPaint.setColor(mColor);
    }

    //用于渲染花瓣，通过不断更改半径使得花瓣越来越大
    public void render(Point mPoint, int mRadius, Canvas canvas) {

        if (this.mRadius < mRadius) {
            this.mRadius += mGrowFactor;
        } else {
            isFinished = true;
        }

        this.draw(mPoint, canvas);
    }

    //绘制花瓣，参数p是花芯的圆心的坐标
    private void draw(Point mPoint, Canvas canvas) {

        if (!isFinished) {
            mPath = new Path();
            Point t = new Point(0, this.mRadius).rotate(MathUtil.degrad(this.mStartAngle));
            //第一个端点，为了保证圆心不会随着radius增大而变大这里固定为3
            Point v1 = new Point(0, 3).rotate(MathUtil.degrad(this.mStartAngle));
            //第二个端点
            Point v2 = t.clone().rotate(MathUtil.degrad(this.mAngle));
            //延长线，分别确定两个控制点
            Point v3 = t.clone().mult(this.mStretchA);
            Point v4 = v2.clone().mult(this.mStretchB);

            //由于圆心在p点，因此，每个点要加圆心坐标点

            v1.add(mPoint);
            v2.add(mPoint);
            v3.add(mPoint);
            v4.add(mPoint);
            mPath.moveTo(v1.x, v1.y);
            mPath.cubicTo(v3.x, v3.y, v4.x, v4.y, v2.x, v2.y);
            mPath.close();
        }

        canvas.drawPath(mPath, mPaint);
    }
}

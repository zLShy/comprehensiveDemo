package com.shy.zlread.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shy.zlread.utils.MathUtil;

/**
 * Created by zhangli on 2019/6/11.
 */

public class FireworksBackGround extends Drawable {
    private float mBackGroundWidth;
    private float mBackGroundHeight;
    private float mMoonRidus;
    private float mMoonCenterX;
    private float mMoonCenterY;
    private int moonCenterColor = Color.argb(255, 255, 249, 177);
    private int moonLightColor = Color.argb(0, 255, 249, 177);
    private int skyColor = Color.argb(255, 0, 31, 86);

    private Paint mPaint = new Paint();
    private float starR;

    public FireworksBackGround(float backGroundWidth, float backGroundHeight) {
        mBackGroundWidth = backGroundWidth;
        mBackGroundHeight = backGroundHeight;
        mMoonRidus = mBackGroundWidth / 20;
        mMoonCenterX = mBackGroundWidth / 4 * 3;
        mMoonCenterY = mBackGroundHeight / 7;
        starR = mMoonRidus / 3;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        drawNight(canvas);
        drawMoon(canvas);
        drawStar(canvas);
    }

    /**
     * 画星星
     *
     * @param canvas
     */
    private void drawStar(Canvas canvas) {
        PointF pointF1 = new PointF(mBackGroundWidth / 4, mBackGroundHeight / 9);
        PointF pointF2 = new PointF(mBackGroundWidth / 3, mBackGroundHeight / 5);
        PointF pointF3 = new PointF(mBackGroundWidth / 2, mBackGroundHeight / 9);
        drawStar(pointF1, MathUtil.randomFloat(starR / 2, starR), canvas);
        drawStar(pointF2, MathUtil.randomFloat(starR / 2, starR), canvas);
        drawStar(pointF3, MathUtil.randomFloat(starR / 2, starR), canvas);
    }
    private void drawStar(PointF pointF,float scale,Canvas canvas){
        float centerX = pointF.x;
        float centerY = pointF.y;
        float saddleP = scale/8;
        float halfScale = scale/2;
        Path path = new Path();
        RadialGradient radialGradient = new RadialGradient(
                centerX, centerY, scale,
                new int[]{Color.WHITE, Color.WHITE,Color.TRANSPARENT}, new float[]{0f, 0.1f, 1f},
                Shader.TileMode.MIRROR
        );
        path.moveTo(centerX - halfScale,centerY);
        path.quadTo(centerX - saddleP,centerY - saddleP,centerX,centerY - scale);
        path.quadTo(centerX + saddleP,centerY - saddleP,centerX+halfScale,centerY);
        path.quadTo(centerX + saddleP,centerY + saddleP,centerX,centerY+scale);
        path.quadTo(centerX - saddleP,centerY + saddleP,centerX-halfScale,centerY);
        path.close();
        mPaint.setShader(radialGradient);
        canvas.drawPath(path,mPaint);
    }
    /**
     * 画月亮
     *
     * @param canvas
     */
    private void drawMoon(Canvas canvas) {

        RadialGradient radialGradient = new RadialGradient(mMoonCenterX, mMoonCenterY, mMoonRidus
                , new int[]{moonCenterColor, moonCenterColor, moonLightColor}
                , new float[]{0F, 0.5F, 1F}
                , Shader.TileMode.MIRROR);
        mPaint.setShader(radialGradient);
        canvas.drawCircle(mMoonCenterX, mMoonCenterY, mMoonRidus, mPaint);
    }

    /**
     * 画背景
     *
     * @param canvas
     */
    private void drawNight(Canvas canvas) {

        LinearGradient linearGradient = new LinearGradient(mBackGroundWidth / 2, 0, mBackGroundWidth / 2, mBackGroundHeight
                , new int[]{skyColor, skyColor, Color.BLACK}
                , new float[]{0F, 0.5F, 1F}
                , Shader.TileMode.MIRROR);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0, 0, mBackGroundWidth, mBackGroundHeight, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mBackGroundHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mBackGroundWidth;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}

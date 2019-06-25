package com.shy.zlread.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.shy.zlread.R;
import com.zl.map.Utils.Constants;

/**
 * Created by zhangli on 2019/6/24.
 */

public class WaveView extends View {
    private Paint mPaint;
    private int mWaveWidth = dip2px(20);
    private int mWaveHeight = dip2px(20);
    private PointF startPoint;
    private int width;
    private int height;

    public WaveView(Context context) {
        this(context, null);
    }

    private int cycle = 200;

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        mWaveWidth = (int) array.getDimension(R.styleable.WaveView_waveWith, mWaveWidth);
        mWaveHeight = (int) array.getDimension(R.styleable.WaveView_waveHeight, mWaveHeight);
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
//        startPoint = new PointF(0, height / 2);
        startPoint = new PointF(-cycle * 4, height / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        int j = 1;
        //循环绘制正弦曲线 循环一次半个周期
        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                //波峰
                path.quadTo(startPoint.x + (cycle * j), startPoint.y + mWaveHeight,
                        startPoint.x + (cycle * 2) * i, startPoint.y);
            } else {
                //波谷
                path.quadTo(startPoint.x + (cycle * j), startPoint.y - mWaveHeight,
                        startPoint.x + (cycle * 2) * i, startPoint.y);
            }
            j += 2;
        }
        //绘制封闭的曲线
        path.lineTo(width, height);//右下角
        path.lineTo(startPoint.x, height);//左下角
        path.lineTo(startPoint.x, startPoint.y);//起点
        path.close();
        canvas.drawPath(path, mPaint);

        //判断是不是平移完了一个周期
        if (startPoint.x + 40 >= 0) {
            //满了一个周期则恢复默认起点继续平移
            startPoint.x = -cycle * 4;
        }
//每次波形的平移量 40
        startPoint.x += 40;
        postInvalidateDelayed(150);
        path.reset();

    }

    private int dip2px(float defValue) {


        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defValue, getResources().getDisplayMetrics());
    }
}

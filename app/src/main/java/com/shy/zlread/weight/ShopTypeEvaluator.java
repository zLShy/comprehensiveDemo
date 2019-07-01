package com.shy.zlread.weight;

import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.PointF;

import com.shy.zlread.bean.Point;

/**
 * Created by zhangli on 2019/6/25.
 */

public class ShopTypeEvaluator implements TypeEvaluator<PointF> {
    private PointF centerPoint;

    public ShopTypeEvaluator(PointF centerPoint) {
        this.centerPoint = centerPoint;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

        PointF pointF = new PointF();
        pointF.x = (1 - fraction) * (1 - fraction) * startValue.x
                + 2 * fraction * (1 - fraction) * centerPoint.x
                + fraction * fraction * endValue.x;
        pointF.y = (1 - fraction) * (1 - fraction) * startValue.y
                + 2 * fraction * (1 - fraction) * centerPoint.y
                + fraction * fraction * endValue.y;
        return pointF;
    }
}

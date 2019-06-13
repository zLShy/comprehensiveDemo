package com.shy.zlread.weight;

import android.graphics.Canvas;

import com.shy.zlread.bean.Point;
import com.shy.zlread.utils.MathUtil;

import java.util.ArrayList;

/**
 * Created by zhangli on 2019/6/13.
 */

public class Bloom {
    private int mColor;//整个花朵的颜色
    private Point mPoint;//花芯圆心
    private int mRadius; //花芯半径
    private ArrayList<Petal> mPetals;//用于保存花瓣


    public Bloom( Point mPoint, int mRadius,int mColor, int petalsCount) {
        this.mColor = mColor;
        this.mPoint = mPoint;
        this.mRadius = mRadius;
        this.mPetals = new ArrayList<>(petalsCount);

        float angle = 360f / petalsCount;
        int startAngle = MathUtil.randomInt(0, 90);

        for (int i=0;i<petalsCount;i++) {
            //随机产生第一个控制点的拉伸倍数
            float stretchA = MathUtil.random(Options.minPetalStretch, Options.maxPetalStretch);
            //随机产生第二个控制地的拉伸倍数
            float stretchB = MathUtil.random(Options.minPetalStretch, Options.maxPetalStretch);
            //计算每个花瓣的起始角度
            int beginAngle = startAngle + (int) (i * angle);
            //随机产生每个花瓣的增长因子（即绽放速度）
            float growFactor = MathUtil.random(Options.minGrowFactor, Options.maxGrowFactor);
            //创建一个花瓣，并添加到花瓣列表中
            this.mPetals.add(new Petal(stretchA, stretchB, beginAngle, angle, mColor, growFactor));
        }

    }

    public void draw(Canvas canvas) {
        Petal petal;
        for (int i=0;i<mPetals.size();i++) {
            petal = mPetals.get(i);
            petal.render(mPoint, this.mRadius, canvas);
        }
    }
    public int getColor() {
        return mColor;
    }

    public Point getPoint() {
        return mPoint;
    }

    static class Options {
        //用于控制产生随机花瓣个数范围
        public static int minPetalCount = 8;
        public static int maxPetalCount = 15;
        //用于控制产生延长线倍数范围
        public static float minPetalStretch = 2f;
        public static float maxPetalStretch = 3.5f;
        //用于控制产生随机增长因子范围,增长因子决定花瓣绽放速度
        public static float minGrowFactor = 1f;
        public static float maxGrowFactor = 1.1f;
        //用于控制产生花朵半径随机数范围
        public static int minBloomRadius = 8;
        public static int maxBloomRadius = 10;
        //用于产生随机颜色
        public static int minRedColor = 128;
        public static int maxRedColor = 255;
        public static int minGreenColor = 0;
        public static int maxGreenColor = 128;
        public static int minBlueColor = 0;
        public static int maxBlueColor = 128;
        //花瓣的透明度
        public static int opacity = 50;//0.1
    }
}

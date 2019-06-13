package com.shy.zlread.utils;

import android.graphics.Color;

public class MathUtil {
    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2)
                + Math.abs(y1 - y2) * Math.abs(y1 - y2));
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public static double pointTotoDegrees(double x, double y) {
        return Math.toDegrees(Math.atan2(x, y));
    }

    public static boolean checkInRound(float sx, float sy, float r, float x,
                                       float y) {
        // x的平方 + y的平方 开根号 < 半径
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
    }

    public static float randomFloat(float min, float max) {
        return min + (float) Math.random() * (max - min);
    }

//    public static int randomInt(int min, int max) {
//        return min + (int) (Math.random() * (max - min + 1));
//    }


    public static float circle = (float) (2 * Math.PI);

    public static int rgba(int r, int g, int b, int a) {
        return Color.argb(a, r, g, b);
    }

    public static int randomInt(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    public static float random(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    //产生随机的argb颜色
    public static int randomrgba(int rmin, int rmax, int gmin, int gmax, int bmin, int bmax, int a) {
        int r = Math.round(random(rmin, rmax));
        int g = Math.round(random(gmin, gmax));
        int b = Math.round(random(bmin, bmax));
        int limit = 5;
        if (Math.abs(r - g) <= limit && Math.abs(g - b) <= limit && Math.abs(b - r) <= limit) {
            return rgba(rmin, rmax, gmin, gmax);
        } else {
            return rgba(r, g, b, a);
        }
    }

    //角度转弧度
    public static float degrad(float angle) {
        return circle / 360 * angle;
    }
}
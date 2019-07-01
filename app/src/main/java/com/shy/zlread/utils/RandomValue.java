package com.shy.zlread.utils;

import java.util.Random;

/**
 * Created by zhangli on 2019/7/1.
 */

public class RandomValue {

    private static String[] mPhone = {"15982056336", "18181914796", "17308004973", "15680234882"};
    private static String[] mName = {"张三", "李四", "小明", "王五"};
    private static String[] mRoads = {"天府三街", "天府四街", "天府五街", "剑南大道"};
    private static String[] mShcools = {"成都七中", "成都四中", "成都九中", "绵阳一中"};

    public static String getTel() {

        return mPhone[new Random().nextInt(3)];
    }

    public static String getChineseName() {
        return mName[new Random().nextInt(3)];
    }

    public static String getRoad() {
        return mRoads[new Random().nextInt(3)];
    }

    public static String getSchoolName() {
        return mShcools[new Random().nextInt(3)];
    }
}

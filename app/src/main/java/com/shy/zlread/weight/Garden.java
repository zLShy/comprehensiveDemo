package com.shy.zlread.weight;

import com.shy.zlread.bean.Point;
import com.shy.zlread.utils.MathUtil;

/**
 * Created by zhangli on 2019/6/13.
 */

public class Garden {


    //创建一个随机的花朵
    public Bloom createRandomBloom(int x, int y) {
        //创建一个随机的花朵半径
        int radius = MathUtil.randomInt(Bloom.Options.minBloomRadius, Bloom.Options.maxBloomRadius);
        //创建一个随机的花朵颜色
        int color = MathUtil.randomrgba(Bloom.Options.minRedColor, Bloom.Options.maxRedColor
                , Bloom.Options.minGreenColor, Bloom.Options.maxGreenColor
                , Bloom.Options.minBlueColor, Bloom.Options.maxBlueColor
                , Bloom.Options.opacity);
        //创建随机的花朵中花瓣个数
        int petalCount = MathUtil.randomInt(Bloom.Options.minPetalCount, Bloom.Options.maxPetalCount);
        return createBloom(x, y, radius, color, petalCount);
    }

    //创建花朵
    public Bloom createBloom(int x, int y, int radius, int color, int petalCount) {
        return new Bloom(new Point(x, y), radius, color, petalCount);
    }
}

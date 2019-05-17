package com.shy.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangli on 2019/4/16.
 */
//Target代表位置 FIELD 属性 TYPE 类 等等...
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) //RunTIme运行时 class 编译时
public @interface ViewById {

    int value();
}

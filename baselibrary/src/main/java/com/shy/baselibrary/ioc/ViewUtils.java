package com.shy.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by zhangli on 2019/4/16.
 */

public class ViewUtils {

    public static void inJect(Activity activity) {

        inJect(new ViewFinder(activity),activity);
    }

    public static void inJect(View view) {

        inJect(new ViewFinder(view),view);
    }

    public static void inJect(View view,Object object) {

        inJect(new ViewFinder(view),object);
    }

    private static void inJect(ViewFinder viewFinder,Object object) {
        inJectFilter(viewFinder,object);
        inJectEvent(viewFinder,object);
    }

    private static void inJectEvent(ViewFinder viewFinder, Object object) {


    }

    private static void inJectFilter(ViewFinder viewFinder, Object object) {

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                int id = viewById.value();
                View view = viewFinder.findViewById(id);
                field.setAccessible(true);
                try {
                    field.set(object,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

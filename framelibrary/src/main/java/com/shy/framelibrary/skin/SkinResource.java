package com.shy.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by zhangli on 2019/6/14.
 */

public class SkinResource {

    private Resources mResources;
    private String mPackageName;
    private static final String TAG = "SkinResource";

    public SkinResource(Context context, String skinPath) {

        try {
            Resources superRes = context.getResources();
            AssetManager manager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.setAccessible(true);
            method.invoke(manager, Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator
                    + "bluetooth"
                    + File.separator
                    + "red.skin.apk");
            mResources = new Resources(manager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            // 获取skinPath包名
            Log.e(TAG,"PackageName--->"+mPackageName);
            mPackageName = context.getPackageManager().getPackageArchiveInfo(
                    skinPath, PackageManager.GET_ACTIVITIES).packageName;

//            int drawableid = resources.getIdentifier("img", "drawable", "com.shy.jnitest");
//            Drawable drawable = resources.getDrawable(drawableid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * g根据名字获取图片
     *
     * @param resName
     * @return
     */
    public Drawable getDrawable(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, "drawable", mPackageName);
            Drawable drawable = mResources.getDrawable(resId);
            return drawable;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据名称获取颜色
     *
     * @param resName
     * @return
     */
    public ColorStateList getColor(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, "drawable", mPackageName);
            ColorStateList color = mResources.getColorStateList(resId);
            return color;
        } catch (Exception e) {
            return null;
        }
    }
}

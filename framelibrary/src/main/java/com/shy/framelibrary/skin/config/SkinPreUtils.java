package com.shy.framelibrary.skin.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangli on 2019/7/2.
 */

public class SkinPreUtils {
    private static SkinPreUtils mInstance;
    private Context mContext;

    private SkinPreUtils(Context context) {

        this.mContext = context.getApplicationContext();
    }

    public static SkinPreUtils getInStance(Context context) {

        if (mInstance == null) {
            synchronized (SkinPreUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinPreUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 存放皮肤路径
     *
     * @param skinPath
     */
    public void saveSkinPath(String skinPath) {

        mContext.getSharedPreferences(SkinConfig.SKIN_INFO, Context.MODE_PRIVATE).edit().putString(SkinConfig.SKIN_PATH, skinPath).commit();
    }

    /**
     * 获取皮肤存放路径
     *
     * @return
     */
    public String getSkinPath() {
        return mContext.getSharedPreferences(SkinConfig.SKIN_INFO, Context.MODE_PRIVATE).getString(SkinConfig.SKIN_PATH, "");
    }

    /**
     * 清空皮肤路径
     */
    public void clearSkinPath() {
        saveSkinPath("");
    }

    public void savePackageName(String packageName) {
        mContext.getSharedPreferences(SkinConfig.SKIN_INFO, Context.MODE_PRIVATE).edit().putString(SkinConfig.SKIN_PACKAGENAME, packageName).commit();
    }

    public String getPackageName() {
        return mContext.getSharedPreferences(SkinConfig.SKIN_INFO, Context.MODE_PRIVATE).getString(SkinConfig.SKIN_PACKAGENAME, "");
    }
}

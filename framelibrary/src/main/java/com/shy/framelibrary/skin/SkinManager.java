package com.shy.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.shy.framelibrary.skin.attr.SkinView;
import com.shy.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangli on 2019/6/14.
 */

public class SkinManager {

    private static SkinManager mInstance;

    private static Map<Activity, List<SkinView>> mSkinViews = new ArrayMap<>();

    private Context mContext;
    private SkinResource mResource;

    static {
        mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {

        return mInstance;
    }

    public void init(Context context) {

        this.mContext = context.getApplicationContext();

        /**
         *判断文件路径是否存在
         */
        String currentPath = SkinPreUtils.getInStance(mContext).getSkinPath();
        File file = new File(currentPath);
        if (!file.exists()) {
            SkinPreUtils.getInStance(mContext).clearSkinPath();
            return;
        }

        /**
         * 判断apk包名
         */
        String packageName = context.getPackageManager().getPackageArchiveInfo(
                currentPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.getInStance(mContext).clearSkinPath();
            return;
        }
        mResource = new SkinResource(mContext, currentPath);
    }

    public int loadSkin(String path) {
        mResource = new SkinResource(mContext, path);

        Set<Activity> keys = mSkinViews.keySet();
        for (Activity key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }

        saveSkinStatus(path);
        return 0;
    }

    private void saveSkinStatus(String path) {
        SkinPreUtils.getInStance(mContext).saveSkinPath(path);
    }

    public int restoreDefalut() {

        return 0;
    }

    public List<SkinView> getSkinView(Activity activity) {

        return mSkinViews.get(activity);
    }

    public void regist(Activity activity, List<SkinView> skinViews) {

        mSkinViews.put(activity, skinViews);
    }

    public SkinResource getResource() {
        return mResource;
    }

    public void unRegister(Activity activity) {
        mSkinViews.remove(activity);
    }

    public void checkSkin(SkinView skinView) {
        String skinPath = SkinPreUtils.getInStance(mContext).getSkinPath();
        if (!TextUtils.isEmpty(skinPath)) {
            skinView.skin();
        }
    }
}

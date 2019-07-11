package com.shy.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.shy.framelibrary.skin.attr.SkinView;
import com.shy.framelibrary.skin.callback.ISkinChangeListener;
import com.shy.framelibrary.skin.config.SkinConfig;
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

    private static final String TAG = SkinManager.class.getSimpleName();
    private static SkinManager mInstance;

    private static Map<ISkinChangeListener, List<SkinView>> mSkinViews = new ArrayMap<>();

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
        /**
         *判断文件路径是否存在
         */
        String currentPath = SkinPreUtils.getInStance(mContext).getSkinPath();
        File file = new File(currentPath);
        if (!file.exists()) {
            SkinPreUtils.getInStance(mContext).clearSkinPath();
            return SkinConfig.SKIN_FILE_NOTEXITS;
        }

        /**
         * 判断apk包名
         */
        String packageName = mContext.getPackageManager().getPackageArchiveInfo(
                currentPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(packageName)) {
            return SkinConfig.SKIN_NOT_APK;
        }

        mResource = new SkinResource(mContext, path);

        changeSkin();

        saveSkinStatus(path);
        return 0;
    }

    private void saveSkinStatus(String path) {
        SkinPreUtils.getInStance(mContext).saveSkinPath(path);
    }

    public int restoreDefalut() {

        String currentPath = SkinPreUtils.getInStance(mContext).getSkinPath();
        Log.e(TAG, currentPath);
        if (TextUtils.isEmpty(currentPath)) {
            return SkinConfig.SKIN_FILE_NOTEXITS;
        }

        String apkPath = mContext.getPackageResourcePath();
        mResource = new SkinResource(mContext, apkPath);

        changeSkin();

        SkinPreUtils.getInStance(mContext).clearSkinPath();
        return SkinConfig.SKIN_SUCCESS;
    }

    public List<SkinView> getSkinView(ISkinChangeListener skinChangeListener) {

        return mSkinViews.get(skinChangeListener);
    }

    public void regist(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {

        mSkinViews.put(skinChangeListener, skinViews);
    }

    public SkinResource getResource() {
        return mResource;
    }

    public void unRegister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }

    public void checkSkin(SkinView skinView) {
        String skinPath = SkinPreUtils.getInStance(mContext).getSkinPath();
        if (!TextUtils.isEmpty(skinPath)) {
            skinView.skin();
        }
    }

    public void changeSkin() {
        Set<ISkinChangeListener> keys = mSkinViews.keySet();
        for (ISkinChangeListener key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
            key.changeSkin(mResource);
        }
    }
}

package com.shy.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;

import com.shy.framelibrary.skin.attr.SkinView;

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
        return 0;
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
}

package com.shy.zlread;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alipay.euler.andfix.patch.PatchManager;
import com.shy.zlread.utils.Tool;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zhangli on 2018/6/14.
 */

public class MyApplication extends Application {
    private static Context mContext;
    public static PatchManager mPatchManager;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mPatchManager = new PatchManager(this);
        mPatchManager.init(Tool.getLocalVersionName(this));
        mPatchManager.loadPatch();
        LeakCanary.install(this);
    }

    public static Context getContext() {
        return mContext;
    }

}

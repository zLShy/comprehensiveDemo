package com.shy.zlread;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.shy.framelibrary.skin.SkinManager;
import com.shy.zlread.utils.CrashHandler;
import com.shy.zlread.utils.Tool;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.mmkv.MMKV;
import com.zl.greendao.gen.DaoMaster;
import com.zl.greendao.gen.DaoSession;

import io.rong.imkit.RongIM;


/**
 * Created by zhangli on 2018/6/14.
 */

public class MyApplication extends Application {
    private final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInatance;
    //    public static PatchManager mPatchManager;
    private RefWatcher mRefWatcher;
    private static DaoSession daoSession;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
//        initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInatance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
//        mPatchManager = new PatchManager(this);
//        mPatchManager.init(Tool.getLocalVersionName(this));
//        mPatchManager.loadPatch();

        SkinManager.getInstance().init(this);

        SophixManager.getInstance().queryAndLoadNewPatch();
//        初始化融云
//        RongIM.init(this);
        //初始化异常处理
        CrashHandler.getInstance().init(this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//
//            return;
//        }
//        LeakCanary.install(this);
        mRefWatcher = setupLeakCanary();
//        initGreenDao();


    }

    private RefWatcher setupLeakCanary() {

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        return leakApplication.mRefWatcher;
    }


    public static MyApplication getInatance() {

        return mInatance;
    }

    private void initGreenDao() {
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(this, "zl.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(null, null, null)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}

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

import com.alipay.euler.andfix.patch.PatchManager;
import com.shy.framelibrary.skin.SkinManager;
import com.shy.zlread.utils.Tool;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.mmkv.MMKV;
import com.yuntongxun.ecsdk.ECDevice;
import com.zl.greendao.gen.DaoMaster;
import com.zl.greendao.gen.DaoSession;


/**
 * Created by zhangli on 2018/6/14.
 */

public class MyApplication extends Application {
    private final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInatance;
    public static PatchManager mPatchManager;
    private RefWatcher mRefWatcher;
    private static DaoSession daoSession;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInatance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mPatchManager = new PatchManager(this);
        mPatchManager.init(Tool.getLocalVersionName(this));
        mPatchManager.loadPatch();

        SkinManager.getInstance().init(this);

        Thread.setDefaultUncaughtExceptionHandler(handler);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//
//            return;
//        }
//        LeakCanary.install(this);
        mRefWatcher = setupLeakCanary();
        initGreenDao();


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

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            restartApp(); //发生崩溃异常时,重启应用
        }
    };

    private void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        @SuppressLint("WrongConstant")
        PendingIntent restartIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        //退出程序
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                restartIntent); // 1秒钟后重启应用

        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        android.os.Process.killProcess(android.os.Process.myPid());
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
}

package com.shy.zlread;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shy.framelibrary.skin.SkinManager;
import com.shy.framelibrary.skin.SkinActivity;
import com.shy.framelibrary.skin.SkinResource;
import com.shy.zlread.bean.User;
import com.shy.zlread.httpRequest.ApiMethods;
import com.shy.zlread.httpRequest.CallBacks;
import com.shy.zlread.httpRequest.ProgressObserver;
import com.shy.zlread.service.MessageService;
import com.shy.zlread.weight.BubbleView;
import com.shy.zlread.weight.FlowerLoadingView;
import com.tencent.mmkv.MMKV;
import com.zl.map.Utils.BaseActicity;
import com.zl.map.Utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cz.msebera.android.httpclient.Header;

public class LoadingActivity extends BaseActicity {

    private static final String TAG = LoadingActivity.class.getSimpleName();
    private FlowerLoadingView mFlowerLoadingView;
    private BubbleView mBubbleView;
    private TextView tv;
    private ImageView iv;
    private Resources mResources;
    private UserAidl mUserAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mUserAidl = UserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void doSDcardPermission() {
        Resources superRes = getResources();
        try {
//            AssetManager manager = AssetManager.class.newInstance();
//            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
////            method.setAccessible(true);
//            method.invoke(manager, getPackageResourcePath());
//            Resources resources = new Resources(manager, superRes.getDisplayMetrics(), superRes.getConfiguration());

//            int drawableid = getResources().getIdentifier("pl_blue", "drawable", "com.shy.zlread");
//            Drawable drawable = getResources().getDrawable(drawableid);
//            iv.setImageDrawable(drawable);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator
                    + "bluetooth"
                    + File.separator
                    + "red.skin.apk";
            int result = SkinManager.getInstance().loadSkin(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchSkin(View view) {

//        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            doSDcardPermission();
//        } else {
//            requestPermission(Constants.Companion.getWRITE_READ_EXTERNAL_CODE(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }

        try {
            Toast.makeText(this, mUserAidl.getuserName(), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void restore(View view) {
//        int result = SkinManager.getInstance().restoreDefalut();
        try {
            Toast.makeText(this, mUserAidl.getpassword(), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void intentActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, LoadingActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeSkin(SkinResource skinResource) {
        super.changeSkin(skinResource);
    }

    @Override
    public void initDate() {
        startService(new Intent(this, MessageService.class));

        Intent intent = new Intent(this, MessageService.class);
//        intent.setAction("com.zl.shy");
//        intent.setPackage("com.shy.ndkdemo");
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

//        new ApiMethods().getTodayNews(new ProgressObserver(new CallBacks() {
//            @Override
//            public void onSuccess(@NotNull Object any) {
//                Log.e(TAG,any.toString());
//            }
//
//            @Override
//            public void onFailure(@NotNull Object any) {
//                Log.e(TAG,"fails");
//            }
//        }));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://api.douban.com/v2/movie/top250?start=25&count=25", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String body = new String(responseBody);
                Log.e(TAG,body);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.e(TAG,"AsyncHttpClient Fails");
            }
        });
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_loading);
    }

    @Override
    public void initViews() {
        //        tv = (TextView) findViewById(R.id.bubble_tv);
        iv = (ImageView) findViewById(R.id.image_view);
        //        mFlowerLoadingView = (FlowerLoadingView) findViewById(R.id.flower_loading);
//        mFlowerLoadingView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFlowerLoadingView.setVisibility(View.GONE);
//                mFlowerLoadingView.setAnimStop(true);
//            }
//        });


//        MessageBubbleView.attchView(tv, new BubbleOnTouchListener.disapperListener() {
//            @Override
//            public void disMiss(View view) {
//
//            }
////            @Override
////            public void showView() {
////                tv.setVisibility(View.VISIBLE);
////            }
//        });

        MMKV mKv = MMKV.defaultMMKV();
        boolean isSuccess = mKv.encode("test", "mmkvTest");
        String test = mKv.decodeString("test", "");
        Log.e(TAG, isSuccess + "=====" + test);

//        MMKV mmkv = MMKV.mmkvWithID("",MMKV.MULTI_PROCESS_MODE);
//        SharedPreferences preferences = getSharedPreferences("",MODE_PRIVATE);
//        mmkv.importFromSharedPreferences(preferences);
//        preferences.edit().clear().commit();
    }
}

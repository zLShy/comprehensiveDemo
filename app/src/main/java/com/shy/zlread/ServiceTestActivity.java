package com.shy.zlread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shy.zlread.bean.RongToken;
import com.shy.zlread.httpRequest.ApiMethods;
import com.shy.zlread.httpRequest.CallBacks;
import com.shy.zlread.httpRequest.ProgressObserver;
import com.shy.zlread.httpRequest.RetrifitUtils;
import com.shy.zlread.service.AwakenService;
import com.shy.zlread.service.GuardService;
import com.shy.zlread.service.JobWaekUpService;
import com.shy.zlread.utils.RongCloudeUtils;
import com.shy.zlread.utils.ShaUtils;
import com.zl.map.Utils.BaseActicity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import banner.BannerAdapter;
import banner.BannerView;
import banner.BannerViewPager;
import cz.msebera.android.httpclient.Header;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ServiceTestActivity extends BaseActicity {

    private Handler mHandler;
    private static final String TAG = ServiceTestActivity.class.getSimpleName();
    private BannerView mBannerView;
    List<String> mImageList = new ArrayList<>();

    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(ServiceTestActivity.this, "mainHander tx ThreadHandler", Toast.LENGTH_SHORT).show();

            mHandler.sendEmptyMessageDelayed(msg.what, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_service_test);
    }

    @Override
    public void initViews() {

        mBannerView = (BannerView) findViewById(R.id.banner_view);
        mImageList.add("http://cdn.cegzm.com/device/a.jpg");
        mImageList.add("http://cdn.cegzm.com/device/b.jpg");

        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView bannerview = new ImageView(ServiceTestActivity.this);
                Glide.with(ServiceTestActivity.this).load(mImageList.get(position)).into(bannerview);
                return bannerview;
            }

            @Override
            public int getCount() {
                return mImageList.size();
            }

            @Override
            public String getBannerDesc(int position) {
                String desc = "";
                if (position == 0) {
                    desc = "123";
                } else {
                    desc = "234";
                }
                return desc;
            }
        });
        mBannerView.startRoll();
    }

    @Override
    public void initDate() {

        Log.e(TAG, ShaUtils.sHA1(this));
        startService(new Intent(this, AwakenService.class));
        startService(new Intent(this, GuardService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //必须大于5.0
            startService(new Intent(this, JobWaekUpService.class));
        }
    }


    public void login(View view) {

        mHandler.sendEmptyMessageDelayed(1, 1000);
//        requestRongToken("15982056336", "zl", "");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void requestRongToken(String userId, String userName, String portraitUrl) {
        if (TextUtils.isEmpty(RongCloudeUtils.getInstance(this).getToken())) {
            Map<String, String> maps = RongCloudeUtils.getInstance(this).sha1();
            RetrifitUtils.Companion.setBaseUrl("http://api-cn.ronghub.com/user/");
            new ApiMethods().getRongToken(new ProgressObserver(new CallBacks() {
                @Override
                public void onSuccess(@NotNull Response<ResponseBody> any) {
                    Log.e(TAG, "code====" + any.code());
                    try {
                        String body = new String(any.body().bytes());
                        RongToken tokenBean = new Gson().fromJson(body, RongToken.class);
                        RongCloudeUtils.getInstance(ServiceTestActivity.this).saveToken(tokenBean.getToken());
                        Log.e(TAG, tokenBean.toString());
                        loginRongTx(tokenBean.getToken());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NotNull Object any) {

                }
            }), maps.get("app_key"), maps.get("Timestamp"), maps.get("Nonce"), maps.get("Signature"), maps.get("Content-Type"), userId, userName, portraitUrl);

        } else {
            Log.e(TAG, RongCloudeUtils.getInstance(this).getToken());
            loginRongTx(RongCloudeUtils.getInstance(ServiceTestActivity.this).getToken());
        }
    }


    private void loginRongTx(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "--onSuccess" + s);
                startActivity(new Intent(ServiceTestActivity.this, ImActivity.class));
                finish();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    public void chileThread() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        mHandler = new Handler(Looper.myLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                Log.e(TAG, "threadHandler----");
                                mainHandler.sendEmptyMessageDelayed(msg.what, 2000);
                            }
                        };
                        Looper.loop();
                    }
                }
        ).start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}

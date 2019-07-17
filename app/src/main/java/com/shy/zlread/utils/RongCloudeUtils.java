package com.shy.zlread.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangli on 2019/7/11.
 */

public class RongCloudeUtils {
    public static RongCloudeUtils mInstance;
    public static String APP_KEY = "8luwapkv8biel"; //开发者平台分配的 App Key。
    public static String APP_SECRET = "25FrfJIqOz";
    public static String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private Context mContext;

    private RongCloudeUtils(Context context) {

        mContext = context.getApplicationContext();
    }

    public static RongCloudeUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (RongCloudeUtils.class) {
                if (mInstance == null) {
                    mInstance = new RongCloudeUtils(context);
                }
            }
        }

        return mInstance;
    }

    public Map<String, String> sha1() {
        Map<String, String> maps = new HashMap<>();
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        String data = APP_SECRET + Nonce + Timestamp;
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for (int i = 0; i < bits.length; i++) {
                int a = bits[i];
                if (a < 0) a += 256;
                if (a < 16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        } catch (Exception e) {

        }
        maps.put("app_key", APP_KEY);
        maps.put("app_secret", APP_SECRET);
        maps.put("Timestamp", Timestamp);
        maps.put("Nonce", Nonce);
        maps.put("Signature", buf.toString());
        maps.put("Content-Type", CONTENT_TYPE);
        return maps;
    }

    public void saveToken(String mToken) {
        mContext.getSharedPreferences("rongInfo",Context.MODE_PRIVATE).edit().putString("token",mToken).commit();
    }

    public String getToken() {
        return mContext.getSharedPreferences("rongInfo",Context.MODE_PRIVATE).getString("token","");
    }
}


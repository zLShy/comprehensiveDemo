package com.shy.zlread.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;


public class Tool {

    public static void showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShortMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getString(TextView view) {
        return view.getText().toString().trim();
    }

    public static String getString(EditText view) {
        return view.getText().toString().trim();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWinWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int winwidth = dm.widthPixels;
        return winwidth;
    }

    /**
     * 获取屏幕高度
     */
    public static int getWinHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int winheight = dm.heightPixels;
        return winheight;
    }

    public static int byteToInt2(byte[] b) {

        int mask=0xff;
        int temp=0;
        int n=0;
        for(int i=0;i<b.length;i++){
            n<<=8;
            temp=b[i]&mask;
            n|=temp;
        }
        return n;
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 压缩图片，处理某些手机拍照角度旋转的问题
     */
    public static String compressImage(String filePath, String fileName, int q, File fileParent) throws FileNotFoundException {

        Bitmap bm = getSmallBitmap(filePath);

        int degree = readPictureDegree(filePath);

        if (degree != 0) {//旋转照片角度
            bm = rotateBitmap(bm, degree);
        }

        File imageDir = fileParent;

        File outputFile = new File(imageDir, fileName);

        FileOutputStream out = new FileOutputStream(outputFile);

        bm.compress(Bitmap.CompressFormat.PNG, q, out);


        bm.recycle();

        System.gc();

        return outputFile.getPath();
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }


    /**
     * 根据时间段，设置屏幕显示亮度
     */
    public static void isCurrentInTimeScope1(Activity context) {
        Calendar cal = Calendar.getInstance();// 当前日期
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
        int minute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        final int start = 10 * 60 + 0;// 起始时间 17:20的分钟数
        final int end = 17 * 60;// 结束时间 19:00的分钟数
        //10点到17点
        if (minuteOfDay >= start && minuteOfDay <= end) {
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = Float.parseFloat(1 + "");//1f
            context.getWindow().setAttributes(lp);

        } else if (minuteOfDay >= 7 * 60 && minuteOfDay <= 10 * 60) {
            //7点到10点
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = Float.parseFloat(0.8f + "");//1f
            context.getWindow().setAttributes(lp);
        } else if (minuteOfDay >= 17 * 60 && minuteOfDay <= 20 * 60) {
            //17点到20点
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = Float.parseFloat(0.3f + "");//1f  旧版0.8
            context.getWindow().setAttributes(lp);
        } else {
            //其他时间段
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = Float.parseFloat(0.2f + "");//1f  旧版0.5
            context.getWindow().setAttributes(lp);

        }
    }


    /**
     *  获取系统版本
     * @param ctx 上下文
     * @return 系统版本号
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.e("wh", "本软件的版本名称：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     *  获取系统版本
     * @param ctx 上下文
     * @return versionCode
     */
    public static int getLocalVersionCode(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取手机IMSI
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI(Context context){
        try {
            TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            String imsi=telephonyManager.getSubscriberId();
            if(null==imsi){
                imsi="";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 获取手机IMSI
     */
    @SuppressLint("MissingPermission")
    public static String getIccid(Context context){
        try {
            TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            String iccid=telephonyManager.getSimSerialNumber();
            if(null==iccid){
                iccid="";
            }
            return iccid;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //获得系统可用内存信息
    private String getSystemAvaialbeMemorySize(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;

        //字符类型转换
        String availMemStr = formateFileSize(memSize,context);

        return availMemStr;

    }
    public static String formateFileSize(long size,Context context){
        return Formatter.formatFileSize(context, size);
    }


    /**
     * 获取系统总内存,返回字节单位为KB
     * @return 系统总内存
     */
    public static long getTotalMemory() {
        long totalMemorySize = 0;
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            //将非数字的字符替换为空
            totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalMemorySize;
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}  

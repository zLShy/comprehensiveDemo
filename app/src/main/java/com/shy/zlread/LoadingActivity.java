package com.shy.zlread;

import android.Manifest;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shy.framelibrary.skin.SkinManager;
import com.shy.framelibrary.skin.SkinActivity;
import com.shy.zlread.weight.BubbleView;
import com.shy.zlread.weight.FlowerLoadingView;
import com.zl.map.Utils.BaseActicity;
import com.zl.map.Utils.Constants;

import java.io.File;

public class LoadingActivity extends BaseActicity {

    private FlowerLoadingView mFlowerLoadingView;
    private BubbleView mBubbleView;
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                if (name.equals("Button")){
//                    TextView textView = new TextView(LoadingActivity.this);
//                    textView.setText("拦截美女");
//                    return textView;
//                }
//
//                return null;
//            }
//
//            @Override
//            public View onCreateView(String name, Context context, AttributeSet attrs) {
//                return null;
//            }
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        tv = (TextView) findViewById(R.id.bubble_tv);
        iv = (ImageView) findViewById(R.id.image_view);

//        Resources resources = new Resources();
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


    }

    @Override
    public void doSDcardPermission() {
//        Resources superRes = getResources();
        try {
//            AssetManager manager = AssetManager.class.newInstance();
//            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
//            method.setAccessible(true);
//            method.invoke(manager, Environment.getExternalStorageDirectory().getAbsolutePath()
//                    + File.separator
//                    + "bluetooth"
//                    + File.separator
//                    + "red.skin.apk");
//            Resources resources = new Resources(manager, superRes.getDisplayMetrics(), superRes.getConfiguration());
//
//            int drawableid = resources.getIdentifier("img", "drawable", "com.shy.jnitest");
//            Drawable drawable = resources.getDrawable(drawableid);
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

        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            doSDcardPermission();
        } else {
            requestPermission(Constants.Companion.getWRITE_READ_EXTERNAL_CODE(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


    }

    public void restore(View view) {
        int result = SkinManager.getInstance().restoreDefalut();
    }
}

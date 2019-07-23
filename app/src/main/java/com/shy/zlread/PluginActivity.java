package com.shy.zlread;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zl.map.Utils.BaseActicity;
import com.zl.map.Utils.Constants;

import java.io.File;
import java.io.IOException;

import http.EnginCallback;
import http.HttpUtils;

public class PluginActivity extends BaseActicity {

    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initDate() {
        HttpUtils.with(this).url("").addParame("", "").post().execute(new EnginCallback() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess(String s) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        s.toString();
                    }
                });
            }
        });

        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            doSDcardPermission();
        } else {
            requestPermission(Constants.Companion.getWRITE_READ_EXTERNAL_CODE(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_plugin2);
    }

    @Override
    public void initViews() {

        this.mBtn = (Button) findViewById(R.id.plugin_btn);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PluginActivity.this, 2 / 0 + "测试效果", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void doSDcardPermission() {
        super.doSDcardPermission();
        File patchFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");

//        if (patchFile.exists()) {
//            try {
//                MyApplication.mPatchManager.addPatch(patchFile.getAbsolutePath());
//                Toast.makeText(PluginActivity.this, "fix success", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                Toast.makeText(PluginActivity.this, "fix fails", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
    }
}

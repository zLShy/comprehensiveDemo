package com.shy.zlread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shy.zlread.service.AwakenService;
import com.zl.map.Utils.BaseActicity;

public class ServiceTestActivity extends BaseActicity {


    private static final String TAG = ServiceTestActivity.class.getSimpleName();

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

    }

    @Override
    public void initDate() {


    }


    public void login(View view) {
        startService(new Intent(this, AwakenService.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

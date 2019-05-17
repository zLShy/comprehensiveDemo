package com.shy.zlread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shy.zlread.bean.TestBean;
import com.shy.zlread.weight.CircleView;
import com.shy.zlread.weight.FlowerLoadingView;

public class LoadingActivity extends AppCompatActivity {

    private FlowerLoadingView mFlowerLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

//        mFlowerLoadingView = (FlowerLoadingView) findViewById(R.id.flower_loading);
//        mFlowerLoadingView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFlowerLoadingView.setVisibility(View.GONE);
//                mFlowerLoadingView.setAnimStop(true);
//            }
//        });
    }
}

package com.shy.zlread;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shy.zlread.bean.TestBean;
import com.shy.zlread.utils.BubbleOnTouchListener;
import com.shy.zlread.weight.BubbleView;
import com.shy.zlread.weight.CircleView;
import com.shy.zlread.weight.FlowerLoadingView;
import com.shy.zlread.weight.MessageBubbleView;

public class LoadingActivity extends AppCompatActivity {

    private FlowerLoadingView mFlowerLoadingView;
    private BubbleView mBubbleView;
    private TextView tv;

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

        tv = (TextView) findViewById(R.id.bubble_tv);
//        mFlowerLoadingView = (FlowerLoadingView) findViewById(R.id.flower_loading);
//        mFlowerLoadingView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFlowerLoadingView.setVisibility(View.GONE);
//                mFlowerLoadingView.setAnimStop(true);
//            }
//        });

        MessageBubbleView.attchView(tv, new BubbleOnTouchListener.disapperListener() {
            @Override
            public void disMiss(View view) {

            }
//            @Override
//            public void showView() {
//                tv.setVisibility(View.VISIBLE);
//            }
        });

    }
}

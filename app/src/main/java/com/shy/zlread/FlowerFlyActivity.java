package com.shy.zlread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.shy.zlread.utils.BubbleOnTouchListener;
import com.shy.zlread.weight.BubbleView;
import com.shy.zlread.weight.FlowerLoadingView;
import com.shy.zlread.weight.FlowerLoveView;
import com.shy.zlread.weight.MessageBubbleView;

public class FlowerFlyActivity extends AppCompatActivity {

    private FlowerLoveView mFlowerLoveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowerfly);

        mFlowerLoveView = (FlowerLoveView) findViewById(R.id.flower_love_view);
    }

    public void add(View view) {

        mFlowerLoveView.addLoveView();
    }
}

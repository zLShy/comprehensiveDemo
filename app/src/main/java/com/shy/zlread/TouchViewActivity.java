package com.shy.zlread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.shy.zlread.weight.TouchView;

public class TouchViewActivity extends AppCompatActivity {

    private TouchView mTouchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_view);
        this.mTouchView = (TouchView) findViewById(R.id.touch_view);

        this.mTouchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAG", "TouchListener-->down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("TAG", "TouchListener-->move");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("TAG", "TouchListener-->up");
                        break;
                }
                return true;
            }
        });

        this.mTouchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("TAG", "ClickListener --->click");
            }
        });
    }
}

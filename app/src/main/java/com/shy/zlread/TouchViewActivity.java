package com.shy.zlread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.shy.zlread.weight.TouchView;

public class TouchViewActivity extends AppCompatActivity {

    private TouchView mTouchView;
    private ImageView mTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_view);
        this.mTouchView = (TouchView) findViewById(R.id.touch_view);
        mTouch = (ImageView) findViewById(R.id.touch_iv);

        this.mTouchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAG", "VIEWGROUP-->TouchListener-->down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("TAG", "VIEWGROUP-->TouchListener-->move");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("TAG", "VIEWGROUP-->TouchListener-->up");
                        break;
                }
                return false;
            }
        });

        this.mTouchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("TAG", "VIEWGROUP-->ClickListener --->click");
            }
        });

        this.mTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAG", "VIEW-->TouchListener-->down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("TAG", "VIEW-->TouchListener-->move");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("TAG", "VIEW-->TouchListener-->up");
                        break;
                }
                return false;
            }
        });

        mTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "VIEW-->ClickListener --->click");
            }
        });
    }
}

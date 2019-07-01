package com.shy.zlread.weight;

import android.content.Context;
import android.graphics.LinearGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by zhangli on 2019/5/30.
 */

public class TouchView extends LinearLayout {
    public TouchView(Context context) {
        this(context, null);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG", "VIEWGROUP-->TouchEvent-->down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("TAG", "VIEWGROUP-->TouchEvent-->move");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG", "VIEWGROUP-->TouchEvent-->up");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            return true;
//        }
        return super.onInterceptTouchEvent(ev);
    }
}

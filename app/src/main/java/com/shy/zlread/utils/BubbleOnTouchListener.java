package com.shy.zlread.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.shy.zlread.weight.BubbleView;
import com.shy.zlread.weight.MessageBubbleView;

/**
 * Created by zhangli on 2019/5/21.
 */

public class BubbleOnTouchListener implements View.OnTouchListener {
    private View mStataicView;
    private WindowManager mWindowManager;
    private MessageBubbleView mBubbleView;
    private WindowManager.LayoutParams mParams;
    private Context mContext;

    public BubbleOnTouchListener(View view, Context context) {
        this.mStataicView = view;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mBubbleView = new MessageBubbleView(context);
        mParams = new WindowManager.LayoutParams();
        this.mContext = context;
        mParams.format = PixelFormat.TRANSPARENT;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStataicView.setVisibility(View.INVISIBLE);
                mWindowManager.addView(mBubbleView, mParams);
                mBubbleView.initPoint(event.getRawX(), event.getRawY() - BubbleUtils.getStatusBarHeight(mContext));
                break;
            case MotionEvent.ACTION_UP:
                mBubbleView.actionUp();
                break;
            case MotionEvent.ACTION_MOVE:
                mBubbleView.updateDragPoint(event.getRawX(), event.getRawY() - BubbleUtils.getStatusBarHeight(mContext));
                break;
        }
        return true;
    }
}

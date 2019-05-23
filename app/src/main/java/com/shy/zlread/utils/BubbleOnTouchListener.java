package com.shy.zlread.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shy.zlread.R;
import com.shy.zlread.weight.BubbleView;
import com.shy.zlread.weight.MessageBubbleView;

/**
 * Created by zhangli on 2019/5/21.
 */

public class BubbleOnTouchListener implements View.OnTouchListener, MessageBubbleView.MessageBubbleListener {
    private View mStataicView;
    private WindowManager mWindowManager;
    private MessageBubbleView mBubbleView;
    private WindowManager.LayoutParams mParams;
    private Context mContext;
    private disapperListener mDisapperListener;
    private FrameLayout mBombFram;
    private ImageView mBombImage;

    public BubbleOnTouchListener(disapperListener listener, View view, Context context) {
        this.mStataicView = view;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mBubbleView = new MessageBubbleView(context);
        mParams = new WindowManager.LayoutParams();
        this.mContext = context;
        this.mBombFram = new FrameLayout(mContext);
        this.mBombImage = new ImageView(mContext);
        mParams.format = PixelFormat.TRANSPARENT;
        mBombImage.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBombFram.addView(mBombImage);
        this.mDisapperListener = listener;
        mBubbleView.setmListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStataicView.setVisibility(View.INVISIBLE);
                mWindowManager.addView(mBubbleView, mParams);
                Bitmap bitmap = getBitmap(v);
                int[] loction = new int[2];
                mStataicView.getLocationOnScreen(loction);
                mBubbleView.initPoint(loction[0] + mStataicView.getWidth() / 2, loction[1] + mStataicView.getHeight() / 2 - BubbleUtils.getStatusBarHeight(mContext));
                mBubbleView.setBitmap(bitmap);
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

    private Bitmap getBitmap(View v) {
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }

    @Override
    public void restore() {
        mWindowManager.removeView(mBubbleView);
        mStataicView.setVisibility(View.VISIBLE);

    }

    @Override
    public void dismiss(PointF pointF) {

        mWindowManager.removeView(mBubbleView);
        mWindowManager.addView(mBombFram, mParams);
        mBombImage.setBackgroundResource(R.drawable.anim_bubble_pop);

        AnimationDrawable drawable = (AnimationDrawable) mBombImage.getBackground();
        mBombImage.setX(pointF.x - drawable.getIntrinsicWidth() / 2);
        mBombImage.setY(pointF.y - drawable.getIntrinsicHeight() / 2);

        drawable.start();
        mBombImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWindowManager.removeView(mBombFram);
                mStataicView.setVisibility(View.VISIBLE);
                mDisapperListener.disMiss(mStataicView);
            }
        },getAnimtorTime(drawable));
    }

    private long getAnimtorTime(AnimationDrawable drawable) {
        int totleFrame = drawable.getNumberOfFrames();
        long time = 0;
        for (int i=0;i<totleFrame;i++) {
            time += drawable.getDuration(i);
        }
        return time;
    }

    public interface disapperListener {
        void disMiss(View view);

    }
}

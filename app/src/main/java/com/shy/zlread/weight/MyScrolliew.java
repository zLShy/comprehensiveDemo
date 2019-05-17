package com.shy.zlread.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by zhangli on 2019/4/16.
 */

public class MyScrolliew extends ScrollView {
    public MyScrolliew(Context context) {
        super(context);
    }

    public MyScrolliew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrolliew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.srollListener(l,t,oldl,oldt);
        }
    }

    public interface onScrollChangeListener{
        public void srollListener(int l, int t, int oldl, int oldt);
    }

    private onScrollChangeListener mListener;
    public void setOnScrollListener(onScrollChangeListener listener) {
        this.mListener = listener;
    }
}

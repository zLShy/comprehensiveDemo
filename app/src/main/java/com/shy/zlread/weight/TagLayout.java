package com.shy.zlread.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangli on 2019/3/14.
 */

public class TagLayout extends ViewGroup {
    List<List<View>> mChildViews = new ArrayList<>();
    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 循环测量子View宽高，计算服务局宽高 mChildViews.clear();清空view 避免重复调用onMeasure方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mChildViews.clear();


        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mLeft = getPaddingLeft();
        int childCount = getChildCount();
        int height = getPaddingTop() + getPaddingBottom();
        int maxHeight = 0;
        ArrayList<View> mChilds = new ArrayList<>();
        for (int i = 0;i< childCount;i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //获取margin值 需要复写generateLayoutParams方法
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            if (child.getMeasuredWidth() + child.getPaddingLeft()+mLeft > width) {
                mLeft = getPaddingLeft()+ child.getMeasuredWidth();
//                Log.e("TGA","childwidth=="+child.getMeasuredWidth());
                mChildViews.add(mChilds);
                mChilds = new ArrayList<>();
                height += maxHeight;
            }else {
                mLeft += child.getMeasuredWidth() + child.getPaddingLeft();
                maxHeight = Math.max(maxHeight,child.getMeasuredHeight());
                mChilds.add(child);
            }
        }

        height += maxHeight;
        if (mChildViews.size() == 0) {
            mChildViews.add(mChilds);
        }
        setMeasuredDimension(width,height);

    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//    }

    /**
     * 子View位置摆放
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int top = 0;
        int bottom = 0;
        int maxHeight = 0;
        for (List<View> mChildView : mChildViews) {
            int left = getPaddingLeft();
            int right = 0;
            for (View childView : mChildView) {
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                right += childView.getMeasuredWidth();
                bottom = top+childView.getMeasuredHeight();

                childView.layout(left,top,right,bottom);
                Log.e("TGA","left=>"+left+"=top=>"+top+"=right=>"+right+"=bottom=>"+bottom);
                maxHeight = Math.max(maxHeight,childView.getMeasuredHeight());
                left += childView.getMeasuredWidth();
            }

            top +=  maxHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

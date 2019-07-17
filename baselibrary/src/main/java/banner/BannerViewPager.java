package banner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

/**
 * Created by zhangli on 2019/7/15.
 */

public class BannerViewPager extends ViewPager {
    private BannerAdapter mAdapter;
    private final int SCROLL_MSG = 0x001;
    private int mCutDownTime = 3500;
    private BannerScroller mScroller;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurrentItem(getCurrentItem() + 1);
            startRoll();
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            //第一个参数 当前属性类 第二个参数设置的值
            mScroller = new BannerScroller(context);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2销毁Handler 内存泄漏解决
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;

    }

    public void setScrollerDuration(int duration) {
        mScroller.setScrollerDuration(duration);
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        setAdapter(new BannerViewAdapter());
    }

    public void startRoll() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    private class BannerViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View bannerItemView = mAdapter.getView(position);
            container.addView(bannerItemView);
            return bannerItemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

}

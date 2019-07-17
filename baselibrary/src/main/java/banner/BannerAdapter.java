package banner;

import android.view.View;

/**
 * Created by zhangli on 2019/7/15.
 */

public abstract class BannerAdapter {
    /**
     * 根据位置获取viewpager子view
     * @param position
     * @return
     */
    public abstract View getView(int position);
}

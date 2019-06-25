package com.shy.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by zhangli on 2019/6/14.
 */

public class SkinAttr {

    private String mResName;
    private SkinType mType;

    public SkinAttr(SkinType skinType, String resName) {
        this.mResName = resName;
        this.mType = skinType;
    }

    public void skin(View view) {

        mType.skin(view, mResName);
    }

}

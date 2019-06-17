package com.shy.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by zhangli on 2019/6/14.
 */

public enum SkinType {
    TEXT_COLOR("textcolor"), BACKGROUND("background"), SRC("src");

    private String mResName;

    private SkinType(String resName) {

        this.mResName = resName;
    }

    public void skin(View view, String mResName) {

    }
}

package com.shy.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by zhangli on 2019/6/14.
 */

public enum SkinType {
    TEXT_COLOR("textcolor") {
        @Override
        public void skin(View view, String mResName) {

        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String mResName) {

        }
    }, SRC("src") {
        @Override
        public void skin(View view, String mResName) {

        }
    };

    private String mResName;

    SkinType(String resName) {

        this.mResName = resName;
    }

    public abstract void skin(View view, String mResName);

    public String  getName() {
        return mResName;
    }
}

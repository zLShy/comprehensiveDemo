package com.shy.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by zhangli on 2019/6/14.
 */

public class SkinView {

    private View mView;
    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mAttrs = skinAttrs;
        this.mView = view;
    }

    public void skin() {
        for (SkinAttr attr : mAttrs) {
            attr.skin(mView);
        }
    }

}

package com.shy.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shy.framelibrary.skin.SkinManager;
import com.shy.framelibrary.skin.SkinResource;

/**
 * Created by zhangli on 2019/6/14.
 */

public enum SkinType {
    TEXT_COLOR("textcolor") {
        @Override
        public void skin(View view, String mResName) {

            SkinResource resource = getSKinResource();
            ColorStateList color = resource.getColor(mResName);
            if (color == null) {
                return;
            }
            TextView textView = (TextView) view;
            textView.setTextColor(color);
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String mResName) {

            SkinResource resource = getSKinResource();
            Drawable drawable = resource.getDrawable(mResName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setBackgroundDrawable(drawable);
                return;
            }
            ColorStateList color = resource.getColor(mResName);
            if (color != null) {
                view.setBackgroundColor(color.getDefaultColor());
            }

        }
    }, SRC("src") {
        @Override
        public void skin(View view, String mResName) {
            SkinResource resource = getSKinResource();
            Drawable drawable = resource.getDrawable(mResName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
                return;
            }
        }
    };

    private String mResName;

    SkinType(String resName) {

        this.mResName = resName;
    }

    public abstract void skin(View view, String mResName);

    public String getName() {
        return mResName;
    }


    public SkinResource getSKinResource() {
        return SkinManager.getInstance().getResource();
    }
}

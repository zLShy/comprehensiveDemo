package com.shy.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.shy.framelibrary.skin.SkinManager;
import com.shy.framelibrary.skin.attr.SkinAttr;
import com.shy.framelibrary.skin.attr.SkinView;
import com.shy.framelibrary.skin.config.SkinPreUtils;
import com.shy.framelibrary.skin.support.SkinAppCompatViewInflater;
import com.shy.framelibrary.skin.support.SkinSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangli on 2019/6/17.
 */

public class SkinActivity extends AppCompatActivity {

    private SkinAppCompatViewInflater mAppCompatViewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = createView(parent, name, context, attrs);

        if (view != null) {
            List<SkinAttr> skinAttrs = SkinSupport.getSkinAttrs(context, attrs);
            SkinView skinView = new SkinView(view, skinAttrs);
            //交给SkinManager管理
            managerSkinView(skinView);

            // 管理皮肤
            SkinManager.getInstance().checkSkin(skinView);
        }

        return view;
    }

    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinView(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().regist(this, skinViews);
        }
        skinViews.add(skinView);
    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        // We only want the View to inherit it's context if we're running pre-v21
        final boolean inheritContext = isPre21 && true && shouldInheritContext((ViewParent) parent);

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }


//    private boolean shouldInheritContext(ViewParent parent) {
//        if (parent == null) {
//            // The initial parent is null so just return false
//            return false;
//        }
//        while (true) {
//            if (parent == null) {
//                // Bingo. We've hit a view which has a null parent before being terminated from
//                // the loop. This is (most probably) because it's the root view in an inflation
//                // call, therefore we should inherit. This works as the inflated layout is only
//                // added to the hierarchy at the end of the inflate() call.
//                return true;
//            } else if (parent == getWindow().getDecorView() || !(parent instanceof View)
//                    || ViewCompat.isAttachedToWindow((View) parent)) {
//                // We have either hit the window's decor view, a parent which isn't a View
//                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
//                // is currently added to the view hierarchy. This means that it has not be
//                // inflated in the current inflate() call and we should not inherit the context.
//                return false;
//            }
//            parent = parent.getParent();
//        }
//    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            return false;
        } else {
            for (View windowDecor = getWindow().getDecorView(); parent != null; parent = parent.getParent()) {
                if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View) parent)) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unRegister(this);
    }
}

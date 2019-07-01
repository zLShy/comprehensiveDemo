package com.shy.framelibrary.skin.support;

import android.content.Context;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.shy.framelibrary.skin.attr.SkinAttr;
import com.shy.framelibrary.skin.attr.SkinType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangli on 2019/6/19.
 */

public class SkinSupport {


    private static String TAG = "SkinSupport";

    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        List<SkinAttr> mAttrs = new ArrayList<>();
        int skinLength = attrs.getAttributeCount();
        for (int i = 0; i < skinLength; i++) {
            String attrName = attrs.getAttributeName(i);

            String attrValue = attrs.getAttributeValue(i);

            SkinType skinType = getSkinType(attrName);
            Log.e(TAG, attrName + "===" + attrValue);
            if (skinType != null) {
                String resName = getResource(context, attrValue);
                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(skinType, resName);
                mAttrs.add(skinAttr);
            }
        }

        return mAttrs;
    }

    private static String getResource(Context context, String attrValue) {
        if (attrValue.startsWith("@")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }
}


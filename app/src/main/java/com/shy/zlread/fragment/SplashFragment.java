package com.shy.zlread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangli on 2019/5/28.
 */

public class SplashFragment extends Fragment {

    public static String LAYOUT_ID = "LAYOUT_ID";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getArguments().getInt(LAYOUT_ID);
        return inflater.inflate(layoutId, container, false);
    }

}

package com.shy.zlread.adapter;

import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by zhangli on 2019/5/5.
 * adapter 设计模式
 */

public abstract class ListScreenAdapter {
    //获取长度
    public abstract int getCount();

    //获取TabView
    public abstract View getTabView(int position, ViewGroup parent);

    //获取内容View
    public abstract View getMenuView(int position, ViewGroup parent);

    public void setTabView(@NotNull View tabView, int position) {

    }

    public void close(@Nullable View tabView) {

    }
}

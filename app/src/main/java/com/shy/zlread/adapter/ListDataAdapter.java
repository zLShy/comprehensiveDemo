package com.shy.zlread.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shy.zlread.R;
import com.shy.zlread.weight.ListScreenView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by zhangli on 2019/5/5.
 */

public class ListDataAdapter extends ListScreenAdapter {

    private Context mContext;
    private String[] mItems = {"全成都", "价格", "面积", "更多"};
    private ListScreenView.MenuObserval mObserverval;

    public ListDataAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        TextView mTv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_screen_item, parent, false);

        mTv.setText(mItems[position]);
        mTv.setTextColor(Color.BLACK);
        return mTv;
    }


    public void registerDataSetObserval(ListScreenView.MenuObserval observer) {
        this.mObserverval = observer;
    }

    public void unregisterDataSetObserval() {
        this.mObserverval = null;
    }

    public void notifiyMenuChanged() {
        mObserverval.closeChange();
    }
    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView mTv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_screen_item, parent, false);

        mTv.setText(mItems[position]);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","click");
                notifiyMenuChanged();
            }
        });
        return mTv;
    }

    @Override
    public void setTabView(@NotNull View tabView, int position) {
        TextView textView = (TextView) tabView;
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLUE);
    }

    @Override
    public void close(@Nullable View tabView) {
        TextView textView = (TextView) tabView;
        textView.setTextColor(Color.BLACK);
    }
}

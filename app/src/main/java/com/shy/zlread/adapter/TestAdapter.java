package com.shy.zlread.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.shy.zlread.R;
import com.shy.zlread.bean.TestBean;

import java.util.List;

import commonAdapter.RecyclerCommonAdapter;
import commonAdapter.ViewHolder;

/**
 * Created by zhangli on 2019/4/24.
 */

public class TestAdapter extends RecyclerCommonAdapter<TestBean> {

    public TestAdapter(List<TestBean> mDatas, Context mContext, int mLayoutId) {
        super(mDatas, mContext, mLayoutId);
    }

    @Override
    protected void covert(ViewHolder holder, TestBean testBean, int position) {

        TextView tv = holder.getView(R.id.recycler_item_tv);
        tv.setText(testBean.getName());

    }

}

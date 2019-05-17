package com.shy.zlread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shy.zlread.R;

import java.util.List;

/**
 * Created by zhangli on 2019/4/2.
 */

public class VerticalListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public VerticalListAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,null);
        TextView textView = (TextView) convertView.findViewById(R.id.item_tv);
        textView.setText(mList.get(position));
        return convertView;
    }
}

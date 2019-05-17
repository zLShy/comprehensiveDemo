package com.shy.zlread.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shy.zlread.FruitDetailsActivity;
import com.shy.zlread.R;
import com.shy.zlread.bean.Fruit;

import java.util.List;

/**
 * Created by zhangli on 2019/4/12.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{


    private List<Fruit> mList;
    private Context mContext;

    public FruitAdapter(List<Fruit> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Fruit fruit = mList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, FruitDetailsActivity.class);
                intent.putExtra("id",mList.get(position).getImageId());
                intent.putExtra("name",mList.get(position).getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

}

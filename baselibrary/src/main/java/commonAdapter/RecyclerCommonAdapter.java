package commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhangli on 2019/4/23.
 */

public abstract class RecyclerCommonAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {

    private List<DATA> mDatas;
    protected Context mContext;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private ItemOnClick mItemOnClick;

    public RecyclerCommonAdapter(List<DATA> mDatas, Context mContext,int mLayoutId) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutId = mLayoutId;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mLayoutId,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        covert(holder,mDatas.get(position),position);
        if (mItemOnClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemOnClick.onItemClick(position);
                }
            });
        }
    }

    protected abstract void covert(ViewHolder holder, DATA data, int position);

    public void setmItemOnClick(ItemOnClick mItemOnClick) {
        this.mItemOnClick = mItemOnClick;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}

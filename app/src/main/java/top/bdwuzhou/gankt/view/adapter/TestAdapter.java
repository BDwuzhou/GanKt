package top.bdwuzhou.gankt.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import top.bdwuzhou.gankt.R;
import top.bdwuzhou.gankt.model.GankData;
import top.bdwuzhou.gankt.util.GlideApp;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<GankData> mData;

    public List<GankData> getData() {
        return mData;
    }

    public void setData(final List<GankData> data) {
        if (mData == null) {
            mData = data;
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mData.get(oldItemPosition).getId().equals(data.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    GankData oldData = mData.get(oldItemPosition);
                    GankData newData = data.get(newItemPosition);
                    return oldData.getId().equals(newData.getId()) &&
                            oldData.getDesc().equals(newData.getDesc());
                }
            });
            result.dispatchUpdatesTo(this);
        } else {
            mData = data;
            notifyItemRangeInserted(0, mData.size());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(holder.mIvItem)
                .load(mData.get(position).getUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.mIvItem);
        holder.mTvDesc.setText(mData.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mIvItem;
        private final TextView mTvDesc;

        ViewHolder(View itemView) {
            super(itemView);
            mIvItem = itemView.findViewById(R.id.iv_item);
            mTvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}

package com.example.qqlist.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by seven
 */

public abstract class   BaseRecyclerAdapter<T> extends RecyclerView.Adapter<MyRVViewHolder> {
    public List<T> mDatas;
    private LayoutInflater mInflater;
    private int mlayoutId;
    public Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public BaseRecyclerAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.mDatas = datas;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    public Context getAdapterContext() {
        return context;
    }

    /**
     * 提供点击开放式方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    /**
     * 提供长按开放方法
     *
     * @param listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public MyRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyRVViewHolder viewHolder = new MyRVViewHolder(mInflater.inflate(mlayoutId, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRVViewHolder holder, int position) {
        setView(holder, mDatas.get(position), position);
        setUpItemEvent(holder);
    }

    public abstract void setView(MyRVViewHolder holder, T datadto, int position);

    /**
     * 点击和长按条目
     *
     * @param holder
     */
    public void setUpItemEvent(final MyRVViewHolder holder) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这个获取位置的方法，防止添加删除导致位置不变
                    int layoutPosition = holder.getAdapterPosition();
                    onItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
        }
        if (null != onItemLongClickListener) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getAdapterPosition();
                    onItemLongClickListener.onItemLongClick(holder.itemView, layoutPosition);
                    return true;
                }
            });
        }
    }

    public void addData(int pos, T datas) {
        mDatas.add(pos, datas);
        notifyItemInserted(pos);
    }

    public void deleteData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    /**
     * 定义点击回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}

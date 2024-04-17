package com.example.qqlist.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by seven
 */

public class   MyRVViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public MyRVViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    //通过viewId获取控件
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     */
    public MyRVViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public MyRVViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

//    public MyRVViewHolder setImageURI(Context context, int viewId, String url, int defultPicResId) {
//        ImageView view = getView(viewId);
//        ImageUtil.displayImage(view, url, context, defultPicResId);
//        return this;
//    }
}

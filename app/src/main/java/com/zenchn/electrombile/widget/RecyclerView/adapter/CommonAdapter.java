package com.zenchn.electrombile.widget.RecyclerView.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.zenchn.electrombile.widget.RecyclerView.base.ItemViewDelegate;
import com.zenchn.electrombile.widget.RecyclerView.base.ViewHolder;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/2 21:55
 * 描    述：
 * 修订记录：
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mData;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> data) {
        super(context, data);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        this.mData = data;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}

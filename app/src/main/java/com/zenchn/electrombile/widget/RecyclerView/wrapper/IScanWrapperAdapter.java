package com.zenchn.electrombile.widget.RecyclerView.wrapper;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 作    者：wangr on 2017/3/13 16:46
 * 描    述：
 * 修订记录：
 */
public interface IScanWrapperAdapter<T extends RecyclerView.ViewHolder> {

    int getItemViewType(int position);

    T onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(T holder, int position);

    int getItemCount();

    void onBindViewHolderByItemCircleScan(T holder, int position, ScanWrapperAdapter.OnScanListener mScanListener);

    void onBindViewHolderByItemHide(T holder, int position);

}

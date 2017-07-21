package com.zenchn.electrombile.widget.RecyclerView.wrapper;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * 作    者：wangr on 2017/3/13 16:15
 * 描    述：
 * 修订记录：
 */
public class ScanWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int[] mCountPool;
    private IScanWrapperAdapter mScanWrapperAdapter;
    private OnItemClickListener mItemClickListener;
    private OnScanListener mScanListener;
    private Handler mHandler;

    private int mCirclePosition = Integer.MAX_VALUE;

    public ScanWrapperAdapter(@NonNull IScanWrapperAdapter mScanWrapperAdapter) {
        this.mScanWrapperAdapter = mScanWrapperAdapter;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public ScanWrapperAdapter setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        return this;
    }

    public ScanWrapperAdapter setScanListener(OnScanListener mScanListener) {
        this.mScanListener = mScanListener;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        return mScanWrapperAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mScanWrapperAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(position);
            }
        });

        if (position < mCirclePosition) {
            mScanWrapperAdapter.onBindViewHolder(holder, position);
        } else if (position == mCirclePosition) {
            if (mScanListener != null)
                mScanListener.onCurrentScanItem(position);
            if (mCirclePosition == 0)
                if (mScanListener != null)
                    mScanListener.onScanStart();
            if (mCirclePosition == getItemCount() - 1)//只在扫描结束的时候，放入监听器
                mScanWrapperAdapter.onBindViewHolderByItemCircleScan(holder, position, mScanListener);
            else
                mScanWrapperAdapter.onBindViewHolderByItemCircleScan(holder, position, null);
        } else {
            mScanWrapperAdapter.onBindViewHolderByItemHide(holder, position);
        }
    }

    /**
     * 展示结果
     */
    public void showResult() {
        mCirclePosition = -1;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCirclePosition++;
                mCountPool[mCirclePosition] = mCirclePosition;
                notifyDataSetChanged();
                if (mCirclePosition < getItemCount() - 1) {
                    mHandler.postDelayed(this, 1200);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        mCountPool = new int[mScanWrapperAdapter.getItemCount()];
        return mCountPool.length;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public interface OnScanListener {

        void onScanStart();

        void onScanEnd();

        void onCurrentScanItem(int position);

    }

}

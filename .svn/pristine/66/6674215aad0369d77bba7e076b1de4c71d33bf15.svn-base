package com.zenchn.electrombile.widget.RecyclerView.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.widget.RecyclerView.base.ViewHolder;
import com.zenchn.electrombile.widget.RecyclerView.utils.WrapperUtils;

/**
 * 作    者：wangr on 2017/3/2 22:01
 * 描    述：
 * 修订记录：
 */
public class LoadMoreWrapperAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;
    public static final int ITEM_TYPE_LOAD_ERROR = Integer.MAX_VALUE - 3;
    public static final int ITEM_TYPE_LOAD_END = Integer.MAX_VALUE - 4;
    public static final int ITEM_TYPE_LOAD_EMPTY = Integer.MAX_VALUE - 5;

    private RecyclerView.Adapter mInnerAdapter;

    private View mLoadMoreView;
    private View mLoadEndView;
    private View mLoadErrorView;
    private View mLoadEmptyView;
    private int mLoadMoreLayoutId;
    private int mLoadEndLayoutId;
    private int mLoadErrorLayoutId;
    private int mLoadEmptyLayoutId;

    private boolean isLoadEnd;
    private boolean isLoadSuccess;

    private OnLoadMoreListener mOnLoadMoreListener;
    private OnItemClickListener mOnItemClickListener;

    public LoadMoreWrapperAdapter<T> setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        if (onLoadMoreListener != null)
            this.mOnLoadMoreListener = onLoadMoreListener;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null)
            this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadEndView(View loadEndView) {
        mLoadEndView = loadEndView;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadEndView(int layoutId) {
        mLoadEndLayoutId = layoutId;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadErrorView(View loadErrorView) {
        mLoadErrorView = loadErrorView;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadErrorView(int layoutId) {
        mLoadErrorLayoutId = layoutId;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadEmptyView(View mLoadEmptyView) {
        this.mLoadEmptyView = mLoadEmptyView;
        return this;
    }

    public LoadMoreWrapperAdapter<T> setLoadEmptyLayoutId(int layoutId) {
        this.mLoadEmptyLayoutId = layoutId;
        return this;
    }

    public LoadMoreWrapperAdapter(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (mInnerAdapter.getItemCount() == 0 && hasLoadMore()) {
            return ITEM_TYPE_LOAD_EMPTY;
        } else if (position >= mInnerAdapter.getItemCount() && hasLoadMore()) {
            return isLoadSuccess ? (isLoadEnd ? ITEM_TYPE_LOAD_END : ITEM_TYPE_LOAD_MORE) : ITEM_TYPE_LOAD_ERROR;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (ITEM_TYPE_LOAD_MORE == viewType) {

            ViewHolder holder;
            if (mLoadMoreView != null)
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            else if (mLoadMoreLayoutId != 0)
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            else
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, R.layout.recyclerview_default_loading);
            return holder;

        } else if (ITEM_TYPE_LOAD_ERROR == viewType) {

            ViewHolder holder;
            if (mLoadErrorView != null)
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadErrorView);
            else if (mLoadErrorLayoutId != 0)
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadErrorLayoutId);
            else
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, R.layout.recyclerview_default_load_error);
            return holder;

        } else if (ITEM_TYPE_LOAD_END == viewType) {

            ViewHolder holder;
            if (mLoadEndView != null)
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadEndView);
            else if (mLoadEndLayoutId != 0)
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadEndLayoutId);
            else
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, R.layout.recyclerview_default_load_end);
            return holder;
        } else if (ITEM_TYPE_LOAD_EMPTY == viewType) {
            ViewHolder holder;
            if (mLoadEmptyView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadEmptyView);
            } else if (mLoadEmptyLayoutId != 0) {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadEmptyLayoutId);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, R.layout.recyclerview_default_empty);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position >= mInnerAdapter.getItemCount()) {
            if (mOnLoadMoreListener != null) {
                if (isLoadSuccess) {
                    if (!isLoadEnd)
                        mOnLoadMoreListener.onLoadMoreRequested();
                } else {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnLoadMoreListener.onLoadMoreErrorReload();
                        }
                    });
                }
                return;
            }
        } else if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();

        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;

            params.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }

    public interface OnLoadMoreListener {

        void onLoadMoreRequested();

        void onLoadMoreEnd();

        void onLoadMoreErrorReload();

    }

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    /**
     * 设置加载成功（适用于分页加载的情形）
     *
     * @param pageNumber
     * @param totalPages
     */
    public void setLoadSuccess(int pageNumber, int totalPages) {
        setLoadSuccess();
        setLoadEnd(pageNumber >= totalPages);
    }

    /**
     * 设置加载成功
     */
    public void setLoadSuccess() {
        this.isLoadSuccess = true;
    }


    /**
     * 设置加载到底
     */
    public void setLoadEnd(boolean isLoadEnd) {
        this.isLoadEnd = isLoadEnd;
        if (isLoadEnd && mOnLoadMoreListener != null)
            mOnLoadMoreListener.onLoadMoreEnd();
    }

    /**
     * 设置加载失败
     */
    public void setLoadError() {
        this.isLoadSuccess = false;
    }

}

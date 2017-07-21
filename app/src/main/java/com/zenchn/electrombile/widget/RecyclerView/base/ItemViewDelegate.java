package com.zenchn.electrombile.widget.RecyclerView.base;


/**
 * 作    者：wangr on 2017/3/2 21:59
 * 描    述：
 * 修订记录：
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}

package com.zenchn.electrombile.widget.RecyclerView.listener;

/**
 * 作    者：wangr on 2017/2/28 19:00
 * 描    述：
 * 修订记录：
 */
public interface OnItemGroupClickListener<T> {

    void onItemClick(int position, T data);

    void onLeftButtonClick(int position, T data);

    void onRightButtonClick(int position, T data);

}

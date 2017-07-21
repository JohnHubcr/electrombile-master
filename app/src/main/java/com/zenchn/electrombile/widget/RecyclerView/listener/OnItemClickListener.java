package com.zenchn.electrombile.widget.RecyclerView.listener;

/**
 * 作    者：wangr on 2017/2/27 16:50
 * 描    述：
 * 修订记录：
 */
public interface OnItemClickListener<T> {

    void onItemChoose(int position, T data);

}

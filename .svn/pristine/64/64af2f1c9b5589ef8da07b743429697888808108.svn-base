package com.zenchn.electrombile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.adapter.bean.ContactInfo;
import com.zenchn.electrombile.widget.RecyclerView.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作    者：wangr on 2017/2/27 16:01
 * 描    述：用于联系人展示的适配器
 * 修订记录：
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context mContext;
    private List<ContactInfo> mData;
    private OnItemClickListener listener;

    public ContactsAdapter(Context mContext, List<ContactInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public ContactsAdapter(Context mContext, List<ContactInfo> mData, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.listener = listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_contacts, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ContactInfo contactInfo = mData.get(position);
        if (contactInfo != null) {
            holder.tvName.setText(contactInfo.getName());
            holder.tvNumber.setText(contactInfo.getPhone());
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        holder.ivChoose.setVisibility(View.VISIBLE);
                        listener.onItemChoose(position, mData.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.iv_choose)
        ImageView ivChoose;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

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
import com.zenchn.electrombile.entity.VehicleRecordInfo;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.RecyclerView.listener.OnGetFlagListener;
import com.zenchn.electrombile.widget.RecyclerView.listener.OnItemGroupClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作    者：wangr on 2017/2/28 12:14
 * 描    述：
 * 修订记录：
 */
public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {

    private Context mContext;
    private List<VehicleRecordInfo> mData;
    private OnItemGroupClickListener<VehicleRecordInfo> listener;
    private OnGetFlagListener<Integer> onGetFlagListener;

    public VehicleListAdapter(Context mContext, List<VehicleRecordInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public VehicleListAdapter(Context mContext, List<VehicleRecordInfo> mData, OnItemGroupClickListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.listener = listener;
    }

    public void setListener(OnItemGroupClickListener listener) {
        this.listener = listener;
    }

    public void addOnFlagListener(OnGetFlagListener onGetFlagListener) {
        this.onGetFlagListener = onGetFlagListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_vehicle_record, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final VehicleRecordInfo vehicleRecordInfo = mData.get(position);
        if (vehicleRecordInfo != null) {

            holder.tvVehicleOrder.setText(String.format(mContext.getString(R.string.vehicle_auto_sort), position + 1));

            String vehicleAlias = vehicleRecordInfo.getVehicleAlias();
            if (CommonUtils.isEmpty(vehicleAlias)) {
                String vehicleModel = vehicleRecordInfo.getVehicleModel();
                String vehicleBrand = vehicleRecordInfo.getVehicleBrand();
                holder.tvVehicleModel.setText(CommonUtils.isEmpty(vehicleModel) || CommonUtils.isEmpty(vehicleBrand) ? vehicleRecordInfo.getSerialNumber() : String.format(mContext.getString(R.string.vehicle_auto_nominate), vehicleBrand, vehicleModel));
            } else {
                holder.tvVehicleModel.setText(vehicleAlias);
            }

            boolean whetherCommon = vehicleRecordInfo.isWhetherCommon();
            if (whetherCommon) {
                if (onGetFlagListener != null)
                    onGetFlagListener.onGetFlag(position);
            }

            holder.tvVehicleStatus.setText(mContext.getString(whetherCommon ? R.string.is_common_vehicle : R.string.is_not_common_vehicle));
            holder.ivVehicleStatus.setImageResource(whetherCommon ? R.drawable.check_safe : R.drawable.check_null_safe);

            if (listener != null) {
                holder.llItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(position, vehicleRecordInfo);
                    }
                });

                holder.llSetDefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onLeftButtonClick(position, vehicleRecordInfo);
                    }
                });

                holder.tvVehicleUnbind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onRightButtonClick(position, vehicleRecordInfo);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_vehicle_order)
        TextView tvVehicleOrder;
        @BindView(R.id.tv_vehicle_model)
        TextView tvVehicleModel;
        @BindView(R.id.iv_vehicle_status)
        ImageView ivVehicleStatus;
        @BindView(R.id.tv_vehicle_status)
        TextView tvVehicleStatus;
        @BindView(R.id.ll_set_default)
        LinearLayout llSetDefault;
        @BindView(R.id.tv_vehicle_unbind)
        TextView tvVehicleUnbind;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

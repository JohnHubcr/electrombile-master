package com.zenchn.electrombile.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.entity.ChargingStationInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 作    者：wangr on 2017/3/3 19:17
 * 描    述：用于维修点展示的适配器
 * 修订记录：
 */
public class ChargingOutletsAdapter extends RecyclerView.Adapter<ChargingOutletsAdapter.ViewHolder> {

    private List<ChargingStationInfo> mData;
    private Context mContext;
    private LatLng mLatLng;
    private final String distance_format;
    private final String distance_k_format;
    private final String address_format;

    public ChargingOutletsAdapter(Context context, List<ChargingStationInfo> data) {
        this.mData = data;
        this.mContext = context;
        this.distance_format = context.getResources().getString(R.string.distance);
        this.distance_k_format = context.getResources().getString(R.string.distance_k);
        this.address_format = context.getResources().getString(R.string.address_format);
    }

    public void setLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    @Override
    public ChargingOutletsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_charge_outlets, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChargingStationInfo chargingStationInfo = mData.get(position);
        if (chargingStationInfo != null) {
            holder.tvName.setText(chargingStationInfo.getName());

            String address = chargingStationInfo.getAddress();
            holder.tvLocation.setText(String.format(address_format, chargingStationInfo.getProvince(), chargingStationInfo.getCity(), chargingStationInfo.getDistrict(), address));

            double distanceValue = chargingStationInfo.getDistance();
            if (distanceValue > 0) {
                holder.tvDistance.setText(distanceValue > 1000f ? String.format(distance_k_format, distanceValue / 1000) : String.format(distance_format, distanceValue));
            } else if (distanceValue <= 0 && mLatLng != null) {
                distanceValue = DistanceUtil.getDistance(mLatLng, chargingStationInfo.getLatLng());
                if (distanceValue < 0)
                    distanceValue = 0;
                chargingStationInfo.setDistance(distanceValue);
            }
        }
    }


    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_star)
        TextView tvStar;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_brand)
        TextView tvBrand;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

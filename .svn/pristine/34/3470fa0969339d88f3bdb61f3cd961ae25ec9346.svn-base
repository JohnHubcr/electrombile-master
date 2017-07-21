package com.zenchn.electrombile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.entity.VehicleContrailListInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：wangr on 2016/10/2 0022 10:41
 * 描述：用于轨迹分段展示的适配器
 */
public class MotorContrailAdapter extends BaseAdapter {

    private List<VehicleContrailListInfo> mData;
    private Context mContext;

    public MotorContrailAdapter(List<VehicleContrailListInfo> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.xlv_item_motor_contrail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        VehicleContrailListInfo vehicleContrailListInfo = mData.get(position);
        viewHolder.tvTimeRange.setText(vehicleContrailListInfo.getTimeRange());
        viewHolder.tvMotorTotalMileage.setText(vehicleContrailListInfo.getMileage());
        viewHolder.tvMotorTotalMileageUnit.setText(vehicleContrailListInfo.getMileageUnit());
        viewHolder.tvMotorTotalTime.setText(vehicleContrailListInfo.getTimeSpan());
        viewHolder.tvMotorTotalTimeUnit.setText(vehicleContrailListInfo.getTimeSpanUnit());
        viewHolder.tvMotorAverageSpeed.setText(String.valueOf(vehicleContrailListInfo.getSpeed()));
        viewHolder.tvMotorAverageSpeedUnit.setText(vehicleContrailListInfo.getSpeedUnit());
        viewHolder.tvDate.setText(vehicleContrailListInfo.getStartDate());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_time_range)
        TextView tvTimeRange;
        @BindView(R.id.tv_motor_total_mileage)
        TextView tvMotorTotalMileage;
        @BindView(R.id.tv_motor_total_mileage_unit)
        TextView tvMotorTotalMileageUnit;
        @BindView(R.id.tv_motor_total_time)
        TextView tvMotorTotalTime;
        @BindView(R.id.tv_motor_total_time_unit)
        TextView tvMotorTotalTimeUnit;
        @BindView(R.id.tv_motor_average_speed)
        TextView tvMotorAverageSpeed;
        @BindView(R.id.tv_motor_average_speed_unit)
        TextView tvMotorAverageSpeedUnit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

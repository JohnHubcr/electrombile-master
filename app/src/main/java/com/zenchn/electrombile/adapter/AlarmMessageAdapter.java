package com.zenchn.electrombile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.entity.AlarmMessageInfo;
import com.zenchn.electrombile.entity.model.AlarmEnum;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.mlibrary.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作    者：wangr on 2017/3/2 16:32
 * 描    述：用于报警信息展示的适配器
 * 修订记录：
 */
public class AlarmMessageAdapter extends RecyclerView.Adapter<AlarmMessageAdapter.ViewHolder> {

    private Context mContext;
    private List<AlarmMessageInfo> mData;

    public AlarmMessageAdapter(Context context, List<AlarmMessageInfo> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_alarm_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlarmMessageInfo alarmMessageInfo = mData.get(position);
        if (alarmMessageInfo != null) {

            holder.tvDate.setText(DateUtils.convertDateToString(alarmMessageInfo.getMsgDate(), "yyyy-MM-dd HH:mm:ss"));
            holder.tvType.setText(AlarmEnum.classifyAlarmByMsgType(alarmMessageInfo.getMsgType()).name());
            holder.tvDesc.setText(alarmMessageInfo.getMsgContent());

            if (CommonUtils.isNonNull(alarmMessageInfo.getVehicleAddress()))
                holder.tvLocation.setText(alarmMessageInfo.getVehicleAddress());

            holder.tvLocation.setSelected(true);
        }
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_location)
        TextView tvLocation;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

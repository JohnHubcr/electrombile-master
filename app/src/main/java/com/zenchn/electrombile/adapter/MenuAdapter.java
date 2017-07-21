package com.zenchn.electrombile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.adapter.bean.MenuInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：wangr on 2016/10/22 0022 22:27
 * 描述：用于首页菜单区域的控制
 */
public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private List menuInfos;

    public MenuAdapter(List menuInfos, Activity activity) {
        this.menuInfos = menuInfos;
        this.mContext = activity;
    }

    @Override
    public int getCount() {
        return menuInfos != null ? menuInfos.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return menuInfos != null ? menuInfos.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_menu_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MenuInfo menuInfo = (MenuInfo) menuInfos.get(position);
        viewHolder.tvMenuTitle.setText(menuInfo.getTitle());
        viewHolder.ivMenuIcon.setImageResource(menuInfo.getIconId());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_menu_icon)
        ImageView ivMenuIcon;
        @BindView(R.id.tv_menu_title)
        TextView tvMenuTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

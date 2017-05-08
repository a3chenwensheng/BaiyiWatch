package com.baiyi.watch.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.SettingAlert;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;

import java.util.Date;
import java.util.List;

public class RemindAdapter extends BaseAdapter {
    List<SettingAlert> datas;
    Device device;
    Context context;

    private OnDeleteListener listener;

    public RemindAdapter(Context context, List<SettingAlert> datas, Device device) {
        this.datas = datas;
        this.device = device;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int arg0) {
        return datas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = View.inflate(context, R.layout.item_remind, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.alert_name_tv);
            holder.remainTimeTv = (TextView) convertView.findViewById(R.id.alert_remaintime_tv);
            holder.deleteBtn = (ImageButton) convertView.findViewById(R.id.alert_delete_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SettingAlert settingAlert = datas.get(position);

        holder.deleteBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnDeleteListener(position);
                }

            }
        });

        holder.nameTv.setText(settingAlert.getName());

        if ("true".equals(settingAlert.getEnable())) {//开启就显示
            if ("1".equals(settingAlert.getAlert_type())) {//仅一次
                Date date = TimeUtils.string2Date(settingAlert.getTime(), "yyyyMMddHHmmss");
                String time = TimeUtils.intervalTime2(date);
                holder.remainTimeTv.setText( time + ("时间已过".equals(time) ? "" : "后响铃"));
            } else {
                holder.remainTimeTv.setText(StringUtils.caculateTime(formatMode(settingAlert.getTime()), formatTime(settingAlert.getTime()))  + "后响铃" );
            }
        } else {
            holder.remainTimeTv.setText("已关闭");
        }

        return convertView;
    }

    private String formatMode(String time) {

        String weekStr = "";

        try {
            weekStr = time.split("\\+")[0];
        } catch (Exception e) {
        }

        return weekStr;
    }

    private String formatTime(String time) {
        String timeStr = "";

        try {
            timeStr = time.split("\\+")[1] + ":" + time.split("\\+")[2];
        } catch (Exception e) {
        }

        return timeStr;
    }

    public static class ViewHolder {
        public TextView nameTv;
        public TextView remainTimeTv;
        public ImageButton deleteBtn;

    }

    public interface OnDeleteListener {
        public void OnDeleteListener(int position);
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.listener = onDeleteListener;
    }

}
package com.baiyi.watch.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Notification;
import com.baiyi.watch.utils.TimeUtils;

import java.util.Date;
import java.util.List;

public class FenceDataAdapter extends BaseAdapter {
    List<Notification> datas;
    Context context;

    public FenceDataAdapter(Context context, List<Notification> datas) {
        this.datas = datas;
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
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = View.inflate(context, R.layout.item_fence_data, null);
            holder = new ViewHolder();
            holder.timeTv = (TextView) convertView.findViewById(R.id.fence_data_time_tv);
            holder.contentTv = (TextView) convertView.findViewById(R.id.fence_data_content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Notification notification = datas.get(position);

        Date date = TimeUtils.jsonStr2StrDate(notification.getCreated_at());
        holder.timeTv.setText(TimeUtils.date2Str(date, "HH:mm"));
        holder.contentTv.setText(notification.getContent());

        return convertView;
    }

    public static class ViewHolder {
        public TextView timeTv;
        public TextView contentTv;
    }

}

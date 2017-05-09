package com.baiyi.watch.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Sosdata;
import com.baiyi.watch.utils.TimeUtils;

import java.util.List;

public class SOSAdapter extends BaseAdapter {
	List<Sosdata> datas;
	Context context;

	public SOSAdapter(Context context, List<Sosdata> datas) {
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
			convertView = View.inflate(context, R.layout.item_sos, null);
			holder = new ViewHolder();
			holder.timeTv = (TextView) convertView.findViewById(R.id.sos_time_tv);
			holder.heartRateTv = (TextView) convertView.findViewById(R.id.sos_heart_rate_tv);
			holder.addressTv = (TextView) convertView.findViewById(R.id.sos_address_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Sosdata sosdata = datas.get(position);

		holder.timeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(sosdata.getTime_begin()), "HH:mm"));
		holder.heartRateTv.setText("心率：" + sosdata.getHeartrate() + " bmp");
		holder.addressTv.setText("地址：" + sosdata.getAddress());

		return convertView;
	}

	public static class ViewHolder {
		public TextView timeTv;
		public TextView heartRateTv;
		public TextView addressTv;
	}

}

package com.baiyi.watch.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.net.BaseApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DevicesAdapter extends BaseAdapter {
	List<Device> datas;
	Context context;

	public DevicesAdapter(Context context, List<Device> datas) {
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
			convertView = View.inflate(context, R.layout.item_device, null);
			holder = new ViewHolder();
			holder.sortTv = (TextView) convertView.findViewById(R.id.sort_tv);
			holder.avatarImv = (CircleImageView) convertView.findViewById(R.id.device_avatar_imv);
			holder.nameTv = (TextView) convertView.findViewById(R.id.device_name_tv);
			holder.imeiTv = (TextView) convertView.findViewById(R.id.device_imei_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Device device = datas.get(position);

//		if (device.mOwner != null && !TextUtils.isEmpty(device.mOwner.getAvatar_url())) {
//			String avtarUrl = device.mOwner.getAvatar_url();
//			if (!avtarUrl.contains("http")) {
//				avtarUrl = BaseApi.BASE_Url2 + device.mOwner.getAvatar_url();
//			}
//			ImageLoader.getInstance().displayImage(avtarUrl, holder.avatarImv, getOptions(R.drawable.default_user_avatar));
//		
//		}
		
		String currentSort = device.getGroup_name();
		String previewSort = (position - 1) >= 0 ? (datas.get(position - 1).getGroup_name()) : " ";

		if (previewSort != null && !previewSort.equals(currentSort)) { 
			holder.sortTv.setVisibility(View.VISIBLE);
			holder.sortTv.setText(currentSort);
		} else {
			holder.sortTv.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(device.getAvatar_url())) {
			String avtarUrl = device.getAvatar_url();
			if (!avtarUrl.contains("http")) {
				avtarUrl = BaseApi.BASE_Url2 + device.getAvatar_url();
			}
			ImageLoader.getInstance().displayImage(avtarUrl, holder.avatarImv, MyApplication.getInstance().getOptions(R.drawable.ic_default_device));

		}
//		if ("true".equals(device.getOnline())) {
//			holder.avatarImv.setSaturation(1);
//		}else {
//			holder.avatarImv.setSaturation(0);
//		}
		
		String name = "";
		try {
			name = device.getName();
		} catch (Exception e) {
		}
		holder.nameTv.setText(name);
		
		holder.imeiTv.setText(device.getImei());

		return convertView;
	}

	public static class ViewHolder {
		public TextView sortTv;
		public CircleImageView avatarImv;
		public TextView nameTv;
		public TextView imeiTv;
	}
	
}

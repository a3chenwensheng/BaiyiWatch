package com.baiyi.watch.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Member4Show;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembersAdapter extends BaseAdapter {
	
	private final int MANAGER_FLAG = 3;//管理员
	private final int NORMAL_FLAG = 4;//普通成员
	
	List<Member4Show> datas;
	Context context;
	Person person;
	
	private OnLeaveListener listener;

	public MembersAdapter(Context context, Person person, List<Member4Show> datas) {
		this.context = context;
		this.person = person;
		this.datas = datas;
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
			convertView = View.inflate(context, R.layout.item_member, null);
			holder = new ViewHolder();
			holder.groupLayout = (LinearLayout) convertView.findViewById(R.id.group_layout);
			holder.groupTv = (TextView) convertView.findViewById(R.id.group_tv);
			holder.leaveTv = (TextView) convertView.findViewById(R.id.leave_tv);
			holder.memberLayout = (LinearLayout) convertView.findViewById(R.id.member_layout);
			holder.avatarImv = (CircleImageView) convertView.findViewById(R.id.member_avatar_imv);
			holder.nameTV = (TextView) convertView.findViewById(R.id.member_name_tv);
			holder.phoneTv = (TextView) convertView.findViewById(R.id.member_phone_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Member4Show member4Show = datas.get(position);
		
		String currentSort = member4Show.getGroup_name();
		String previewSort = (position - 1) >= 0 ? (datas.get(position - 1).getGroup_name()) : " ";

		if (!previewSort.equals(currentSort)) { 
			holder.groupLayout.setVisibility(View.VISIBLE);
			holder.groupTv.setText(currentSort);
			
			if (person != null && person.mId.equals(member4Show.getGroup_ownerid())) {
				holder.leaveTv.setTag(MANAGER_FLAG);
				holder.leaveTv.setText("解 散");
			}else {
				holder.leaveTv.setTag(NORMAL_FLAG);
				holder.leaveTv.setText("退 出");
			}
			
			holder.leaveTv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((Integer)v.getTag()) == MANAGER_FLAG) {
						if (listener != null) {
							listener.onDisband(member4Show);
						}
					}else if (((Integer)v.getTag()) == NORMAL_FLAG) {
						if (listener != null) {
							listener.onLeave(member4Show);
						}
					}			
				}
			});
			
		} else {
			holder.groupLayout.setVisibility(View.GONE);
			holder.groupTv.setText("");
		}
		
		if (TextUtils.isEmpty(member4Show.mId)) {
			holder.memberLayout.setVisibility(View.GONE);
		}else {
			holder.memberLayout.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(member4Show.getAvatar_url())) {
				String avtarUrl = member4Show.getAvatar_url();
				if (!avtarUrl.contains("http")) {
					avtarUrl = BaseApi.BASE_Url2 + member4Show.getAvatar_url();
				}
				ImageLoader.getInstance().displayImage(avtarUrl, holder.avatarImv, MyApplication.getInstance().getOptions(R.drawable.ic_avatar));
			
			}
			
			String nickName = "";
			try {
				nickName = member4Show.getNickname();
			} catch (Exception e) {
			}
			holder.nameTV.setText(nickName);
			
			holder.phoneTv.setText(member4Show.getUsername());
		}

		

		return convertView;
	}

	public static class ViewHolder {
		public LinearLayout groupLayout;
		public TextView groupTv;
		public TextView leaveTv;
		
		public LinearLayout memberLayout;
		public CircleImageView avatarImv;
		public TextView nameTV;
		public TextView phoneTv;
	}
	
	public interface OnLeaveListener{
		public void onLeave(Member4Show member4Show);
		public void onDisband(Member4Show member4Show);
	}
	
	public void setOnLeaveListener(OnLeaveListener onLeaveListener) {
		this.listener = onLeaveListener;
	}
	
}

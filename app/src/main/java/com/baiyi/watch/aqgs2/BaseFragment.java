package com.baiyi.watch.aqgs2;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.baiyi.watch.auth.LoginActivity;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.Constant;
import com.baiyi.watch.utils.Sputil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author cws
 * @date 2014-12-10
 * TODO
 */

public abstract class BaseFragment extends Fragment {
	public static String TAG;
	protected Context mContext;
	protected Sputil mSputil;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		mContext = getActivity();

		
		if(null == mSputil){
			mSputil = new Sputil(mContext, Constant.PRE_NAME);
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//MobclickAgent.onPageStart(TAG);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//MobclickAgent.onPageEnd(TAG);
	}
	
	/**
	 * Activity跳转
	 * @param context
	 * @param targetActivity
	 * @param bundle
	 */
	public void redictToActivity(Context context,Class<?> targetActivity,Bundle bundle){
		Intent intent = new Intent(context, targetActivity);
		if(null != bundle){
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	protected void logout() {
		ActivityUtil.cleartDB();// 清空数据库
		NotificationManager nm =(NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancelAll();//清除所有Notification

		ImageLoader.getInstance().clearDiscCache();
		ImageLoader.getInstance().clearMemoryCache();

		redictToActivity(mContext, LoginActivity.class, null);
		//AppManager.getAppManager().finishActivity(MainActivity.class);
		getActivity().finish();
	}

}

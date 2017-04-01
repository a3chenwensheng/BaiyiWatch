package com.baiyi.watch.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.utils.ActivityUtil;

/**
 * 
 * 关于Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-10-13 16:00
 * @version v2.0
 */
@SuppressLint("NewApi")
public class AboutActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回

	private LinearLayout mQQLayout;
	private LinearLayout mVersionLayout;
	private TextView mVersionTv;
	
	//private BaseDialog mUpdateDialog;//版本更新对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);

		mQQLayout = (LinearLayout) findViewById(R.id.about_qq_layout);
		mVersionLayout = (LinearLayout) findViewById(R.id.version_layout);
		mVersionTv = (TextView) findViewById(R.id.version_tv);

	}

	/** 初始化数据 */
	private void initData() {

		mVersionTv.setText(ActivityUtil.getVersionName(mContext));

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mQQLayout.setOnClickListener(this);
		mVersionLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.about_qq_layout:
			try {
				String url = "mqqwpa://im/chat?chat_type=wpa&uin=3173249410";
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			} catch (Exception e) {
			}
			break;
		case R.id.version_layout:
			checkUpdate();//检查版本
			break;
		default:
			break;
		}

	}
	
	/**
	 * 检查版本
	 */
	private void checkUpdate() {

//		new UpdateManager(mContext).checkUpdate(new OnVersionUpdateListener() {
//
//			@Override
//			public void OnUpdate(Version version) {
//				showUpdateDialog(version);
//			}
//
//			@Override
//			public void OnNotNew(Version version) {
//				ActivityUtil.showToast(mContext, "已经是最新版本了", 1);
//			}
//
//			@Override
//			public void OnError(String error) {
//
//			}
//		});
		
	}
	
//	private void showUpdateDialog(final Version version) {
//		if (mUpdateDialog != null) {
//			mUpdateDialog.cancel();
//		}
//		mUpdateDialog = new BaseDialog(mContext);
//		mUpdateDialog.setCanceledOnTouchOutside(false);
//		mUpdateDialog.setTitle("发现新版本");
//		if (version != null) {
//			String content = version.getContent();
//			try {
//				content = content.replace("|", "\n");
//			} catch (Exception e) {
//			}
//			mUpdateDialog.setMessage(content);
//		}
//		mUpdateDialog.setTitleLineVisibility(View.INVISIBLE);
//
//		mUpdateDialog.setButton2("立即更新", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				ActivityUtil.openDownLoadService(mContext, version.getUrl(), version.getVersionName());
//				mUpdateDialog.dismiss();
//			}
//		});
//
//		if(Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1){
//			if (!isDestroyed()) {
//				mUpdateDialog.show();
//			}
//		}else {
//			if (!isFinishing()) {
//				mUpdateDialog.show();
//			}
//		}
//	}

}

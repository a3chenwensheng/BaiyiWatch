package com.baiyi.watch.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.net.BaseApi;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AdDialog extends Dialog implements View.OnClickListener {

	private String pic_url;

	private ImageView mImageView;
	private ImageButton mCloseBtn;


	private OnClickListener mOnClickListener1;// 按钮1的单击监听事件

	public AdDialog(Context context, String pic_url) {
		super(context);
	    setContentView(R.layout.include_dialog_ad);

//		Window dialogWindow = this.getWindow();
//		dialogWindow.setGravity(Gravity.BOTTOM);
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//		dialogWindow.setAttributes(lp);

		mImageView = (ImageView) findViewById(R.id.ad_imv);
		mCloseBtn = (ImageButton) findViewById(R.id.ad_close_btn);

		mImageView.setOnClickListener(this);
		mCloseBtn.setOnClickListener(this);

		this.pic_url = pic_url;

		if (!TextUtils.isEmpty(pic_url)){
			ImageLoader.getInstance().displayImage(pic_url, mImageView, MyApplication.getInstance().getOptions(R.mipmap.mis_default_error));

		}



	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ad_close_btn:
			super.cancel();
			break;
		case R.id.ad_imv:
			if (mOnClickListener1 != null) {
				mOnClickListener1.onClick(this, 0);
			}
			break;
		}
		
	}


	
	public void setRedictTo(OnClickListener listener) {
		mOnClickListener1 = listener;
	}

}

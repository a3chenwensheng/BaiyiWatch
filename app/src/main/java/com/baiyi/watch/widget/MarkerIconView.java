package com.baiyi.watch.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 
 * 自定义 MarkerIcon
 * 
 * @author 陈文声
 * @date：2016-2-29 11:30
 * @version v1.0
 */
public class MarkerIconView extends LinearLayout {

	private int mWidth;
	private int mHeight;
	private LayoutInflater layoutInflater;
	private CircleImageView mAvatarImv;


	public MarkerIconView(Context context) {
		super(context);
		initWidget(context);
	}

	public MarkerIconView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initWidget(context);
	}
	
	public MarkerIconView(Context context, String avtarUrl) {
		super(context);
		initWidget(context);
		
		if (!TextUtils.isEmpty(avtarUrl)) {
			ImageLoader.getInstance().displayImage(avtarUrl, mAvatarImv, MyApplication.getInstance().getOptions(R.drawable.ic_avatar));
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);
	}

	private void initWidget(Context context) {

		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.marker_icon_view, this);
		mAvatarImv = (CircleImageView) findViewById(R.id.device_avatar_imv);
		
	}

}

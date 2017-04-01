package com.baiyi.watch.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.utils.ActivityUtil;

public class PedometerReportAxisYView3 extends View {

	private float tb;
	private float interval_bottom;// 离底部距离
	private Paint paint_line;// Pain

	private int fineLineColor = 0xff666666; // 灰色

	private float base;// 每个数值单位的高度

	public PedometerReportAxisYView3(Context context, AttributeSet attrs) {
		super(context, attrs);

		Resources res = getResources();
		tb = res.getDimension(R.dimen.heart_rate_tb);
		interval_bottom = tb * 2.5f;

		paint_line = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_line.setStrokeWidth(tb * 2.8f);
		paint_line.setTextSize(ActivityUtil.dip2px(getContext(), 15));
		paint_line.setColor(fineLineColor);

	}

	protected void onDraw(Canvas c) {
		base = (getHeight() - interval_bottom) / 2.0f;
		//c.drawText("0",  getWidth()/2 - ActivityUtil.getFontlength(paint_line, "30") / 2.0f, getHeight() - interval_bottom + ActivityUtil.getFontHeight(paint_line) / 4.0f, paint_line);
		c.drawText("2h",  getWidth()/2 - ActivityUtil.getFontlength(paint_line, "2h") / 2.0f, getHeight() - interval_bottom + ActivityUtil.getFontHeight(paint_line) / 4.0f - 1 * base, paint_line);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(width, height);
	}

}

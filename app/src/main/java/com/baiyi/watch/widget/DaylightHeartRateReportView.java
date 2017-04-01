package com.baiyi.watch.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Daylight_heart_rate_detail;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;

import java.util.List;

public class DaylightHeartRateReportView extends View {

	private float tb;
	private float interval_left_right;
	private float interval_bottom;// 离底部距离
	private Paint paint_histogram;// 柱状图Pain
	private Paint paint_value;// 数值Pain
	private Paint paint_dottedline;// 底部虚线Pain
	private Paint paint_separator;// 分割线Pain

	private List<Daylight_heart_rate_detail> dataList;

	private int separatorColor = 0xff666666;
	
	private int histogramColor1 = 0xff00c7ff;
	private int histogramColor2 = 0xffe74388;

	private float base;// 每个数值单位的高度
	private float base_h;

	public DaylightHeartRateReportView(Context context) {
		super(context);
		init(null);
	}

	public DaylightHeartRateReportView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(null);
	}

	public DaylightHeartRateReportView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(null);
	}

	public void init(List<Daylight_heart_rate_detail> dataList) {

		this.dataList = dataList;
		Resources res = getResources();
		tb = res.getDimension(R.dimen.heart_rate_tb);
		interval_left_right = tb * 4.0f;
		interval_bottom = tb * 2.5f;

		paint_separator = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_separator.setTextAlign(Paint.Align.CENTER);
		paint_separator.setStrokeWidth(tb * 0.15f);
		paint_separator.setTextSize(tb * 0.95f);
		paint_separator.setColor(separatorColor);

		paint_dottedline = new Paint(Paint.ANTI_ALIAS_FLAG);
		// paint_dottedline.setPathEffect(effects);
		paint_dottedline.setStyle(Paint.Style.STROKE);
		paint_dottedline.setColor(separatorColor);
		paint_dottedline.setStrokeWidth(tb * 0.10f);

		paint_histogram = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_histogram.setStrokeWidth(tb * 2.0f);
		paint_histogram.setAntiAlias(true);
		
		paint_value = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_value.setTextAlign(Paint.Align.CENTER);
		paint_value.setStrokeWidth(tb * 0.15f);
		paint_value.setTextSize(tb * 1.1f);
		paint_value.setColor(separatorColor);

	}

	protected void onDraw(Canvas c) {

		drawStraightLine(c);
		drawHistogram(c);
		drawDate(c);

	}

	/**
	 * 绘制竖线
	 * 
	 * @param c
	 */
	public void drawStraightLine(Canvas c) {

		// 数据背景分割线
		base = (getHeight() - interval_bottom) / 180;
		base_h = (getWidth() - interval_left_right) / 6;

		// 底部时间分隔线
//		for (int i = 0; i < 7; i++) {
//			float x = base_h * i + interval_left_right / 2.0f;
//			c.drawLine(x, getHeight() - interval_bottom + tb * 0.15f, x, getHeight() - interval_bottom - tb * 0.2f, paint_dottedline);
//		}

		// 底部横条时间横实线
		c.drawLine(0, getHeight() - interval_bottom, getWidth(), getHeight() - interval_bottom, paint_dottedline);
		c.drawLine(0, 0, 0, getHeight() - interval_bottom, paint_dottedline);

//		paint_dottedline2.setColor(fineLineColor);
//		Path path2 = new Path();
//		path2.moveTo(0, getHeight() - interval_bottom - 100 * base);
//		path2.lineTo(getWidth(), getHeight() - interval_bottom - 100 * base);
//		c.drawPath(path2, paint_dottedline2);

	}

	/**
	 * 绘制柱状图
	 * 
	 * @param c
	 */
	public void drawHistogram(Canvas c) {
		if (dataList != null) {
			
			for (int i = 0; i < dataList.size(); i++) {
				Daylight_heart_rate_detail data = dataList.get(i);
	
				int value = StringUtils.string2Int(data.getHeartRate());
				float x = base_h * i + interval_left_right / 2.0f;
				float y1 = getHeight() - interval_bottom - value * base;
				float y2 = getHeight() - interval_bottom;
	
				if (value < 60 || value > 100 ) {
					paint_histogram.setColor(histogramColor2);
				}else {
					paint_histogram.setColor(histogramColor1);
				}
				c.drawLine(x,  y2, x, y1, paint_histogram);
				if (value > 0) {
					c.drawText(value + "", x, y1 - ActivityUtil.getFontHeight(paint_value)*0.2f, paint_value);
				}
				
	
			}

		}
	}

	

	private void drawDate(Canvas c) {
		if (dataList != null) {

			for (int i = 0; i < dataList.size(); i++) {
				float x = base_h * i + interval_left_right / 2.0f;
				float y = getHeight() - interval_bottom / 2;
				// drawText(c, x, y, data);
				String dateStr = "";
				try {
					dateStr = dataList.get(i).getHeartDate().substring(5);
				} catch (Exception e) {
				}
				
				c.drawText(dateStr, x, y, paint_separator);
			}
		}

	}

	/*
	 * 清空，重绘图
	 */
	public void clear() {
		init(null);
		invalidate();
	}

	/*
	 * 刷新，重绘图
	 */
	public void refresh(List<Daylight_heart_rate_detail> dataList) {
		this.dataList = dataList;
		init(this.dataList);
		invalidate();
	}

}

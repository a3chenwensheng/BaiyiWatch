package com.baiyi.watch.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Fall_report_detail;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;

import java.util.List;

public class FallReportView extends View {

	private float tb;
	private float interval_left_right;
	private float interval_bottom;// 离底部距离
	private Paint paint_date;
	private Paint paint_line;
	private Paint paint_brokenline;
	private Paint paint_cubic;
	private Paint paint_circle;
	private Paint paint_dottedline;// 底部虚线Pain
	private Paint paint_dottedline2;// 背景虚线Pain
	private Paint paint_separator;// 分割线Pain

	private List<Fall_report_detail> dataList;

	private int fineLineColor = 0xffaaaaaa; // 深灰色
	private int dateColor = 0xff666666; // 深灰色
	private int separatorColor = 0xff666666;
	private int brokenlineColor = 0xff00C7ff; // 主题色
	private int circleColor = 0xffffffff; // 白色

	private float base;// 每个数值单位的高度
	private float base_h;

	public FallReportView(Context context) {
		super(context);
		init(null);
	}

	public FallReportView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(null);
	}

	public FallReportView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(null);
	}

	public void init(List<Fall_report_detail> dataList) {

		this.dataList = dataList;
		Resources res = getResources();
		tb = res.getDimension(R.dimen.heart_rate_tb);
		interval_left_right = tb * 4.0f;
		interval_bottom = tb * 2.5f;

		paint_date = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_date.setStrokeWidth(tb * 2.8f);
		paint_date.setTextSize(ActivityUtil.dip2px(getContext(), 15));
		paint_date.setColor(dateColor);

		paint_separator = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_separator.setTextAlign(Paint.Align.CENTER);
		paint_separator.setStrokeWidth(tb * 0.15f);
		paint_separator.setTextSize(tb * 0.95f);
		paint_separator.setColor(separatorColor);

		paint_line = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_line.setStrokeWidth(tb * 0.15f);
		paint_line.setTextSize(tb * 1.2f);
		paint_line.setColor(fineLineColor);

		PathEffect effects = new DashPathEffect(new float[] { tb * 0.4f, tb * 0.4f, tb * 0.4f, tb * 0.4f }, tb * 0.1f);

		paint_dottedline = new Paint(Paint.ANTI_ALIAS_FLAG);
		// paint_dottedline.setPathEffect(effects);
		paint_dottedline.setStyle(Paint.Style.STROKE);
		paint_dottedline.setColor(separatorColor);
		paint_dottedline.setStrokeWidth(tb * 0.10f);

		paint_dottedline2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_dottedline2.setPathEffect(effects);
		paint_dottedline2.setStyle(Paint.Style.STROKE);
		paint_dottedline2.setColor(fineLineColor);
		paint_dottedline2.setStrokeWidth(tb * 0.10f);

		paint_brokenline = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_brokenline.setStrokeWidth(tb * 0.18f);
		paint_brokenline.setColor(brokenlineColor);
		paint_brokenline.setAntiAlias(true);

		paint_circle = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_circle.setStyle(Paint.Style.FILL);
		paint_circle.setStrokeWidth(tb * 0.18f);
		paint_circle.setAntiAlias(true);

		paint_cubic = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_cubic.setStrokeWidth(tb * 0.16f);
		paint_cubic.setStyle(Paint.Style.STROKE);
		paint_cubic.setColor(brokenlineColor);

	}

	protected void onDraw(Canvas c) {

		drawStraightLine(c);
		drawBrokenLine(c);
		drawDate(c);

	}

	/**
	 * 绘制竖线
	 * 
	 * @param c
	 */
	public void drawStraightLine(Canvas c) {

		// 数据背景分割线
		base = (getHeight() - interval_bottom) / 12;
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
	 * 绘制线框图
	 * 
	 * @param c
	 */
	public void drawBrokenLine(Canvas c) {
		if (dataList != null) {

			if (dataList.size() == 1) {
				float x = interval_left_right / 2.0f;
				float y = getHeight() - interval_bottom - StringUtils.string2Int(dataList.get(0).getFallCount()) * base;
				drawCircle(c, x, y, paint_circle);

			} else {
				for (int i = 0; i < dataList.size() - 1; i++) {
					Fall_report_detail data1 = dataList.get(i);
					Fall_report_detail data2 = dataList.get(i + 1);

					float x1 = base_h * i + interval_left_right / 2.0f;
					float y1 = getHeight() - interval_bottom - StringUtils.string2Int(data1.getFallCount()) * base;

					float x2 = base_h * (i + 1) + interval_left_right / 2.0f;
					float y2 = getHeight() - interval_bottom - StringUtils.string2Int(data2.getFallCount()) * base;

					c.drawLine(x1, y1, x2, y2, paint_brokenline);

//					float wt = (x1 + x2) / 2;
//					Path path = new Path();
//					path.moveTo(x1, y1);
//					path.cubicTo(wt, y1, wt, y2, x2, y2);
//					c.drawPath(path, paint_cubic);

//					drawCircle(c, x1, y1, paint_circle, data1);
//					if (i == dataList.size() - 2) {
//						drawCircle(c, x2, y2, paint_circle, data2);
//					}

				}

			}
		}
	}

	private void drawCircle(Canvas c, float x, float y, Paint paint) {
		paint.setColor(brokenlineColor);
		c.drawCircle(x, y, 8, paint);
		paint.setColor(circleColor);
		c.drawCircle(x, y, 2, paint);

	}

	private void drawDate(Canvas c) {
		if (dataList != null) {

			for (int i = 0; i < dataList.size(); i++) {
				Fall_report_detail data = dataList.get(i);
				float x = base_h * i + interval_left_right / 2.0f;
				float y = getHeight() - interval_bottom / 2;
				// drawText(c, x, y, data);
				String dateStr = "";
				try {
					dateStr = dataList.get(i).getFallDate().substring(5);
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
	public void refresh(List<Fall_report_detail> dataList) {
		this.dataList = dataList;
		init(this.dataList);
		invalidate();
	}

}

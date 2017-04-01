package com.baiyi.watch.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.baiyi.watch.aqgs2.R;

public class CalorieView extends View {

	private float tb;
	private float interval_left_right;
	private float interval_bottom;// 离底部距离
	
	private Paint paint_line;
	private Paint paint_circle;

	private float progress = 0;
	private float max = 35000f;

	private int fineLineColor = 0xffa1a1a1; // 深灰色
	private int circleColor1 = 0xffdbdada; // 
	private int circleColor2 = 0xff00c7ff; // 
	
	private Bitmap bitmap_icon;//

	private float base_h;

	public CalorieView(Context context) {
		super(context);
		init();
	}

	public CalorieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CalorieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {

		Resources res = getResources();
		tb = res.getDimension(R.dimen.heart_rate_tb);
		interval_left_right = tb * 4.0f;
		interval_bottom = tb * 2.5f;

		paint_line = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_line.setStyle(Paint.Style.FILL);
		paint_line.setStrokeWidth(tb * 0.12f);
		

		paint_circle = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_circle.setTextAlign(Paint.Align.CENTER);
		paint_circle.setStyle(Paint.Style.FILL);
		paint_circle.setStrokeWidth(tb * 0.18f);
		paint_circle.setAntiAlias(true);
		
		bitmap_icon = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_calorie_walk);
		
	}

	protected void onDraw(Canvas c) {

		drawStraightLine(c);
		drawBrokenLine(c);

	}

	/**
	 * 绘制竖线
	 * 
	 * @param c
	 */
	public void drawStraightLine(Canvas c) {

		// 底部横条时间横实线
		paint_line.setColor(fineLineColor);
		c.drawLine(interval_left_right/2, getHeight() - interval_bottom, getWidth()-interval_left_right/2, getHeight() - interval_bottom, paint_line);
		
		paint_circle.setColor(circleColor1);
		drawCircle(c, interval_left_right/2, getHeight() - interval_bottom, paint_circle);
		drawCircle(c, getWidth()-interval_left_right/2, getHeight() - interval_bottom, paint_circle);

	}

	/**
	 * 绘制线框图
	 * 
	 * @param c
	 */
	public void drawBrokenLine(Canvas c) {
		if (max <= 0) {
			max = 35000;
		}
		if (progress < 0) {
			progress = 0;
		}
		if (progress > max) {
			progress = max;
		}
		
		base_h = (getWidth() - interval_left_right) / max;
		
		float x = interval_left_right/2 + base_h * progress;
		float y = getHeight() - interval_bottom;
		
		paint_line.setColor(circleColor2);
		c.drawLine(interval_left_right/2, y, x, y, paint_line);
		
		paint_circle.setColor(circleColor2);
		drawCircle(c, interval_left_right/2, y, paint_circle);
		drawCircle(c, x, y, paint_circle);
		
		c.drawBitmap(bitmap_icon,  x- bitmap_icon.getWidth() / 2, y- bitmap_icon.getHeight()* 1.5f, null);
		
	}

	private void drawCircle(Canvas c, float x, float y, Paint paint) {
		c.drawCircle(x, y, 13, paint);
	}
	
	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
		refresh();
	}

	public float getProgress() {
		return progress;
		
	}

	public void setProgress(float progress) {
		this.progress = progress;
		refresh();
	}

	/*
	 * 清空，重绘图
	 */
	public void clear() {
		this.progress = 0;
		init();		
		invalidate();
	}

	/*
	 * 刷新，重绘图
	 */
	public void refresh() {
		invalidate();
	}

}

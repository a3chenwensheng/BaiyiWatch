package com.baiyi.watch.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View {
	private int count = 3; // 数据个数
	private float angle = (float) (Math.PI * 2 / count);
	private float radius; // 网格最大半径
	private int centerX; // 中心X
	private int centerY; // 中心Y
	private String[] titles = { "亲情通话", "足迹追寻", "温暖提醒" };
	private double[] data = { 0, 0, 0 }; // 各维度分值
	private float maxValue = 21; // 数据最大值
	private Paint mainPaint; // 雷达区画笔
	private Paint valuePaint; // 数据区画笔
	private Paint textPaint; // 文本画笔

	public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RadarView(Context context) {
		super(context);
		init();
	}

	// 初始化
	private void init() {
		count = Math.min(data.length, titles.length);

		mainPaint = new Paint();
		mainPaint.setAntiAlias(true);
		mainPaint.setColor(Color.GRAY);
		mainPaint.setStyle(Paint.Style.STROKE);

		valuePaint = new Paint();
		valuePaint.setAntiAlias(true);
		valuePaint.setColor(0xFF257CF0);
		valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(20);
		textPaint.setColor(Color.BLACK);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		radius = Math.min(h, w) / 2 * 0.9f;
		centerX = w / 2;
		centerY = h / 2;
		postInvalidate();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		canvas.save();
		canvas.rotate(-90, centerX, centerY);
		
		drawPolygon(canvas);
		drawLines(canvas);
		drawRegion(canvas);
		
		canvas.restore();
		
		drawText(canvas);
	}

	/**
	 * 绘制正多边形
	 */
	private void drawPolygon(Canvas canvas) {
		Path path = new Path();
		float r = radius / (count - 1);
		for (int i = 1; i < count; i++) {
			float curR = r * i;
			path.reset();
			for (int j = 0; j < count; j++) {
				if (j == 0) {
					path.moveTo(centerX + curR, centerY);
				} else {
					float x = (float) (centerX + curR * Math.cos(angle * j));
					float y = (float) (centerY + curR * Math.sin(angle * j));
					path.lineTo(x, y);
				}
			}
			path.close();
			canvas.drawPath(path, mainPaint);
		}
	}

	/**
	 * 绘制直线
	 */
	private void drawLines(Canvas canvas) {
		Path path = new Path();
		for (int i = 0; i < count; i++) {
			path.reset();
			path.moveTo(centerX, centerY);
			float x = (float) (centerX + radius * Math.cos(angle * i));
			float y = (float) (centerY + radius * Math.sin(angle * i));
			path.lineTo(x, y);
			canvas.drawPath(path, mainPaint);
		}
	}

	/**
	 * 绘制文字
	 * 
	 * @param canvas
	 */
	private void drawText(Canvas canvas) {
		Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
		float fontHeight = fontMetrics.descent - fontMetrics.ascent;
//		for (int i = 0; i < count; i++) {
//			float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
//			float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
//			if (angle * i >= 0 && angle * i <= Math.PI / 2) {// 第4象限
//				canvas.drawText(titles[i], x, y, textPaint);
//			} else if (angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2) {// 第3象限
//				canvas.drawText(titles[i], x, y, textPaint);
//			} else if (angle * i > Math.PI / 2 && angle * i <= Math.PI) {// 第2象限
//				float dis = textPaint.measureText(titles[i]);// 文本长度
//				canvas.drawText(titles[i], x - dis, y, textPaint);
//			} else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {// 第1象限
//				float dis = textPaint.measureText(titles[i]);// 文本长度
//				canvas.drawText(titles[i], x - dis, y, textPaint);
//			}
//		}
		
		float dis1 = textPaint.measureText(titles[0]);// 文本长度
		canvas.drawText(titles[0], centerX - dis1/2, fontHeight, textPaint);
		
		float x2 = (float) (centerX + radius);
		float y2 = (float) (centerY + (radius/2 + fontHeight / 2) * Math.sin(angle));
		canvas.drawText(titles[1],x2, y2, textPaint);
		
		float dis = textPaint.measureText(titles[2]);// 文本长度
		float x3 = centerX - radius - dis;
		float y3 = (float) (centerY + (radius/2 + fontHeight / 2) * Math.sin(angle)) ;
		canvas.drawText(titles[2],x3, y3, textPaint);
		
	}

	/**
	 * 绘制区域
	 * 
	 * @param canvas
	 */
	private void drawRegion(Canvas canvas) {
		Path path = new Path();
		valuePaint.setAlpha(255);
		for (int i = 0; i < count; i++) {
			double value = data[i];
			if (value < 0) {
				value = 0;
			}
			if (value > maxValue) {
				value = maxValue;
			}
			
			double percent = value / maxValue;
			float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
			float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
			if (i == 0) {
				path.moveTo(x, centerY);
			} else {
				path.lineTo(x, y);
			}
			// 绘制小圆点
			canvas.drawCircle(x, y, 5, valuePaint);
		}
		valuePaint.setStyle(Paint.Style.STROKE);
		canvas.drawPath(path, valuePaint);
		valuePaint.setAlpha(127);
		// 绘制填充区域
		valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawPath(path, valuePaint);
	}

	// 设置标题
	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	// 设置数值
	public void setData(double[] data) {
		this.data = data;
		invalidate();
	}

	public float getMaxValue() {
		return maxValue;
	}

	// 设置最大数值
	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
		invalidate();
	}

	// 设置蜘蛛网颜色
	public void setMainPaintColor(int color) {
		mainPaint.setColor(color);
		invalidate();
	}

	// 设置标题颜色
	public void setTextPaintColor(int color) {
		textPaint.setColor(color);
		invalidate();
	}

	// 设置覆盖局域颜色
	public void setValuePaintColor(int color) {
		valuePaint.setColor(color);
		invalidate();
	}
}
package com.baiyi.watch.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Sleep_detail;
import com.baiyi.watch.utils.StringUtils;

import java.util.List;

public class SleepReportView2 extends View {

	private float tb;
	private float interval_left_right;
	private float interval_bottom;// 离底部距离
	private Paint paint_histogram;// 柱状图Pain
	private Paint paint_dottedline;// 底部虚线Pain
	private Paint paint_separator;// 分割线Pain

	private List<Sleep_detail> dataList;

	private int separatorColor = 0xff666666;
	
	private int histogramColor1 = 0xff005faf;
	private int histogramColor2 = 0xffaaddf8;

	private float base;// 每个数值单位的高度
	private float base_h;
	
	 private OnSelectedListener listener;

	public SleepReportView2(Context context) {
		super(context);
		init(null);
	}

	public SleepReportView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(null);
	}

	public SleepReportView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(null);
	}

	public void init(List<Sleep_detail> dataList) {

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
		

	}

	protected void onDraw(Canvas c) {

		drawStraightLine(c);
		drawHistogram(c);
		drawDate(c);

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = (int) event.getX();
		float y = (int) event.getY();
		
        for (int i = 0; i < dataList.size(); i++) {
			float x1 = base_h * i + interval_left_right / 2.0f;
			float y2 = getHeight() - interval_bottom / 2;
			
			if (Math.abs(x - x1) < 2 * tb && y < y2) {
				if (listener != null) {
					Sleep_detail data = dataList.get(i);

					int deepValue = StringUtils.string2Int(data.getDeepSleepDuration());
					int lightValue = StringUtils.string2Int(data.getLightSleepDuration());
					if (0 < (deepValue+lightValue)) {
						listener.OnSelected(data);
						//ActivityUtil.showToast(getContext(), i+1+"");
					}
				}
				break;
			}
		}
//        if (event.getAction() == MotionEvent.ACTION_DOWN ||
//                event.getAction() == MotionEvent.ACTION_UP){
//            postInvalidate();
//        }
        return true;
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

	}

	/**
	 * 绘制柱状图
	 * 
	 * @param c
	 */
	public void drawHistogram(Canvas c) {
		if (dataList != null) {
			
			int max = 0;
			for (int i = 0; i < dataList.size(); i++) {
				Sleep_detail data = dataList.get(i);

				int deepValue = StringUtils.string2Int(data.getDeepSleepDuration());
				int lightValue = StringUtils.string2Int(data.getLightSleepDuration());
				if (max < (deepValue+lightValue)) {
					max = (deepValue+lightValue);
				}
			}
			if (max < 0) {
				max = 180;
			}
			base = (getHeight() - interval_bottom) / max;

			for (int i = 0; i < dataList.size(); i++) {
				Sleep_detail data = dataList.get(i);

				int deepValue = StringUtils.string2Int(data.getDeepSleepDuration());
				int lightValue = StringUtils.string2Int(data.getLightSleepDuration());
				float x = base_h * i + interval_left_right / 2.0f;
				float y1 = getHeight() - interval_bottom - deepValue * base;
				float y2 = y1 - lightValue * base;

				paint_histogram.setColor(histogramColor1);
				c.drawLine(x, getHeight() - interval_bottom, x, y1, paint_histogram);
				paint_histogram.setColor(histogramColor2);
				c.drawLine(x, y1, x, y2, paint_histogram);

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
					dateStr = dataList.get(i).getSleepBegin().substring(5, 10);
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
	public void refresh(List<Sleep_detail> dataList) {
		this.dataList = dataList;
		init(this.dataList);
		invalidate();
	}
	
	public interface OnSelectedListener{
		public void OnSelected(Sleep_detail sleep_detail);
	}
	
	public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
		this.listener = onSelectedListener;
	}

}

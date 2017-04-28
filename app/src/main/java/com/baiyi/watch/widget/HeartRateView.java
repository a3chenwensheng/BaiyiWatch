package com.baiyi.watch.widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Heartratedata;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

public class HeartRateView extends View {

	private float tb;
	private float interval_left_right;
	private float interval_bottom;// 离底部距离
	private Paint paint_date;
	private Paint paint_value;
	private Paint paint_line;
	private Paint paint_brokenline;
	private Paint paint_cubic;
	private Paint paint_circle;
	private Paint paint_dottedline;// 底部虚线Pain
	private Paint paint_dottedline2;// 背景虚线Pain
	private Paint paint_dottedline3;// 竖直虚线Pain
	private Paint paint_separator;// 分割线Pain

	private List<Heartratedata> dataList;

	private int fineLineColor = 0xffaaaaaa; // 深灰色
	private int fineLineColor2 = 0xffe5e5e5; // 浅灰色
	private int dateColor = 0xff666666; // 深灰色
	private int separatorColor = 0xff1064A0; // 蓝色
	private int brokenlineColor = 0xffff6e15; // 主题色
	private int circleColor = 0xffffffff; // 白色
	
	private float base;//每个数值单位的高度
	
    private Dot selectedDot;
    
    private int backgroundGridWidth = ActivityUtil.dip2px(getContext(),45);
    
    private OnSelectedListener listener;

	public HeartRateView(Context context) {
		super(context);
		init(null);
	}

	public HeartRateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(null);
	}

	public HeartRateView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(null);
	}

	public void init(List<Heartratedata> dataList) {

		this.dataList = dataList;
		Resources res = getResources();
		tb = res.getDimension(R.dimen.heart_rate_tb);
		interval_left_right = tb * 6.0f;
		interval_bottom = tb * 2.5f;
		base = (getHeight() - interval_bottom) / 180;

		paint_date = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_date.setStrokeWidth(tb * 2.8f);
		paint_date.setTextSize(ActivityUtil.dip2px(getContext(), 15));
		paint_date.setColor(dateColor);
		paint_date.setTextAlign(Paint.Align.CENTER);
		
		paint_value = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_value.setStrokeWidth(tb * 1.5f);
		paint_value.setTextSize(ActivityUtil.dip2px(getContext(), 10));
		paint_value.setColor(dateColor);
		paint_value.setTextAlign(Paint.Align.CENTER);
		
		paint_separator = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_separator.setStrokeWidth(tb * 0.15f);
		paint_separator.setTextSize(tb * 1.0f);
		paint_separator.setColor(separatorColor);

		paint_line = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_line.setStrokeWidth(tb * 0.15f);
		paint_line.setTextSize(tb * 1.2f);
		paint_line.setColor(fineLineColor);

		PathEffect effects = new DashPathEffect(new float[] { tb * 0.4f, tb * 0.4f, tb * 0.4f, tb * 0.4f }, tb * 0.1f);
		
		paint_dottedline = new Paint(Paint.ANTI_ALIAS_FLAG);
		//paint_dottedline.setPathEffect(effects);
		paint_dottedline.setStyle(Paint.Style.STROKE);
		paint_dottedline.setColor(separatorColor);
		paint_dottedline.setStrokeWidth(tb * 0.10f);
		
		paint_dottedline2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_dottedline2.setPathEffect(effects);
		paint_dottedline2.setStyle(Paint.Style.STROKE);
		paint_dottedline2.setColor(fineLineColor);
		paint_dottedline2.setStrokeWidth(tb * 0.10f);
		
		paint_dottedline3 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint_dottedline3.setPathEffect(effects);
		paint_dottedline3.setStyle(Paint.Style.STROKE);
		paint_dottedline3.setColor(separatorColor);
		paint_dottedline3.setStrokeWidth(tb * 0.10f);

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

		setLayoutParams(new LayoutParams((int) (25 * interval_left_right), LayoutParams.WRAP_CONTENT));
	}

	protected void onDraw(Canvas c) {

		drawStraightLine(c);
		drawDate(c);

		drawBrokenLine(c);

	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        //ActivityUtil.showToast(getContext(), "x-->"+ point.x +"   y-->"+ point.y);
        Region r = new Region();
        int width = backgroundGridWidth/2;
        if(dataList != null && !dataList.isEmpty()){
            for(Heartratedata heartratedata : dataList){
            	//x, 0, x, y, dataList.get(i)
            	float x = getX(heartratedata.getTime_begin());
				float y = getHeight() - interval_bottom - StringUtils.string2Int(heartratedata.getHeartrate()) * base;
            	Dot dot = new Dot((int)x, (int)y, (int)x, (int)y, StringUtils.string2Int(heartratedata.getHeartrate()));
            	
                r.set(dot.x-width,dot.y-width,dot.x+width,dot.y+width);
                if (r.contains(point.x,point.y) && event.getAction() == MotionEvent.ACTION_DOWN){
                    selectedDot = dot;
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_UP){
            postInvalidate();
        }
        return true;
    }

	/**
	 * 绘制竖线
	 * 
	 * @param c
	 */
	public void drawStraightLine(Canvas c) {

		// 头尾两条竖线
//		c.drawLine(0, interval_left_right * 1.0f, 0, getHeight() - interval_bottom - tb * 0.15f, paint_line);
//		c.drawLine(interval_left_right * 24, interval_left_right * 1.2f, interval_left_right * 48, getHeight() - interval_bottom - tb * 0.15f, paint_line);

		// 底部时间分隔线
		for (int i = 0; i <= 24; i += 1) {
			c.drawLine(interval_left_right * i + interval_left_right / 2.0f, getHeight() - interval_bottom + tb * 0.25f, interval_left_right * i + interval_left_right / 2.0f, getHeight() - interval_bottom - tb * 0.35f, paint_separator);
		}

		// 底部横条时间横实线
		Path path2 = new Path();
		path2.moveTo(0, getHeight() - interval_bottom);
		path2.lineTo(getWidth(), getHeight() - interval_bottom);
		c.drawPath(path2, paint_dottedline);
		
		//数据背景分割线
		base = (getHeight() - interval_bottom) / 180;
		
		paint_dottedline2.setColor(fineLineColor); 
		Path path3 = new Path();
		path3.moveTo(0, getHeight() - interval_bottom - 30 * base);
		path3.lineTo(getWidth(), getHeight() - interval_bottom - 30 * base);
		c.drawPath(path3, paint_dottedline2);
		
		paint_dottedline2.setColor(fineLineColor2); 
		Path path4 = new Path();
		path4.moveTo(0, getHeight() - interval_bottom - 60 * base);
		path4.lineTo(getWidth(), getHeight() - interval_bottom - 60 * base);
		c.drawPath(path4, paint_dottedline2);
		
		paint_dottedline2.setColor(fineLineColor2); 
		Path path5 = new Path();
		path5.moveTo(0, getHeight() - interval_bottom - 90 * base);
		path5.lineTo(getWidth(), getHeight() - interval_bottom - 90 * base);
		c.drawPath(path5, paint_dottedline2);
		
		paint_dottedline2.setColor(fineLineColor2); 
		Path path6 = new Path();
		path6.moveTo(0, getHeight() - interval_bottom - 120 * base);
		path6.lineTo(getWidth(), getHeight() - interval_bottom - 120 * base);
		c.drawPath(path6, paint_dottedline2);
		
		paint_dottedline2.setColor(fineLineColor2); 
		Path path7 = new Path();
		path7.moveTo(0, getHeight() - interval_bottom - 150 * base);
		path7.lineTo(getWidth(), getHeight() - interval_bottom - 150 * base);
		c.drawPath(path7, paint_dottedline2);
		
	}

	/**
	 * 绘制时间
	 * 
	 * @param c
	 */
	public void drawDate(Canvas c) {
		int hour = 0;
		for (int i = 0; i <= 24; i += 1) {
			
			String text = formatTime(hour % 24);
			c.drawText(text, interval_left_right * i + interval_left_right / 2.0f, getHeight() - interval_bottom + tb * 2.0f, paint_date);

			hour += 1;
		}

	}

	/**
	 * 绘制线框图
	 * 
	 * @param c
	 */
	public void drawBrokenLine(Canvas c) {

		if (dataList != null) {
			List<List<Heartratedata>> splitDatas = splitData(dataList);
			for (List<Heartratedata> list : splitDatas) {
				if (list.size() == 1) {
					float x = getX(list.get(0).getTime_begin());
					float y = getHeight() - interval_bottom - StringUtils.string2Int(list.get(0).getHeartrate()) * base;
					
					drawVerticalDashLine(c, x, y);
					drawCircle(c,x, y, paint_circle, list.get(0)); 
					
				}else {
					for (int i = 0; i < list.size() - 1; i++) {
					Heartratedata data1 = list.get(i);
					Heartratedata data2 = list.get(i + 1);

					float x1 = getX(data1.getTime_begin());
					float y1 = getHeight() - interval_bottom - StringUtils.string2Int(data1.getHeartrate()) * base;
					float x2 = getX(data2.getTime_begin());
					float y2 = getHeight() - interval_bottom - StringUtils.string2Int(data2.getHeartrate()) * base;

//					c.drawLine(x1, y1, x2, y2, paint_brokenline);
					
		    		float wt=(x1+x2)/2;
		        	Path path = new Path();  
		            path.moveTo(x1,y1); 
		            path.cubicTo(wt, y1, wt, y2,x2, y2); 
		            c.drawPath(path, paint_cubic);
		            
		            drawVerticalDashLine(c, x1, y1);
					drawCircle(c,x1, y1, paint_circle, data1);
					if (i == list.size() - 2) {
						drawVerticalDashLine(c, x2, y2);
						drawCircle(c,x2, y2, paint_circle, data2);
					}

				}

				}
			}
		}
	}
	
	private void drawCircle(Canvas c, float x, float y, Paint paint, Heartratedata heartratedata) {
		
		c.drawText(heartratedata.getHeartrate(), x, y - ActivityUtil.getFontHeight(paint_value) / 2, paint_value);
		
		if (selectedDot != null) {
			Region r = new Region();
			int width = backgroundGridWidth / 2;
			r.set(selectedDot.x - width, selectedDot.y - width, selectedDot.x + width, selectedDot.y + width);
			
			float y1 = getHeight() - interval_bottom - selectedDot.data * base;
			if (r.contains((int)x, (int)y) && (y1 == y)) {
				paint.setColor(brokenlineColor);
				c.drawCircle(x, y, 8, paint);
				if (listener != null) {
					listener.OnSelected(heartratedata);
				}
			}else {
				paint.setColor(brokenlineColor);
				c.drawCircle(x, y, 6, paint);
				paint.setColor(circleColor);
				c.drawCircle(x, y, 2, paint);
			}
			
		} else {
			paint.setColor(brokenlineColor);
			c.drawCircle(x, y, 6, paint);
			paint.setColor(circleColor);
			c.drawCircle(x, y, 2, paint);
		}
	}
	
	private void drawVerticalDashLine(Canvas c, float x, float y){
		Path path = new Path();
		path.moveTo(x, y);
		path.lineTo(x, getHeight() - interval_bottom);
		c.drawPath(path, paint_dottedline3);
	}

	private List<List<Heartratedata>> splitData(List<Heartratedata> datas) {
		List<List<Heartratedata>> splitDatas = new ArrayList<List<Heartratedata>>();
		List<Heartratedata> data = new ArrayList<Heartratedata>();
		boolean isFirst = true;
		if (datas != null) {
			for (int i = 0; i < datas.size(); i++) {
				Heartratedata heartrate1 = dataList.get(i);
				if (isFirst) {
					data.add(heartrate1);
					isFirst = false;
				}

				if (i + 1 < datas.size()) {
					Heartratedata heartrate2 = dataList.get(i + 1);

					if (StringUtils.string2Long(heartrate2.mTimebegin) - StringUtils.string2Long(heartrate1.mTimebegin) < 90 * 60 * 1000) {
						data.add(heartrate2);
						isFirst = false;
					} else {
						splitDatas.add(data);
						data = new ArrayList<Heartratedata>();
						isFirst = true;

					}
				}else {
					splitDatas.add(data);
					data = new ArrayList<Heartratedata>();
					isFirst = true;
				}

			}
		}

		return splitDatas;
	}

	// 获取开始绘图X轴距离
	private float getX(String time_json) {
		Date date = TimeUtils.jsonStr2StrDate(time_json);
		int hour = date.getHours();
		int min = date.getMinutes();
		return (interval_left_right * hour + interval_left_right / 60.0f * min) + interval_left_right / 2.0f;
	}
	
	// 格式化时间显示
	private String formatTime(int time) {
		String timeStr = "";
		if (time < 10) {
			timeStr = "0" + time + ":00";
		} else {
			timeStr = time + ":00";
		}
		return timeStr;
	}

	/*
	 * 清空，重绘睡眠图
	 */
	public void clear() {
		selectedDot = null;
		init(null);
		invalidate();
	}

	/*
	 * 刷新，重绘睡眠图
	 */
	public void refresh(List<Heartratedata> dataList) {
		selectedDot = null;
		this.dataList = dataList;
		init(this.dataList);
		invalidate();
	}
	
	class Dot{
        int x;
        int y;
        int data;
        int targetX;
        int targetY;
        int velocity = ActivityUtil.dip2px(getContext(),20);

        Dot(int x,int y,int targetX,int targetY,Integer data){
            this.x = x;
            this.y = y;
            setTargetData(targetX, targetY,data);
        }

        Point getPoint(){
            return new Point(x,y);
        }

        Dot setTargetData(int targetX,int targetY,Integer data){
            this.targetX = targetX;
            this.targetY = targetY;
            this.data = data;
            return this;
        }

        boolean isAtRest(){
            return (x==targetX)&&(y==targetY);
        }

        void update(){
            x = updateSelf(x, targetX, velocity);
            y = updateSelf(y, targetY, velocity);
        }
    }
	
	private int updateSelf(int origin, int target, int velocity){
        if (origin < target) {
            origin += velocity;
        } else if (origin > target){
            origin-= velocity;
        }
        if(Math.abs(target-origin)<velocity){
            origin = target;
        }
        return origin;
    }
	
	public interface OnSelectedListener{
		public void OnSelected(Heartratedata heartratedata);
	}
	
	public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
		this.listener = onSelectedListener;
	}

}


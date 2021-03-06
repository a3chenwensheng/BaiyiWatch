package com.baiyi.watch.widget;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.utils.MediaPlayTools;
import com.baiyi.watch.utils.RecordStrategy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordButton extends Button {

	private static final int MIN_RECORD_TIME = 1; // 最短录音时间，单位秒
	private static final int MAX_RECORD_TIME = 5; // 最长录音时间，单位秒
	private static final int RECORD_OFF = 0; // 不在录音
	private static final int RECORD_ON = 1; // 正在录音

	private Dialog mRecordDialog;
	private RecordStrategy mAudioRecorder;
	private Thread mRecordThread;
	private RecordListener listener;

	private int recordState = 0; // 录音状态
	private float recodeTime = 0.0f; // 录音时长，如果录音时间太短则录音失败
	private double voiceValue = 0.0; // 录音的音量值
	private boolean isCanceled = false; // 是否取消录音
	private float downY;

	private TextView dialogTextView;
	private ImageView dialogImg;
	private TextView dialogTimeTv;
	private Context mContext;

	public RecordButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		this.setText("按住 说话");
	}

	public void setAudioRecord(RecordStrategy record) {
		this.mAudioRecorder = record;
	}

	public void setRecordListener(RecordListener listener) {
		this.listener = listener;
	}

	// 录音时显示Dialog
	private void showVoiceDialog(int flag) {
		if (mRecordDialog == null) {
			mRecordDialog = new Dialog(mContext, R.style.RecordDialogstyle);
			mRecordDialog.setContentView(R.layout.dialog_record);
			dialogImg = (ImageView) mRecordDialog.findViewById(R.id.record_dialog_img);
			dialogTextView = (TextView) mRecordDialog.findViewById(R.id.record_dialog_txt);
			dialogTimeTv = (TextView) mRecordDialog.findViewById(R.id.record_dialog_time_txt);
		}
		switch (flag) {
		case 1:
			dialogImg.setImageResource(R.mipmap.record_cancel);
			dialogTextView.setText("松开手指可取消录音");
			this.setText("松开手指 取消录音");
			break;

		default:
			dialogImg.setImageResource(R.mipmap.record_animate_01);
			dialogTextView.setText("向上滑动可取消录音");
			this.setText("松开手指 完成录音");
			break;
		}
		dialogTextView.setTextSize(14);
		mRecordDialog.show();
	}

	// 录音时间太短时Toast显示
	private void showWarnToast(String toastText) {
		Toast toast = new Toast(mContext);
		View warnView = LayoutInflater.from(mContext).inflate(R.layout.toast_warn, null);
		TextView tv = (TextView) warnView.findViewById(R.id.toast_warn_tv);
		tv.setText(toastText);
		toast.setView(warnView);
		toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间
		toast.show();
	}

	// 开启录音计时线程
	private void callRecordTimeThread() {
		mRecordThread = new Thread(recordThread);
		mRecordThread.start();
	}

	// 录音Dialog图片随录音音量大小切换
	private void setDialogImage() {
		
		if (voiceValue < 600.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_01);
		} else if (voiceValue > 600.0 && voiceValue < 1000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_02);
		} else if (voiceValue > 1000.0 && voiceValue < 1200.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_03);
		} else if (voiceValue > 1200.0 && voiceValue < 1400.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_04);
		} else if (voiceValue > 1400.0 && voiceValue < 1600.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_05);
		} else if (voiceValue > 1600.0 && voiceValue < 1800.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_06);
		} else if (voiceValue > 1800.0 && voiceValue < 2000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_07);
		} else if (voiceValue > 2000.0 && voiceValue < 3000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_08);
		} else if (voiceValue > 3000.0 && voiceValue < 4000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_09);
		} else if (voiceValue > 4000.0 && voiceValue < 6000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_10);
		} else if (voiceValue > 6000.0 && voiceValue < 8000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_11);
		} else if (voiceValue > 8000.0 && voiceValue < 10000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_12);
		} else if (voiceValue > 10000.0 && voiceValue < 12000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_13);
		} else if (voiceValue > 12000.0) {
			dialogImg.setImageResource(R.mipmap.record_animate_14);
		}
	}

	// 录音线程
	private Runnable recordThread = new Runnable() {

		@Override
		public void run() {
			recodeTime = 0.0f;
			while (recordState == RECORD_ON) {
				{
					try {
						Thread.sleep(100);
						recodeTime += 0.1;
						// 获取音量，更新dialog
						timeHandler.sendEmptyMessage(1);
						if (!isCanceled) {
							voiceValue = mAudioRecorder.getAmplitude();
							recordHandler.sendEmptyMessage(1);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler recordHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setDialogImage();
		}
	};
	@SuppressLint("HandlerLeak")
	private Handler timeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int remainTime = (int) (1 + (5.0f - recodeTime));
			if (remainTime <= 0) {
				dialogTimeTv.setTextColor(getResources().getColorStateList(R.color.red));
				dialogTimeTv.setText("0 s");
				//TODO
				if (recordState == RECORD_ON) {
					recordState = RECORD_OFF;
					if (mRecordDialog.isShowing()) {
						mRecordDialog.dismiss();
					}
					mAudioRecorder.stop();
					mRecordThread.interrupt();
					voiceValue = 0.0;
					if (isCanceled) {
						mAudioRecorder.deleteOldFile();
					} else {
						if (listener != null) {
							listener.recordEnd(mAudioRecorder.getFilePath());
						}
					}
					isCanceled = false;
					RecordButton.this.setText("按住 说话");
				}
			}else {
				dialogTimeTv.setTextColor(getResources().getColorStateList(R.color.white));
				dialogTimeTv.setText(remainTime + " s");
			}
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下按钮
			MediaPlayTools.getInstance().stop();//停止播放
			if (recordState != RECORD_ON) {
				showVoiceDialog(0);
				downY = event.getY();
				if (mAudioRecorder != null) {
					mAudioRecorder.ready();
					recordState = RECORD_ON;
					mAudioRecorder.start();
					callRecordTimeThread();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE: // 滑动手指
			float moveY = event.getY();
			if (downY - moveY > 50) {
				isCanceled = true;
				showVoiceDialog(1);
			}
			if (downY - moveY < 20) {
				isCanceled = false;
				showVoiceDialog(0);
			}
			break;
		case MotionEvent.ACTION_UP: // 松开手指
			if (recordState == RECORD_ON) {
				recordState = RECORD_OFF;
				if (mRecordDialog.isShowing()) {
					mRecordDialog.dismiss();
				}
				mAudioRecorder.stop();
				mRecordThread.interrupt();
				voiceValue = 0.0;
				if (isCanceled) {
					mAudioRecorder.deleteOldFile();
				} else {
					if (recodeTime < MIN_RECORD_TIME) {
						showWarnToast("时间太短  录音失败");
						mAudioRecorder.deleteOldFile();
					} else if (recodeTime >= MAX_RECORD_TIME) {
						showWarnToast("时间过长  录音失败");
						mAudioRecorder.deleteOldFile();
					}else {
						if (listener != null) {
							listener.recordEnd(mAudioRecorder.getFilePath());
						}
					}
				}
				isCanceled = false;
				this.setText("按住 说话");
			}
			break;
		case MotionEvent.ACTION_CANCEL: // 松开手指
			stopRecord();
			break;
		}
		return true;
	}
	
	private void stopRecord(){
		recordState = RECORD_OFF;
		if (mRecordDialog.isShowing()) {
			mRecordDialog.dismiss();
		}
		mAudioRecorder.stop();
		mRecordThread.interrupt();
		voiceValue = 0.0;
		mAudioRecorder.deleteOldFile();
		isCanceled = false;
		this.setText("按住 说话");
	}

	public interface RecordListener {
		public void recordEnd(String filePath);
	}
}

package com.baiyi.watch.remind;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.dialog.DateTimePickerDialog;
import com.baiyi.watch.dialog.RemindModeDialog;
import com.baiyi.watch.dialog.TimePickerDialog;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.SettingAlert;
import com.baiyi.watch.net.BaseApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.ui.PermissionsActivity;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.AudioRecorder;
import com.baiyi.watch.utils.Constant;
import com.baiyi.watch.utils.FileUtil;
import com.baiyi.watch.utils.MediaPlayTools;
import com.baiyi.watch.utils.PermissionsChecker;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;
import com.baiyi.watch.widget.RecordButton;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import toasty.Toasty;

/**
 * 编辑腕表提醒 Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-3-28 16:30
 */
public class RemindEditWatchActivity extends BaseActivity implements OnClickListener {
    private static final int REQUEST_CODE = 0; // 请求码
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    private TextView mBackTv;// 返回
    private Button mSaveBtn;// 保存

    private RelativeLayout mTimeLayout;
    private RelativeLayout mModeLayout;
    private RecordButton mSpeakBtn;

    private TextView mAlertTitleTv;
    private TextView mTimeTv;
    private TextView mModeTv;

    private LinearLayout mPlayLayout;
    private ImageView mPlayImg;

    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;
    private CheckBox mCheckBox5;
    private Button mTitleElseBtn;

    private String[] mTitles;

    private TimePickerDialog mTimePickerDialog;// 时间选择器对话框
    private DateTimePickerDialog mDatePickerDialog;// 日期选择器对话框
    private RemindModeDialog mRemindModeDialog;// 自定义模式输入对话框

    private SettingAlert mSettingAlert;
    private Device mDevice;

    private String mIsMedicine = "true";

    private String mAlertType = "1";
    private String mWeekCycle = "0000000";
    private Calendar mCalendar = Calendar.getInstance();

    private String newLocalRecordPath;

    private AnimationDrawable anim = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_edit_watch);
        mPermissionsChecker = new PermissionsChecker(this);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);
        mSaveBtn = (Button) findViewById(R.id.save_btn);

        mTimeLayout = (RelativeLayout) findViewById(R.id.remind_edit_watch_time_layout);
        mModeLayout = (RelativeLayout) findViewById(R.id.remind_edit_watch_mode_layout);
        mSpeakBtn = (RecordButton) findViewById(R.id.speak_btn);

        mAlertTitleTv = (TextView) findViewById(R.id.remind_title_tv);
        mTimeTv = (TextView) findViewById(R.id.remind_edit_watch_time_tv);
        mModeTv = (TextView) findViewById(R.id.remind_edit_watch_mode_tv);
        mPlayLayout = (LinearLayout) findViewById(R.id.voice_play_statue_layout);
        mPlayImg = (ImageView) findViewById(R.id.voice_play_statue_img);

        mCheckBox1 = (CheckBox) findViewById(R.id.checkBox1);
        mCheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        mCheckBox3 = (CheckBox) findViewById(R.id.checkBox3);
        mCheckBox4 = (CheckBox) findViewById(R.id.checkBox4);
        mCheckBox5 = (CheckBox) findViewById(R.id.checkBox5);
        mTitleElseBtn = (Button) findViewById(R.id.title_else_btn);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mSpeakBtn.setAudioRecord(new AudioRecorder());

        mTitles = getResources().getStringArray(R.array.alert_titles);

        mDevice = (Device) getIntent().getSerializableExtra("device");
        mSettingAlert = (SettingAlert) getIntent().getSerializableExtra("SettingAlert");

        if (mSettingAlert != null) {
            if ("1".equals(mSettingAlert.getAlert_type())) {
                mAlertType = "1";
                mWeekCycle = "0000000";
            } else {
                mAlertType = "0";
                try {
                    mWeekCycle = mSettingAlert.getTime().split("\\+")[0];
                } catch (Exception e) {
                }
            }

        }

        showData(mSettingAlert);

        FileUtil.deleteDirectory(Constant.PATH_REMIND_ARM);//删除目录下所有录音文件

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);

        mModeLayout.setOnClickListener(this);
        mTimeLayout.setOnClickListener(this);
        mPlayLayout.setOnClickListener(this);

        mCheckBox1.setOnClickListener(this);
        mCheckBox2.setOnClickListener(this);
        mCheckBox3.setOnClickListener(this);
        mCheckBox4.setOnClickListener(this);
        mCheckBox5.setOnClickListener(this);
        mTitleElseBtn.setOnClickListener(this);

        mSpeakBtn.setRecordListener(new RecordButton.RecordListener() {

            @Override
            public void recordEnd(String filePath) {
                //ActivityUtil.showToast(mContext, filePath);
                newLocalRecordPath = filePath;
                mPlayLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.save_btn:
                save();
                break;
            case R.id.title_else_btn:
                //showCustomTitleDialog();
                break;
            case R.id.remind_edit_watch_mode_layout:
                showModeDialog();
                break;
            case R.id.remind_edit_watch_time_layout:
                if ("1".equals(mAlertType)) {
                    showDatePickerDialog();
                } else {
                    showTimePickerDialog();
                }
                break;
            case R.id.voice_play_statue_layout:
                if (!TextUtils.isEmpty(newLocalRecordPath)) {
                    playVoice(newLocalRecordPath);
                    ;
                } else {
                    downLoadRecod(mSettingAlert.getUrl());
                }
                break;
            case R.id.checkBox1:
            case R.id.checkBox2:
            case R.id.checkBox3:
            case R.id.checkBox4:
            case R.id.checkBox5:
                showCheckBox(v);
                break;
            default:
                break;
        }
    }

    private void showCheckBox(View v) {
        CheckBox checkBox = (CheckBox) v;
        if (!(checkBox.isChecked())) {
            checkBox.setChecked(true);
            return;
        }

        switch (v.getId()) {
            case R.id.checkBox1:
                mCheckBox1.setChecked(true);
                mCheckBox2.setChecked(false);
                mCheckBox3.setChecked(false);
                mCheckBox4.setChecked(false);
                mCheckBox5.setChecked(false);
                mAlertTitleTv.setText(mTitles[0]);
                break;
            case R.id.checkBox2:
                mCheckBox1.setChecked(false);
                mCheckBox2.setChecked(true);
                mCheckBox3.setChecked(false);
                mCheckBox4.setChecked(false);
                mCheckBox5.setChecked(false);
                mAlertTitleTv.setText(mTitles[1]);
                break;
            case R.id.checkBox3:
                mCheckBox1.setChecked(false);
                mCheckBox2.setChecked(false);
                mCheckBox3.setChecked(true);
                mCheckBox4.setChecked(false);
                mCheckBox5.setChecked(false);
                mAlertTitleTv.setText(mTitles[2]);
                break;
            case R.id.checkBox4:
                mCheckBox1.setChecked(false);
                mCheckBox2.setChecked(false);
                mCheckBox3.setChecked(false);
                mCheckBox4.setChecked(true);
                mCheckBox5.setChecked(false);
                mAlertTitleTv.setText(mTitles[3]);
                break;
            case R.id.checkBox5:
                mCheckBox1.setChecked(false);
                mCheckBox2.setChecked(false);
                mCheckBox3.setChecked(false);
                mCheckBox4.setChecked(false);
                mCheckBox5.setChecked(true);
                mAlertTitleTv.setText(mTitles[4]);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayTools.getInstance().stop();
    }


    /**
     * 显示时间选择器
     */
    private void showTimePickerDialog() {
        mTimePickerDialog = new TimePickerDialog(mContext, mCalendar);
        mTimePickerDialog.setTitleLineVisibility(View.INVISIBLE);
        mTimePickerDialog.setTitle("选择时间");
        mTimePickerDialog.setButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTimePickerDialog.dismiss();
            }
        }, "确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ActivityUtil.showToast(mContext, mTimePickerDialog.getTime());
                mTimeTv.setText(mTimePickerDialog.getTime());
                if (mCalendar == null) {
                    mCalendar = Calendar.getInstance();
                }
                mCalendar.set(Calendar.HOUR_OF_DAY, mTimePickerDialog.getHour());
                mCalendar.set(Calendar.MINUTE, mTimePickerDialog.getMinute());
                mTimePickerDialog.dismiss();

            }
        });
        mTimePickerDialog.show();

    }

    /**
     * 显示日期选择器
     */
    private void showDatePickerDialog() {
        mDatePickerDialog = new DateTimePickerDialog(mContext, mCalendar, true);
        mDatePickerDialog.setTitleLineVisibility(View.INVISIBLE);
        mDatePickerDialog.setTitle("选择时间");
        mDatePickerDialog.setButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatePickerDialog.dismiss();
            }
        }, "确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ActivityUtil.showToast(mContext, mDatePickerDialog.getDateTime());
                mTimeTv.setText(mDatePickerDialog.getDateTime());
                if (mCalendar == null) {
                    mCalendar = Calendar.getInstance();
                }
                mCalendar.set(Calendar.YEAR, mDatePickerDialog.getYear());
                mCalendar.set(Calendar.MONTH, mDatePickerDialog.getMonth() - 1);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDatePickerDialog.getDay());
                mCalendar.set(Calendar.HOUR_OF_DAY, mDatePickerDialog.getHour());
                mCalendar.set(Calendar.MINUTE, mDatePickerDialog.getMinute());

//				mDate = TimeUtils.string2Date(
//						("" + mDatePickerDialog.getYear()) + mDatePickerDialog.getMonth() + mDatePickerDialog.getDay() + mDatePickerDialog.getHour() + mDatePickerDialog.getMinute(), "yyyyMdHm");

                mDatePickerDialog.dismiss();

            }
        });
        mDatePickerDialog.show();

    }

    /**
     * 显示模式输入对话框
     */
    private void showModeDialog() {

        if (mRemindModeDialog != null) {
            mRemindModeDialog.dismiss();
        }
        mRemindModeDialog = new RemindModeDialog(mContext, mAlertType, mWeekCycle);
        mRemindModeDialog.setConfirmBtn(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // mFenceNameTv.setText(text);
                mWeekCycle = mRemindModeDialog.getCycleStr();
                mAlertType = mRemindModeDialog.getTypeStr();
                if ("1".equals(mAlertType)) {
                    mModeTv.setText("仅一次");
                    mTimeTv.setText(TimeUtils.date2Str(mCalendar.getTime(), "yyyy-MM-dd HH:mm"));
                } else {
                    mModeTv.setText(StringUtils.getWeekTips(mWeekCycle));
                    mTimeTv.setText(TimeUtils.date2Str(mCalendar.getTime(), "HH:mm"));
                }


                mRemindModeDialog.dismiss();
            }
        });
        mRemindModeDialog.show();

    }

    private void showData(SettingAlert settingAlert) {
        if (settingAlert != null) {

            mAlertTitleTv.setText(settingAlert.getName());

            if ("1".equals(settingAlert.getAlert_type())) {
                mModeTv.setText("仅一次");
                Date date = TimeUtils.string2Date(settingAlert.getTime(), "yyyyMMddHHmmss");
                if (null != date) {
                    mCalendar.setTime(date);
                } else {
                    mCalendar.setTime(new Date());
                }

                mTimeTv.setText(TimeUtils.date2Str(mCalendar.getTime(), "yyyy-MM-dd HH:mm"));
            } else {
                mModeTv.setText(formatMode(settingAlert.getTime()));
                mTimeTv.setText(formatTime(settingAlert.getTime()));
            }

            if (TextUtils.isEmpty(settingAlert.getUrl())) {
                mPlayLayout.setVisibility(View.GONE);
            } else {
                mPlayLayout.setVisibility(View.VISIBLE);
            }

//			if ("true".equals(settingAlert.getEnable())) {
//				mCheckBox.setChecked(true);
//			} else {
//				mCheckBox.setChecked(false);
//			}

        }

    }

    private String formatMode(String time) {

        String weekStr = "";
        String str = "";

        try {
            weekStr = time.split("\\+")[0];
        } catch (Exception e) {
        }
        if (weekStr.length() == 7) {
            str = StringUtils.getWeekTips(weekStr);
        } else {
            return "";
        }

        return str;
    }

    private String formatTime(String time) {
        String timeStr = "";

        try {
            timeStr = time.split("\\+")[1] + ":" + time.split("\\+")[2];
            mCalendar.set(Calendar.HOUR_OF_DAY, StringUtils.string2Int(time.split("\\+")[1]));
            mCalendar.set(Calendar.MINUTE, StringUtils.string2Int(time.split("\\+")[2]));
        } catch (Exception e) {
        }

        return timeStr;
    }

    private void save() {
        if (null == mSettingAlert) {
            ActivityUtil.showToast(mContext, "SettingAlert为空");
            return;
        }

        String name = mAlertTitleTv.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            //ActivityUtil.showToast(mContext, "请输入标题名称");

            //showCustomTitleDialog();

            return;
        }

        if (TextUtils.isEmpty(mSettingAlert.getUrl()) && TextUtils.isEmpty(newLocalRecordPath)) {
            //ActivityUtil.showToast(mContext, "请录制提醒语音");

            //showRecordDialog();
            return;
        }

        mSettingAlert.setIs_medicine(mIsMedicine);
        mSettingAlert.setName(name);
        if ("1".equals(mAlertType)) {
            mSettingAlert.setAlert_type("1");
            mSettingAlert.setCycle("2");
            mSettingAlert.setTime(TimeUtils.date2Str(mCalendar.getTime(), "yyyyMMddHHmm") + "00");
        } else {
            mSettingAlert.setAlert_type("0");
            mSettingAlert.setCycle("1");
            mSettingAlert.setTime(mWeekCycle + "+" + TimeUtils.date2Str(mCalendar.getTime(), "HH+mm") + "+");
        }

        mSettingAlert.setEnable("true");

        File file = null;
        if (!TextUtils.isEmpty(mSettingAlert.getUrl()) && TextUtils.isEmpty(newLocalRecordPath)) {

        } else {
            file = new File(newLocalRecordPath);
            if (!file.exists()) {
                ActivityUtil.showToast(mContext, "录音文件不存在，请重录");
                return;
            }
        }

        editAlert(mSettingAlert, file);

    }

    private void editAlert(SettingAlert alert, File file) {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            ActivityUtil.showToast(mContext, "请选择设备");
            return;
        }

        //showLoadingDialog("请求中...");
        DeviceApi.getInstance(mContext).editAlert(mDevice.mId, alert, file, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    ActivityUtil.showToast(mContext, "设置成功");
                    finish();
                } else {
                    ActivityUtil.showToast(mContext, result.getError_desc());
                }

            }
        });

    }

    private void downLoadRecod(String url) {
        if (!ActivityUtil.hasNetwork(mContext)) {
            Toasty.error(mContext, "请检查网络").show();
            return;
        }

        HttpUtils http = new HttpUtils();
        String[] array = url.split("\\/");
        final String fileName = array[array.length - 1];

        File file = new File(Constant.PATH_REMIND_ARM + "/down_" + fileName);
        if (file.exists()) {
            playVoice(Constant.PATH_REMIND_ARM + "/down_" + fileName);
            return;
        }

        try {
            HttpHandler handler = http.download(BaseApi.BASE_Url2 + url, Constant.PATH_REMIND_ARM + "/down_" + fileName, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                    false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                    new RequestCallBack<File>() {

                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                        }

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            //System.out.println("downLoad!!!");
                            playVoice(Constant.PATH_REMIND_ARM + "/down_" + fileName);
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                        }
                    });
        } catch (Exception e) {
        }

    }

    protected void playVoice(String fileLocalPath) {
        MediaPlayTools instance = MediaPlayTools.getInstance();
        if (instance.isPlaying()) {
            stopRecordAnimation();
            instance.stop();

            // 点击的是当前播放的item，停止播放
            if (fileLocalPath.equals(instance.getUrlPath())) {
                return;
            }

        }

        instance.setOnVoicePlayCompletionListener(new MediaPlayTools.OnVoicePlayCompletionListener() {

            @Override
            public void OnVoicePlayCompletion() {
                stopRecordAnimation();
            }

            @Override
            public void onVoicePlayInterrupt() {
                stopRecordAnimation();

            }
        });
        startRecordAnimation();
        instance.playVoice(fileLocalPath, false);

    }

    /**
     * 开启播放动画
     */
    private void startRecordAnimation() {
        //TODO
        //mPlayImg.setImageResource(R.drawable.sound_stop);
        mPlayImg.setImageResource(R.drawable.anim_chat_voice_left);
        anim = (AnimationDrawable) mPlayImg.getDrawable();
        anim.start();
    }

    /**
     * 停止播放动画
     */
    private void stopRecordAnimation() {
        //TODO
        //mPlayImg.setImageResource(R.drawable.sound_play);
        if (anim != null) {
            anim.stop();
        }
        mPlayImg.setImageResource(R.mipmap.voice_right3);
    }

}

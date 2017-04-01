package com.baiyi.watch.user;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.PersonApi;
import com.baiyi.watch.widget.toggle.ToggleButton;
import com.baiyi.watch.widget.toggle.ToggleButton.OnToggleChanged;

import toasty.Toasty;

/**
 * 推送管理Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-11-6 14:30
 */
public class SettingsPushActivity extends BaseActivity implements OnClickListener, OnToggleChanged {

    private TextView mBackTv;// 返回

    private ToggleButton mSOSToggle;// SOS异常推送
    private ToggleButton mFenceToggle;// 电子围栏异常推送
    private ToggleButton mAbnormalToggle;// 健康数据异常推送
    private ToggleButton mMessageToggle;// 家庭圈信息异常推送
    private ToggleButton mLowpowerToggle;// 低电量推送
    private ToggleButton mSystemToggle;// 系统信息推送

    private Person mPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_push);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);

        mSOSToggle = (ToggleButton) findViewById(R.id.sos_push_toggle);
        mFenceToggle = (ToggleButton) findViewById(R.id.fence_push_toggle);
        mAbnormalToggle = (ToggleButton) findViewById(R.id.abnormal_push_toggle);
        mMessageToggle = (ToggleButton) findViewById(R.id.message_push_toggle);
        mLowpowerToggle = (ToggleButton) findViewById(R.id.lowpower_push_toggle);
        mSystemToggle = (ToggleButton) findViewById(R.id.system_push_toggle);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);

        mSOSToggle.setOnToggleChanged(this);
        mFenceToggle.setOnToggleChanged(this);
        mAbnormalToggle.setOnToggleChanged(this);
        mMessageToggle.setOnToggleChanged(this);
        mLowpowerToggle.setOnToggleChanged(this);
        mSystemToggle.setOnToggleChanged(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            default:
                break;
        }

    }

    @Override
    public void onToggle(boolean on, ToggleButton v) {
        switch (v.getId()) {

            case R.id.sos_push_toggle:
                editPerson("push_sos_enable", v);
                break;
            case R.id.fence_push_toggle:
                editPerson("push_fence_enable", v);
                break;
            case R.id.abnormal_push_toggle:
                editPerson("push_abnormal_enable", v);
                break;
            case R.id.message_push_toggle:
                editPerson("push_message_enable", v);
                break;
            case R.id.lowpower_push_toggle:
                editPerson("push_lowpower_enable", v);
                break;
            case R.id.system_push_toggle:
                editPerson("push_system_enable", v);
                break;
            default:
                break;
        }

    }

    private void getPersonInfo() {
        if (mPerson == null) {
            Toasty.error(mContext, "请先登录").show();
            logout();
            return;
        }

        //showLoadingDialog("请求中...");
        PersonApi.getInstance(mContext).getPersonInfo(mPerson.mId, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    Object bm = result.getResult("Person");
                    if (bm != null && bm instanceof Person) {
                        mPerson = (Person) bm;// 转换成Person实体
                        //DB
                        MyApplication.getInstance().getPersonDaoInface().clearPersonTable();
                        MyApplication.getInstance().getPersonDaoInface().addPerson(mPerson);
                        showData(mPerson);
                    }
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                }

            }
        });

    }

    private void showData(Person person) {
        // TODO
        if (null == person) {
            return;
        }

        if ("true".equals(person.getPush_sos_enable())) {
            mSOSToggle.setToggleOn();
        } else {
            mSOSToggle.setToggleOff();
        }

        if ("true".equals(person.getPush_fence_enable())) {
            mFenceToggle.setToggleOn();
        } else {
            mFenceToggle.setToggleOff();
        }

        if ("true".equals(person.getPush_abnormal_enable())) {
            mAbnormalToggle.setToggleOn();
        } else {
            mAbnormalToggle.setToggleOff();
        }

        if ("true".equals(person.getPush_message_enable())) {
            mMessageToggle.setToggleOn();
        } else {
            mMessageToggle.setToggleOff();
        }

        if ("true".equals(person.getPush_lowpower_enable())) {
            mLowpowerToggle.setToggleOn();
        } else {
            mLowpowerToggle.setToggleOff();
        }

        if ("true".equals(person.getPush_system_enable())) {
            mSystemToggle.setToggleOn();
        } else {
            mSystemToggle.setToggleOff();
        }

    }


    private void editPerson(String key, final View view) {

        if (mPerson == null) {
            ((ToggleButton) view).toggle2();
            return;
        }

        //showLoadingDialog("处理中...");
        PersonApi.getInstance(mContext).editPerson(mPerson.mId, key, ((ToggleButton) view).isChecked() ? "1" : "0", new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
                ((ToggleButton) view).toggle2();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    Toasty.success(mContext, "修改成功").show();
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                    ((ToggleButton) view).toggle2();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取 成员信息
        getPersonInfo();
    }

}

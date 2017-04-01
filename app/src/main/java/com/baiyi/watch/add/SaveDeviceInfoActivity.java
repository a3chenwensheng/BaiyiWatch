package com.baiyi.watch.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.ActivityUtil;

/**
 * S1黄手环 完善设备信息Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2016-9-8 19:00
 */
public class SaveDeviceInfoActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回

    private EditText mNameEdit;
    private EditText mPhoneEdit;
    private EditText mAddressEdit;
    private EditText mCaseHistoryEdit;

    private Button mSaveBtn;

    private String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_device_info);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);

        mNameEdit = (EditText) findViewById(R.id.name_edit);
        mPhoneEdit = (EditText) findViewById(R.id.care_phone_edit);
        mAddressEdit = (EditText) findViewById(R.id.address_edit);
        mCaseHistoryEdit = (EditText) findViewById(R.id.casehistory_edit);

        mSaveBtn = (Button) findViewById(R.id.save_btn);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        imei = getIntent().getStringExtra("imei");

    }

    private void setListener() {

        mBackTv.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.save_btn:
                save();
            default:
                break;
        }
    }

    private void save() {

        if (TextUtils.isEmpty(mNameEdit.getText().toString().trim())) {
            ActivityUtil.showToast(mContext, "请填写姓名");
            mNameEdit.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mPhoneEdit.getText().toString().trim())) {
            ActivityUtil.showToast(mContext, "请填写监护人手机号码");
            mPhoneEdit.requestFocus();
            return;
        }

        saveDeciceInfo(mNameEdit.getText().toString().trim(), mPhoneEdit.getText().toString().trim(), mAddressEdit.getText().toString().trim(), mCaseHistoryEdit.getText().toString().trim());

    }

    private void saveDeciceInfo(String name, String care_phone, String address, String casehistory) {
        if (TextUtils.isEmpty(imei)) {
            return;
        }

        //showLoadingDialog("处理中...");
        DeviceApi.getInstance(mContext).saveDeciceInfo(imei, name, care_phone, address, casehistory, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    // TODO
                    finish();
                } else {
                    ActivityUtil.showToast(mContext, result.getError_desc());
                }

            }
        });

    }

}

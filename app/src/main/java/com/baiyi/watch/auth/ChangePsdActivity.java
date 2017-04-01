package com.baiyi.watch.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.PersonApi;

import toasty.Toasty;

public class ChangePsdActivity extends BaseActivity implements View.OnClickListener {

    private TextView mBackTv;
    private EditText mOldEdit;
    private EditText mNewPsdEdit;
    private EditText mConfirmNewPsdEdit;
    private Button mConfirmBtn;

    private Person mPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psd);

        initView();
        initData();
        setListener();

    }

    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);
        mOldEdit = (EditText) findViewById(R.id.old_psd_edit);
        mNewPsdEdit = (EditText) findViewById(R.id.new_psd_edit);
        mConfirmNewPsdEdit = (EditText) findViewById(R.id.new_psd_confirm_edit);
        mConfirmBtn = (Button) findViewById(R.id.change_psd_confirm_btn);

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
    }

    private void initData() {
        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.change_psd_confirm_btn:
                //TODO
                changePsd();
                break;
            default:
                break;
        }
    }

    private void changePsd() {

        final CharSequence oldPsd = mOldEdit.getText();
        final CharSequence newPsd = mNewPsdEdit.getText();
        final CharSequence newConfirmPsd = mConfirmNewPsdEdit.getText();

        if (TextUtils.isEmpty(oldPsd.toString().trim())) {
            mOldEdit.requestFocus();
            Toasty.warning(mContext, "请输入旧密码").show();
            return;
        }

        if (TextUtils.isEmpty(newPsd)) {
            mNewPsdEdit.requestFocus();
            Toasty.warning(mContext, "请输入新密码").show();
            return;
        }

        if (TextUtils.isEmpty(newConfirmPsd)) {
            mConfirmNewPsdEdit.requestFocus();
            Toasty.warning(mContext, "请再次输入新密码").show();
            return;
        }

        if (!newPsd.toString().equals(newConfirmPsd.toString())) {
            mConfirmNewPsdEdit.requestFocus();
            Toasty.warning(mContext, "两个新密码输入不一致！").show();
            return;
        }


        if (mPerson == null) {
            return;
        }

        //showLoadingDialog("处理中...");
        PersonApi.getInstance(mContext).changePassword(/*mMember4Show.mId, */oldPsd.toString(), newPsd.toString(), new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    // TODO
                    Toasty.success(mContext, "修改成功！").show();
                    logout();
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                }

            }
        });

    }


}

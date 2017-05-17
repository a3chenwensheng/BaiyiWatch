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
import com.baiyi.watch.net.GroupApi;
import com.baiyi.watch.net.HttpCallback;

import toasty.Toasty;

/**
 * 我是被邀请成员Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-3-21 10:00
 */
public class BeInvitedActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回

    private EditText mEditText;
    private Button mInvitedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beinvited);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);
        mEditText = (EditText) findViewById(R.id.invite_code_edit);
        mInvitedBtn = (Button) findViewById(R.id.invite_btn);
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    private void setListener() {
        mBackTv.setOnClickListener(this);
        mInvitedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.invite_btn:
                invite();
                break;
            default:
                break;
        }

    }

    private void invite() {
        final CharSequence code = mEditText.getText();

        if (TextUtils.isEmpty(code)) {
            Toasty.warning(mContext, "请输入6位邀请码").show();
            mEditText.requestFocus();
            return;
        }

        //showLoadingDialog("请求中...");
        GroupApi.getInstance(mContext).JoinGroupByCode(code.toString().trim(), new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    // TODO
                    Toasty.success(mContext, "邀请成功").show();
                    finish();
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                }

            }
        });
    }

}

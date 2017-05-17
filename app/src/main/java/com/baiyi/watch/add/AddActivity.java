package com.baiyi.watch.add;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;

/**
 * 添加选择项（添加新App成员，添加新设备）Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-3-23 16:00
 */
public class AddActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回

    private LinearLayout mAddAppLayout;
    private LinearLayout mAddWatchLayout;
    private LinearLayout mBeInvitedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);
        mAddAppLayout = (LinearLayout) findViewById(R.id.add_app_layout);
        mAddWatchLayout = (LinearLayout) findViewById(R.id.add_watch_layout);
        mBeInvitedLayout = (LinearLayout) findViewById(R.id.beinvited_layout);

    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);
        mAddAppLayout.setOnClickListener(this);
        mAddWatchLayout.setOnClickListener(this);
        mBeInvitedLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.add_watch_layout:
                redictToActivity(mContext, QRCodeIllustrationActivity.class, null);
                break;
            case R.id.add_app_layout:
                redictToActivity(mContext, AddAppMemberActivity.class, null);
                break;
            case R.id.beinvited_layout:
                redictToActivity(mContext, BeInvitedActivity.class, null);
                break;
            default:
                break;
        }

    }

}

package com.baiyi.watch.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.AppManager;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Group;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.AuthApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.ui.MainActivity;
import com.baiyi.watch.utils.ActivityUtil;

import statusbarutil.StatusBarUtil;
import toasty.Toasty;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mPhoneEdit;
    private EditText mPsdEdit;
    private Button mLoginBtn;
    private TextView mRegistTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
        setListener();

    }

    private void initView() {
        mPhoneEdit = (EditText) findViewById(R.id.phone_edit);
        mPsdEdit = (EditText) findViewById(R.id.password_edit);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mRegistTv = (TextView) findViewById(R.id.regist_tv);

    }

    private void setListener() {
        mLoginBtn.setOnClickListener(this);
        mRegistTv.setOnClickListener(this);
    }

    private void initData() {
        String accout = mSputil.getValue("account", "");
        mPhoneEdit.setText(accout);
        mPhoneEdit.setSelection(accout.length());
    }

    @Override
    protected void setStatusBar() {
        //StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setTranslucent(this, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.regist_tv:
                //TODO
                //redictToActivity(mContext, RegistActivity.class, null);
                break;
            default:
                break;
        }
    }

    private void login() {

        final CharSequence account = mPhoneEdit.getText();
        final CharSequence password = mPsdEdit.getText();

        if (TextUtils.isEmpty(account.toString().trim())) {
            mPhoneEdit.requestFocus();
            Toasty.warning(mContext, "请输入账号").show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPsdEdit.requestFocus();
            Toasty.warning(mContext, "请输入密码").show();
            return;
        }

        AuthApi.getInstance(mContext).login(account.toString().trim(), password.toString(), new HttpCallback() {
            @Override
            public void onComplete(BaseMessage result) {
                //mProgressBar.setVisibility(View.GONE);
                if (result.isSuccess()) {
                    // 登录成功
                    mSputil.setValue("account", account.toString().trim());
                    mSputil.setValue("password", password.toString());
                    mSputil.setValue("auth_type", "");
                    mSputil.setValue("auth_uid", "");

                    Object bm = result.getResult("Person");
                    if (bm != null && bm instanceof Person) {

                        Person person = (Person) bm;// 转换成Person实体

                        // db
                        ActivityUtil.cleartDB();
                        MyApplication.getInstance().getPersonDaoInface().addPerson(person);

                        if (person.mGroups != null && person.mGroups.size() > 0) {
                            for (Group group : person.mGroups) {
                                MyApplication.getInstance().getGroupDaoInface().addGroup(group);
                            }

                        }
                        Toasty.success(mContext, "登录成功").show();
                        redictToActivity(mContext, MainActivity.class, null);
                        finish();

                    }
                } else {
                    // 登录失败
                    if ("105".equals(result.getError_code())) {
                        Toasty.error(mContext, "密码错误").show();
                    } else {
                        Toasty.error(mContext, result.getError_desc()).show();
                    }

                }
            }

            @Override
            public void onError(String error) {
                //mProgressBar.setVisibility(View.GONE);
                Toasty.error(mContext, error).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().AppExit(getApplicationContext());
        super.onBackPressed();
    }
}

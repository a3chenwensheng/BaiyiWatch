package com.baiyi.watch.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseFragment;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.auth.ChangePsdActivity;
import com.baiyi.watch.dialog.EditTextDialog;
import com.baiyi.watch.member.ListMemberActivity;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.PersonApi;
import com.baiyi.watch.user.AboutActivity;
import com.baiyi.watch.user.SettingsPushActivity;
import com.baiyi.watch.utils.ActivityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import toasty.Toasty;

public class UserFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView mAvatarImv;
    private LinearLayout mNickNameLayout;
    private LinearLayout mPhoneLayout;
    private LinearLayout mPasswordLayout;
    private LinearLayout mMembersLayout;
    private LinearLayout mPushLayout;
    private LinearLayout mAboutLayout;
    private TextView mUserNameTv;
    private TextView mNickNameTv;
    private TextView mPhoneTv;
    private Button mLogoutBtn;

    private Person mPerson;

    private EditTextDialog mNicknameDialog;//修改昵称输入对话框

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_user, container, false);
        initView(view);
        initData();
        setListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于Fragment的onResume

        } else {
            // 相当于Fragment的onPause

        }
    }

    /**
     * 初始化布局
     */
    private void initView(View view) {
        mAvatarImv = (CircleImageView) view.findViewById(R.id.user_avatar_imv);
        mNickNameLayout = (LinearLayout) view.findViewById(R.id.nick_name_layout);
        mPhoneLayout = (LinearLayout) view.findViewById(R.id.phone_layout);
        mPasswordLayout = (LinearLayout) view.findViewById(R.id.password_layout);
        mMembersLayout = (LinearLayout) view.findViewById(R.id.members_layout);
        mPushLayout = (LinearLayout) view.findViewById(R.id.push_layout);
        mAboutLayout = (LinearLayout) view.findViewById(R.id.about_layout);
        mUserNameTv = (TextView) view.findViewById(R.id.user_name_tv);
        mNickNameTv = (TextView) view.findViewById(R.id.nick_name_tv);
        mPhoneTv = (TextView) view.findViewById(R.id.phone_tv);
        mLogoutBtn = (Button) view.findViewById(R.id.logout_btn);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
        showData(mPerson);
    }

    private void setListener() {
        mNickNameLayout.setOnClickListener(this);
        mPhoneLayout.setOnClickListener(this);
        mPasswordLayout.setOnClickListener(this);
        mMembersLayout.setOnClickListener(this);
        mPushLayout.setOnClickListener(this);
        mAboutLayout.setOnClickListener(this);
        mLogoutBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nick_name_layout:
                showNicknameDialog(mNickNameTv.getText().toString());
                break;
            case R.id.password_layout:
                redictToActivity(mContext, ChangePsdActivity.class, null);
                break;
            case R.id.members_layout:
                redictToActivity(mContext, ListMemberActivity.class, null);
                break;
            case R.id.push_layout:
                redictToActivity(mContext, SettingsPushActivity.class, null);
                break;
            case R.id.about_layout:
                redictToActivity(mContext, AboutActivity.class, null);
                break;
            case R.id.logout_btn:
                logout();
                break;
            default:
                break;

        }
    }

    /**
     * 修改昵称输入对话框
     */
    private void showNicknameDialog(String content) {
        mNicknameDialog = new EditTextDialog(mContext);
        mNicknameDialog.setTitleLineVisibility(View.INVISIBLE);
        mNicknameDialog.setTitle("修改昵称");
        mNicknameDialog.setInputType(InputType.TYPE_CLASS_TEXT);
        mNicknameDialog.setEditContent(content);
        mNicknameDialog.setButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mNicknameDialog.cancel();
            }
        }, "确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = mNicknameDialog.getText();
                if (text == null) {
                    mNicknameDialog.requestFocus();
                    Toasty.warning(mContext, "内容不能为空").show();
                } else {
                    mNickNameTv.setText(text);
                    mNicknameDialog.dismiss();

                    editPerson("nickname", text);
                }
            }
        });
        mNicknameDialog.show();

    }

    private void editPerson(final String key, final String value) {

        if (mPerson == null) {
            Toasty.error(mContext, "请先登录").show();
            logout();
            return;
        }

        //showLoadingDialog("处理中...");
        PersonApi.getInstance(mContext).editPerson(mPerson.mId, key, value, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
                Toasty.error(mContext, error).show();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    // TODO
                    // DB
                    ContentValues cv = new ContentValues();
                    cv.put(key, value);
                    MyApplication.getInstance().getPersonDaoInface().updatePerson(cv, "_id = ?", new String[]{mPerson.mId});

                    getPersonInfo();
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                }

            }
        });

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
                ///dismissLoadingDialog();
                Toasty.error(mContext, error).show();
            }

            @Override
            public void onComplete(BaseMessage result) {
                ////dismissLoadingDialog();
                if (result.isSuccess()) {
                    Object bm = result.getResult("Person");
                    if (bm != null && bm instanceof Person) {
                        mPerson = (Person) bm;// 转换成Person实体
                        // DB
                        MyApplication.getInstance().getPersonDaoInface().clearPersonTable();
                        MyApplication.getInstance().getPersonDaoInface().addPerson(mPerson);

                        showData(mPerson);
                    }
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                    if ("101".equals(result.getError_code())) {//未授权，退出
                        logout();
                    }
                }

            }
        });

    }

    private void showData(Person person) {

        if (null != person) {

            if (!TextUtils.isEmpty(person.getAvatar_url())) {
                String avtarUrl = person.getAvatar_url();
                if (!avtarUrl.contains("http")) {
                    avtarUrl = BaseApi.BASE_Url2 + person.getAvatar_url();
                }
                ImageLoader.getInstance().displayImage(avtarUrl, mAvatarImv, MyApplication.getInstance().getOptions(R.drawable.ic_avatar));
            }
            //mAvatarNickNameTv.setText((person.getUsername() == null ? "" : person.getUsername()) + "\n" + mSputil.getValue("auth_uid", "")  + "\n" + mSputil.getValue("union_id", ""));
            mUserNameTv.setText(person.getUsername() == null ? "" : person.getUsername());
            mNickNameTv.setText(person.getNickname() == null ? "" : person.getNickname());
            mPhoneTv.setText(person.getPhone() == null ? "" : person.getPhone());

        } else {
            logout();
        }

    }

}

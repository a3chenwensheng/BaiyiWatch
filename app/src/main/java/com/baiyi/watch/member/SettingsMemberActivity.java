package com.baiyi.watch.member;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Member4Show;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.GroupApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.PersonApi;
import com.baiyi.watch.utils.ActivityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import toasty.Toasty;

/**
 * 成员管理Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-3-23 14:30
 */
public class SettingsMemberActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回
    private CircleImageView mAvatarImv;
    private TextView mHeadNickNameTv;
    private TextView mUserNameTv;
    private TextView mNickNameTv;
    private TextView mPhoneTv;

    private RelativeLayout mDeleteLayout;

    private Member4Show mMember4Show;
    private Person mPerson;

    //private BaseDialog mDeleteMemberDialog;// 移出家庭圈对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_member);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);

        mAvatarImv = (CircleImageView) findViewById(R.id.member_avatar_imv);
        mHeadNickNameTv = (TextView) findViewById(R.id.member_head_nickname_tv);
        mUserNameTv = (TextView) findViewById(R.id.member_username_tv);
        mNickNameTv = (TextView) findViewById(R.id.member_nickname_tv);
        mPhoneTv = (TextView) findViewById(R.id.member_phone_tv);
        mDeleteLayout = (RelativeLayout) findViewById(R.id.delete_layout);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mMember4Show = (Member4Show) getIntent().getSerializableExtra("member4Show");
        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
        //mGroup = MyApplication.getInstance().getGroupDaoInface().viewGroup("iscurrent = ?", new String[] { "1" });

        //是否为管理员
        if ((mMember4Show.getGroup_ownerid()).equals(mPerson.mId)) {
            mDeleteLayout.setVisibility(View.VISIBLE);
        } else {
            mDeleteLayout.setVisibility(View.GONE);
        }

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);
        mAvatarImv.setOnClickListener(this);

        mDeleteLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.delete_layout:
                showDeleteMemberDialog();
                break;
            default:
                break;
        }

    }

    private void showDeleteMemberDialog() {
//		mDeleteMemberDialog = new BaseDialog(mContext);
//		mDeleteMemberDialog.setTitle("提示");
//		mDeleteMemberDialog.setMessage("是否确认移出家庭圈");
//		mDeleteMemberDialog.setTitleLineVisibility(View.INVISIBLE);
//		mDeleteMemberDialog.setButton1("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mDeleteMemberDialog.dismiss();
//			}
//		});
//		mDeleteMemberDialog.setButton2("确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				deleteMember();
//				mDeleteMemberDialog.dismiss();
//			}
//		});
//
//		mDeleteMemberDialog.show();
    }

    private void deleteMember() {

        if (mMember4Show == null) {
            Toasty.error(mContext, "Member为空").show();
            return;
        }

        if (TextUtils.isEmpty(mMember4Show.getGroup_id())) {
            Toasty.error(mContext, "Group为空").show();
            return;
        }

        //showLoadingDialog("请求中...");
        GroupApi.getInstance(mContext).deleteMember(/*mGroup.mId*/mMember4Show.getGroup_id(), mMember4Show.mId, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    // TODO
                    //getGroupMembers(mMember4Show.getGroup_ownerid());// 加载家庭圈所有成员
                    ActivityUtil.showToast(mContext, "成功移出家庭圈");
                    finish();
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();

                }

            }
        });

    }

    private void getMemberInfo() {
        if (mMember4Show == null) {
            Toasty.error(mContext, "Member为空").show();
            return;
        }

        //showLoadingDialog("请求中...");
        PersonApi.getInstance(mContext).getPersonInfo(mMember4Show.mId, new HttpCallback() {

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

        if (!TextUtils.isEmpty(person.getAvatar_url())) {
            String avtarUrl = person.getAvatar_url();
            if (!avtarUrl.contains("http")) {
                avtarUrl = BaseApi.BASE_Url2 + person.getAvatar_url();
            }
            ImageLoader.getInstance().displayImage(avtarUrl, mAvatarImv, MyApplication.getInstance().getOptions(R.drawable.ic_avatar));
        }
        mHeadNickNameTv.setText(person.getNickname() == null ? "" : person.getNickname());
        mNickNameTv.setText(person.getNickname() == null ? "" : person.getNickname());
        mPhoneTv.setText(person.getPhone() == null ? "" : person.getPhone());
        mUserNameTv.setText(person.getUsername());

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取 成员信息
        getMemberInfo();
    }

}

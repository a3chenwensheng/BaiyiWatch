package com.baiyi.watch.add;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.dialog.BaseDialog;
import com.baiyi.watch.model.Group;
import com.baiyi.watch.model.Groupinvitecode;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.GroupApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.ActivityUtil;

import toasty.Toasty;

/**
 * 
 * 添加新App成员Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-6-8 9:30
 * @version v2.0
 */
public class AddAppMemberActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回

	private EditText mPhoneEdit;
	private Button mPhoneInviteBtn;

	private Person mPerson;
	private Group mGroup;
	
	private BaseDialog mBaseDialog;// 未注册用户，发送短信邀请提示对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_app_member);
		initView();
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);

		mPhoneEdit = (EditText) findViewById(R.id.phone_edit);
		mPhoneInviteBtn = (Button) findViewById(R.id.invite_phone_btn);

	}

	/** 初始化数据 */
	private void initData() {

		mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
		mGroup = MyApplication.getInstance().getGroupDaoInface().viewGroup("iscurrent = ?", new String[]{"1"});

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mPhoneInviteBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.invite_phone_btn:
			inviteUserName();
			break;
		default:
			break;
		}

	}
	
	private void showBaseDialog() {
		mBaseDialog = new BaseDialog(mContext);
		mBaseDialog.setTitle("提示");
		mBaseDialog.setMessage("该号码尚未注册，是否通过发送邀请码邀请加入？");
		mBaseDialog.setTitleLineVisibility(View.INVISIBLE);
		mBaseDialog.setButton1("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mBaseDialog.dismiss();
			}
		});
		mBaseDialog.setButton2("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				inviteCode();
				mBaseDialog.dismiss();
			}
		});

		mBaseDialog.show();
	}

	/**
	 * 生成邀请码
	 */
	private void inviteCode() {
		if (TextUtils.isEmpty(mPhoneEdit.getText().toString().trim())) {
			Toasty.warning(mContext,  "请输入新成员手机号").show();
			mPhoneEdit.requestFocus();
			return;
		}

		if (mGroup == null || TextUtils.isEmpty(mGroup.mId)) {
			return;
		}

		//showLoadingDialog("请求中...");
		GroupApi.getInstance(mContext).inviteCode(mGroup.mId, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();

			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					Groupinvitecode groupinvitecode = (Groupinvitecode) result.getResult("Groupinvitecode");
					if (groupinvitecode != null && !TextUtils.isEmpty(groupinvitecode.getCode())) {
						sendSmsWithBody(mContext, mPhoneEdit.getText().toString().trim(),
								"【爱牵挂】 " + mPerson.getUsername() + " 邀请您加入爱牵挂家庭圈，一起关爱家人健康。操作提示：下载APP，注册并输入邀请码即可加入。APP地址：http//www.aiqiangua.com 邀请码：" + groupinvitecode.getCode() + "，24小时内有效。");
					}
				} else {
					Toasty.error(mContext, result.getError_desc()).show();
				}

			}
		});

	}

	private void inviteUserName() {
		if (TextUtils.isEmpty(mPhoneEdit.getText().toString().trim())) {
			Toasty.warning(mContext, "请输入新成员手机号").show();
			mPhoneEdit.requestFocus();
			return;
		}

		//showLoadingDialog("请求中...");
		GroupApi.getInstance(mContext).inviteMember(mPhoneEdit.getText().toString().trim(), null, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();

			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					Toasty.success(mContext, "邀请成功").show();
					// 重新加载成员列表
					///getGroupMembers();
					finish();
				} else {
					if ("302".equals(result.getError_code())) {
						Toasty.warning(mContext, "该用户已在家庭圈中").show();
					}else if ("1".equals(result.getError_code())){
						//未注册用户，短信邀请
						showBaseDialog();
					}else {
						Toasty.error(mContext, result.getError_desc()).show();
					}
				}

			}
		});

	}

	/**
	 * 调用系统界面，给指定的号码发送短信，并附带短信内容
	 * 
	 * @param context
	 * @param number
	 * @param body
	 */
	public void sendSmsWithBody(Context context, String number, String body) {
		// Intent sendIntent = new Intent("android.intent.action.SENDTO");
		// sendIntent.setType("vnd.android-dir/mms-sms");

		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(Uri.parse("smsto:" + number));
		sendIntent.putExtra("sms_body", body);
		context.startActivity(sendIntent);
	}

}

package com.baiyi.watch.add;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Group;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.GroupApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.utils.ActivityUtil;
import com.znq.zbarcode.CaptureActivity;

import org.json.JSONObject;

import java.util.List;

import toasty.Toasty;

/**
 * 二维码扫描说明 Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2016-3-29 14:00
 */
public class QRCodeIllustrationActivity extends BaseActivity implements OnClickListener {

    public static final int SCANNIN_GREQUEST_CODE = 1;

    private TextView mBackTv;// 返回

    private Button mScanBtn;

    private String imei;
    private String sim_phone;
    private String sim_phone_type;
    private String type;//设备型号

    private Person mPerson;
    private Group mGroup;
    private boolean mIsManager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_illustration);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);
        mScanBtn = (Button) findViewById(R.id.scan_btn);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
        mGroup = MyApplication.getInstance().getGroupDaoInface().viewGroup("iscurrent = ?", new String[]{"1"});

        //是否为管理员
        try {
            if ((mGroup.mOwnerId).equals(mPerson.mId)) {
                mIsManager = true;
            } else {
                mIsManager = false;
            }
        } catch (Exception e) {
            mIsManager = false;
        }

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);
        mScanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.scan_btn:
                if (mGroup == null || (mGroup != null && TextUtils.isEmpty(mGroup.mId))) {
                    //startActivityForResult(new Intent(mContext, QRCodeScanActivity.class), SCANNIN_GREQUEST_CODE);
                    startActivityForResult(new Intent(mContext, CaptureActivity.class), SCANNIN_GREQUEST_CODE);

                } else {
                    if (!mIsManager) {
                        //showAddWatchDialog();
                    } else {
                        //startActivityForResult(new Intent(mContext, QRCodeScanActivity.class), SCANNIN_GREQUEST_CODE);
                        startActivityForResult(new Intent(mContext, CaptureActivity.class), SCANNIN_GREQUEST_CODE);
                    }
                }
                break;

            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SCANNIN_GREQUEST_CODE) {
            //String code = data.getExtras().getString("result");
            //mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
            //Toasty.success(mContext, code).show();
            String code = data.getStringExtra(CaptureActivity.EXTRA_STRING);
            if (TextUtils.isEmpty(code)) {
                Toasty.error(mContext, "无效二维码").show();
                //showHelpTipsDialog("无效二维码");
                return;
            }

            if (code.length() != 20 && code.length() != 83) {
                Toasty.error(mContext, "无效二维码").show();
                //showHelpTipsDialog("无效二维码");
                return;
            }

            if (code.length() == 83) {
                try {
                    code = code.split("\\=")[1];
                } catch (Exception e) {
                }
            }

            validateDevice(code);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void validateDevice(final String deviceID) {

        if (!ActivityUtil.hasNetwork(mContext)) {
            ActivityUtil.showToast(mContext, "请检查网络");
            return;
        }

        //showLoadingDialog("验证中...");
        DeviceApi.getInstance(mContext).validateDevice(deviceID, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();

                if (result.isSuccess()) {

                    try {
						JSONObject obj = new JSONObject(result.getResultSrc());
						imei = obj.getString("imei");
						sim_phone = obj.getString("sim_phone");
						try {
							sim_phone_type = obj.getString("sim_phone_type");
						} catch (Exception e) {
							sim_phone_type = "";
						}
						type = obj.getString("type");
						Bundle bundle = new Bundle();
						bundle.putString("imei", imei);
						bundle.putString("sim_phone", sim_phone);
						bundle.putString("sim_phone_type", sim_phone_type);
						bundle.putString("type", type);
						bundle.putString("deviceID", deviceID);
						redictToActivity(mContext, RegistDeviceActivity.class, bundle);
                    } catch (Exception e) {
                        //showHelpTipsDialog("解析错误");
                    }
                } else {

                    if ("401".equals(result.getError_code())) {

                        //showHelpTipsDialog("无效二维码");
                    } else if ("402".equals(result.getError_code())) {

                        //showHelpTipsDialog("验证失败");
                    } else if ("403".equals(result.getError_code())) {

                        Toasty.warning(mContext, "设备已经激活").show();
                        try {
                            JSONObject obj = new JSONObject(result.getResultSrc());
                            String owner = obj.getString("owner");
                            String device_type = obj.getString("device_type");
                            List<String> groups = ParserServer.paserStrings(obj.getString("groups"));
                            if (groups == null || (groups != null && groups.size() < 1)) {
                                // TODO
                                inviteUser(owner);
                            } else {
                                //showTipsDialog("该设备已被" + owner + "绑定，如需关注该设备请联系TA。");
                                if ("S1".equals(device_type)) {
                                    imei = "";
                                    try {
                                        imei = deviceID.substring(0, 15);
                                    } catch (Exception e) {
                                    }
                                    findDeviceInfo(imei);
                                }
                            }
                        } catch (Exception e) {
                        }

                    } else if ("404".equals(result.getError_code())) {
                        ActivityUtil.showToast(mContext, "其它错误");
                    }
                }

            }
        });

    }

    private void findDeviceInfo(final String imei) {
        if (TextUtils.isEmpty(imei)) {
            return;
        }

        //showLoadingDialog("请求中...");
        DeviceApi.getInstance(mContext).getDeviceInfo3(imei, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    try {
                        JSONObject obj = new JSONObject(result.getResultSrc());
                        String name = "";
                        if (obj.has("name")) {
                            name = obj.getString("name");
                        }

                        String care_phone = "";
                        if (obj.has("care_phone")) {
                            care_phone = obj.getString("care_phone");
                        }

                        String address = "";
                        if (obj.has("address")) {
                            address = obj.getString("address");
                        }

                        String case_history = "";
                        if (obj.has("case_history")) {
                            case_history = obj.getString("case_history");
                        }

                        //showDeviceInfoDialog(name, care_phone, address, case_history);
                    } catch (Exception e) {
                    }

                } else {
                    if ("209".equals(result.getError_code())) {
                        if (mIsManager) {
                            //showSaveDeviceInfoDialog("是否完善设备信息？", imei);
                        }
                    } else {
                        ActivityUtil.showToast(mContext, result.getError_desc());
                    }
                }

            }
        });

    }

    private void inviteUser(String userid) {
        if (TextUtils.isEmpty(userid)) {
            return;
        }

        //showLoadingDialog("请求中...");
        GroupApi.getInstance(mContext).inviteMember(null, userid, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    ActivityUtil.showToast(mContext, "邀请成功");
                    //TODO 选择当前device


                    finish();

                    //去掉group... 20160704
                    //getGroupMembers();// 重新加载成员列表
                    ///getGroupDevices();// 重新加载设备列表
                } else {
                    if ("302".equals(result.getError_code())) {
                        ActivityUtil.showToast(mContext, "该用户已在家庭圈中");
                    } else {
                        ActivityUtil.showToast(mContext, result.getError_desc());
                    }
                }

            }
        });

    }

}

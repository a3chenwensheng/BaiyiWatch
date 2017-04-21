package com.baiyi.watch.member;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.watch.adapter.MembersAdapter;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Group4Show;
import com.baiyi.watch.model.Member4Show;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.GroupApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.net.PersonApi;
import com.baiyi.watch.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import toasty.Toasty;

/**
 * 成员列表（App成员）Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-4-18 14:30
 */
public class ListMemberActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回

    private SwipeRefreshLayout mSwipeRefreshLayout;// 谷歌下拉刷新控件
    private ListView mListView;//
    private MembersAdapter mMembersAdapter;

    private List<Member4Show> listMember4Show = new ArrayList<Member4Show>();

    private Person mPerson;

    //private BaseDialog mDisbandGroupDialog;// 解散家庭圈对话框
    //private BaseDialog mLeaveGroupDialog;// 退出家庭圈对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_members);
        initView();
        initData();
        setListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.member_swipeRefreshLayout);
        // 设置下拉刷新控件卷内的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_color, R.color.theme_color, R.color.theme_color, R.color.theme_color);
        mListView = (ListView) findViewById(R.id.members_listView);
        // listview滑动冲突
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    mSwipeRefreshLayout.setEnabled(true);
                else
                    mSwipeRefreshLayout.setEnabled(false);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);

        mMembersAdapter = new MembersAdapter(mContext, mPerson, listMember4Show);
        mListView.setAdapter(mMembersAdapter);
    }

    private void setListener() {
        mBackTv.setOnClickListener(this);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Member4Show member = listMember4Show.get(position);
                if (TextUtils.isEmpty(member.mId)) {
                    return;
                }

                if ((member.mId).equals(mPerson.mId)) {
                    //TODO
                    ///redictToActivity(mContext, SettingsPersonActivity.class, null);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("member4Show", listMember4Show.get(position));
                    redictToActivity(mContext, SettingsMemberActivity.class, bundle);
                }
            }
        });

        // 设置下拉刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMembers();
            }
        });

        mMembersAdapter.setOnLeaveListener(new MembersAdapter.OnLeaveListener() {

            @Override
            public void onLeave(Member4Show member4Show) {
                showLeaveGroupDialog(member4Show);
            }

            @Override
            public void onDisband(Member4Show member4Show) {
                showDisbandGroupDialog(member4Show);
            }
        });
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
    protected void onResume() {
        super.onResume();
        getAllMembers();

    }

    // 过滤自己
    private void filterSelf(List<Member4Show> listMember) {
        if (mPerson == null) {
            return;
        }

        for (Member4Show member4Show : listMember) {
            if (member4Show.mId.equals(mPerson.mId)) {
                listMember.remove(member4Show);
                break;
            }
        }
    }

    private void getAllMembers() {
        if (mPerson == null || TextUtils.isEmpty(mPerson.mId)) {
            mSwipeRefreshLayout.setRefreshing(false);
            Toasty.error(mContext, "请登录").show();
            logout();
            return;
        }

        //showLoadingDialog("载入家庭成员中...");
        PersonApi.getInstance(mContext).getAllMembers(mPerson.mId, new HttpCallback() {
            @Override
            public void onError(String error) {
                mSwipeRefreshLayout.setRefreshing(false);
                //dismissLoadingDialog();
                Toasty.error(mContext, error).show();
            }

            @Override
            public void onComplete(BaseMessage result) {
                mSwipeRefreshLayout.setRefreshing(false);
                //dismissLoadingDialog();
                if (result.isSuccess()) {

                    // 更新DB
                    ArrayList<Group4Show> group4ShowList = null;
                    try {
                        group4ShowList = ParserServer.paserGroup4Shows(result.getResultSrc());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    List<Member4Show> listMember = new ArrayList<Member4Show>();
                    if (group4ShowList != null) {
                        for (Group4Show group4Show : group4ShowList) {
                            if (group4Show.mMemebers != null && !group4Show.mMemebers.isEmpty()) {
                                for (Member4Show member4Show : group4Show.mMemebers) {
                                    member4Show.setGroup_name(group4Show.getName());
                                    member4Show.setGroup_id(group4Show.mId);
                                    member4Show.setGroup_ownerid(group4Show.mOwnerId);
                                }

                                listMember.addAll(group4Show.mMemebers);
                            } else {
                                //添加一个空的member4Show,用来只显示group信息
                                Member4Show member4Show = new Member4Show();
                                member4Show.mId = "";
                                member4Show.setGroup_name(group4Show.getName());
                                member4Show.setGroup_id(group4Show.mId);
                                member4Show.setGroup_ownerid(group4Show.mOwnerId);

                                listMember.add(member4Show);

                            }

                        }

                    }

                    listMember4Show.clear();
                    filterSelf(listMember);
                    listMember4Show.addAll(listMember);
                    mMembersAdapter.notifyDataSetChanged();

                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                }
            }
        });
    }

    private void showLeaveGroupDialog(final Member4Show member4Show) {
//		if (mLeaveGroupDialog != null) {
//			mLeaveGroupDialog.dismiss();
//		}
//		mLeaveGroupDialog = new BaseDialog(mContext);
//		mLeaveGroupDialog.setTitle("提示");
//		mLeaveGroupDialog.setMessage("是否确认退出家庭圈");
//		mLeaveGroupDialog.setTitleLineVisibility(View.INVISIBLE);
//		mLeaveGroupDialog.setButton1("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mLeaveGroupDialog.dismiss();
//			}
//		});
//		mLeaveGroupDialog.setButton2("确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				leaveGroup(member4Show);
//				mLeaveGroupDialog.dismiss();
//			}
//		});
//
//		mLeaveGroupDialog.show();

        Toasty.warning(mContext, "此功能研发中").show();
    }

    private void showDisbandGroupDialog(final Member4Show member4Show) {
//		if (mDisbandGroupDialog != null) {
//			mDisbandGroupDialog.dismiss();
//		}
//		mDisbandGroupDialog = new BaseDialog(mContext);
//		mDisbandGroupDialog.setTitle("提示");
//		mDisbandGroupDialog.setMessage("是否确认解散家庭圈");
//		mDisbandGroupDialog.setTitleLineVisibility(View.INVISIBLE);
//		mDisbandGroupDialog.setButton1("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mDisbandGroupDialog.dismiss();
//			}
//		});
//		mDisbandGroupDialog.setButton2("确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				disbandGroup(member4Show);
//				mDisbandGroupDialog.dismiss();
//			}
//		});
//
//		mDisbandGroupDialog.show();
        Toasty.warning(mContext, "此功能研发中").show();
    }

    private void leaveGroup(Member4Show member4Show) {
        if (TextUtils.isEmpty(member4Show.getGroup_id())) {
            ActivityUtil.showToast(mContext, "Group为空");
            return;
        }
        //showLoadingDialog("请求中...");
        GroupApi.getInstance(mContext).leaveGroup(member4Show.getGroup_id(), new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    listMember4Show.clear();
                    mMembersAdapter.notifyDataSetChanged();

                    getAllDevices();
                    getAllMembers();
                } else {
                    ActivityUtil.showToast(mContext, result.getError_desc());
                }

            }
        });

    }

    private void disbandGroup(Member4Show member4Show) {
        if (TextUtils.isEmpty(member4Show.getGroup_id())) {
            ActivityUtil.showToast(mContext, "Group为空");
            return;
        }
        //showLoadingDialog("请求中...");
        GroupApi.getInstance(mContext).disbandGroup(member4Show.getGroup_id(), new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {

                    listMember4Show.clear();
                    mMembersAdapter.notifyDataSetChanged();

                    getAllDevices();
                    getAllMembers();
                } else {
                    ActivityUtil.showToast(mContext, result.getError_desc());
                }

            }
        });

    }


    private void getAllDevices() {
        if (mPerson == null || TextUtils.isEmpty(mPerson.mId)) {
            return;
        }

        //showLoadingDialog("载入设备中...");
        PersonApi.getInstance(mContext).getAllDevices(mPerson.mId, new HttpCallback() {
            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
                Toasty.error(mContext, error).show();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {

                    // 更新DB
                    ArrayList<Group4Show> group4ShowList = null;
                    try {
                        group4ShowList = ParserServer.paserGroup4Shows(result.getResultSrc());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //TODO
                    // 添加家庭圈DB

                    List<Device> listD = new ArrayList<Device>();
                    if (group4ShowList != null) {
                        for (Group4Show group4Show : group4ShowList) {
                            if (group4Show.mDevices != null && !group4Show.mDevices.isEmpty()) {
                                for (Device device : group4Show.mDevices) {
                                    device.mId = device.getImei();
                                    Person owner = new Person();
                                    owner.setAvatar_url(device.getAvatar_url());
                                    device.mOwner = owner;
                                    device.setGroup_name(group4Show.getName());
                                    device.setGroup_id(group4Show.mId);
                                    device.setGroup_ownerid(group4Show.mOwnerId);
                                }
                            }
                            listD.addAll(group4Show.mDevices);
                        }

                    }

                    if (!listD.isEmpty()) {
                        // DB
                        // 当前设备
                        Device mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[]{"1"});
                        boolean hasDevice = false;//退出或解散家庭圈后， 当前设备是否还在家庭

                        MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
                        for (Device device : listD) {
                            MyApplication.getInstance().getDeviceDaoInface().addDevice(device);

                            if (mDevice != null && device.mId.equals(mDevice.mId)) {
                                hasDevice = true;
                            }
                        }

                        if (!hasDevice) {
                            mDevice = null;
                        }

                        // 恢复默认查看腕表
                        ContentValues cv = new ContentValues();
                        cv.put("iscurrent", 1);
                        if (mDevice == null || (mDevice != null && TextUtils.isEmpty(mDevice.mId))) {
                            // 取第一个腕表，当默认查看腕表
                            Device device = MyApplication.getInstance().getDeviceDaoInface().viewDevice("_id != ?", new String[]{""});
                            if (null != device && !TextUtils.isEmpty(device.mId)) {
                                MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[]{device.mId});
                            }
                        } else {
                            MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[]{mDevice.mId});
                        }

                    } else {
                        MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
                    }
                } else {
                    Toasty.error(mContext, result.getError_desc()).show();
                    MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
                    MyApplication.getInstance().getGroupDaoInface().clearGroupTable();

                }
            }
        });
    }

}

package com.baiyi.watch.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.dialog.AdDialog;
import com.baiyi.watch.dialog.RemindModeDialog;
import com.baiyi.watch.model.Ad;
import com.baiyi.watch.net.AdApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.HttpCallback;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ViewPager mVpHome;
    private BottomNavigationBar mBottomNavigationBar;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private DataFragment dataFragment = new DataFragment();
    private ReportFragment reportFragment = new ReportFragment();
    private DevicesFragment devicesFragment = new DevicesFragment();
    private UserFragment userFragment = new UserFragment();

    private AdDialog mAdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVpHome = (ViewPager) findViewById(R.id.vp_home);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_data, "数据"))
                .addItem(new BottomNavigationItem(R.drawable.ic_report, "报告"))
                .addItem(new BottomNavigationItem(R.drawable.ic_device, "设备"))
                .addItem(new BottomNavigationItem(R.drawable.ic_user, "我"))
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mVpHome.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });


        mFragmentList.add(dataFragment);
        mFragmentList.add(reportFragment);
        mFragmentList.add(devicesFragment);
        mFragmentList.add(userFragment);

        mVpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mVpHome.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        mVpHome.setOffscreenPageLimit(4);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLastAd();
            }
        }, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (dataFragment.isVisible()){
//            dataFragment.refreshData();
//        }
    }

    private void getLastAd() {

        // showLoadingDialog("请求中...");
        AdApi.getInstance(mContext).getLastAd(new HttpCallback() {

            @Override
            public void onError(String error) {
                // dismissLoadingDialog();
            }

            @Override
            public void onComplete(BaseMessage result) {
                // dismissLoadingDialog();
                if (result.isSuccess()) {
                    try {
                        Ad ad = (Ad) result.getResult("Ad");
                        showAdDialog(ad);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    /**
     * 显示广告弹屏
     */
    private void showAdDialog(final Ad ad) {

        if (mAdDialog != null) {
            mAdDialog.dismiss();
        }
        mAdDialog = new AdDialog(mContext, ad.getPic_url());
        mAdDialog.setCanceledOnTouchOutside(false);
        mAdDialog.setRedictTo(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Uri uri = Uri.parse(ad.getUrl());
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);

                mAdDialog.dismiss();
            }
        });
        mAdDialog.show();

    }
}

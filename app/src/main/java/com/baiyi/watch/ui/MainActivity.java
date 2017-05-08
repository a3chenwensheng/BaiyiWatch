package com.baiyi.watch.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ViewPager mVpHome;
    private BottomNavigationBar mBottomNavigationBar;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private DataFragment dataFragment = new DataFragment();
    private ReportFragment reportFragment = new ReportFragment();
    private DevicesFragment devicesFragment = new DevicesFragment();
    private UserFragment userFragment = new UserFragment();

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
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (dataFragment.isVisible()){
//            dataFragment.refreshData();
//        }
    }
}

package com.baiyi.watch.aqgs2;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.baiyi.watch.auth.LoginActivity;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.Constant;
import com.baiyi.watch.utils.Sputil;
import com.nostra13.universalimageloader.core.ImageLoader;

import statusbarutil.StatusBarUtil;

/**
 * 应用程序Activity的基类
 */
public class BaseActivity extends AppCompatActivity {

    protected static String TAG;
    public static final String MESSAGE_RECEIVED_ACTION = "com.baiyi.watch.aqgs2.MESSAGE_RECEIVED_ACTION";
    /**
     * 屏幕的宽度、高度、密度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    protected Context mContext;
    protected Sputil mSputil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();

        mContext = this;
        TAG = this.getClass().getSimpleName();

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;


        if (null == mSputil) {
            mSputil = new Sputil(mContext, Constant.PRE_NAME);
        }


    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * Activity跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void redictToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }


    protected void logout() {
        ActivityUtil.cleartDB();// 清空数据库
        NotificationManager nm =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();//清除所有Notification

        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().clearMemoryCache();

        redictToActivity(mContext, LoginActivity.class, null);
        //AppManager.getAppManager().finishActivity(MainActivity.class);
        finish();
    }

}

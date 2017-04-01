package com.baiyi.watch.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.auth.LoginActivity;
import com.baiyi.watch.model.Person;

import statusbarutil.StatusBarUtil;

/**
 * 欢迎Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-3-18 10:00
 */
public class SplashActivity extends BaseActivity {

    private static final long DELAY_TIME = 2500L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);


        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(DELAY_TIME / 5);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
//                if (mSputil.isFirstInstall(mContext) || mSputil.isFirstStart(mContext)) {// 第一次安装或升级了APP
//                    // 跳到App介绍页面。（viewPager几张介绍图片）
//                    redirectByTime(GuideActivity.class);//
//                    //redirectByTime(BindHelpActivity.class);
//                    mSputil.setInstalled(mContext);
//                    mSputil.setStarted(mContext);
//
//                } else {
                    Person person = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
                    if (person != null && !TextUtils.isEmpty(person.mId)) {
                        redirectByTime(MainActivity.class);//
                    } else {
                        redirectByTime(LoginActivity.class);//
                    }

//                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });

    }

    /**
     * 根据时间进行页面跳转
     */
    private void redirectByTime(final Class<?> targetActivity) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO
                redictToActivity(SplashActivity.this, targetActivity, null);
                finish();
            }
        }, DELAY_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setStatusBar() {
        //StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setTranslucent(this, 0);
    }

}

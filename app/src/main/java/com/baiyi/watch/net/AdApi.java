package com.baiyi.watch.net;

import android.content.Context;

public class AdApi extends BaseApi {

    public static AdApi mInstance;

    public static AdApi getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AdApi(context);
        }
        return mInstance;
    }

    public AdApi(Context context) {
        init(context);
    }

    /**
     * 获取Ad
     *
     * @param bt
     */
    public void getLastAd(HttpCallback bt) {
        String url = AD_URL + "AqgNews/api/last_ad.do";
        doRequest(url, null, bt);
    }

}

package com.baiyi.watch.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.SettingSosNumber;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.sosnumber.SOSNumberEditActivity;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.widget.toggle.ToggleButton;
import com.baiyi.watch.widget.toggle.ToggleButton.OnToggleChanged;

import java.util.List;

import toasty.Toasty;

public class SOSNumberAdapter extends BaseAdapter {
    List<SettingSosNumber> datas;
    Context context;

    Device device;

    public SOSNumberAdapter(Context context, List<SettingSosNumber> datas, Device device) {
        this.datas = datas;
        this.context = context;
        this.device = device;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int arg0) {
        return datas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = View.inflate(context, R.layout.item_sos_number, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.sos_name_tv);
            holder.numberTv = (TextView) convertView.findViewById(R.id.sos_number_tv);
            holder.toggleBtn = (ToggleButton) convertView.findViewById(R.id.sos_number_toggle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SettingSosNumber sosNumber = datas.get(position);


        if ("true".equals(sosNumber.getDial_flag())) {
            holder.toggleBtn.setToggleOn();
        } else {
            holder.toggleBtn.setToggleOff();
        }

        if (TextUtils.isEmpty(sosNumber.getName())) {
            holder.nameTv.setText("亲情号" + sosNumber.getSeqid());
        } else {
            holder.nameTv.setText(sosNumber.getName());
        }

        if (TextUtils.isEmpty(sosNumber.getNum())) {
            holder.numberTv.setText("点击设置");
        } else {
            holder.numberTv.setText(sosNumber.getNum());
        }

        holder.toggleBtn.setOnToggleChanged(new OnToggleChanged() {

            @Override
            public void onToggle(boolean on, ToggleButton view) {
                if (TextUtils.isEmpty(sosNumber.getName()) || TextUtils.isEmpty(sosNumber.getName())) {
                    view.toggle2();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SettingSosNumber", sosNumber);
                    //bundle.putSerializable("device", mDevice);
                    redictToActivity(context, SOSNumberEditActivity.class, bundle);
                } else {
                    sosNumber.setDial_flag(view.isChecked() ? "true" : "false");
                    editSosNumber(sosNumber);
                }

            }
        });

        return convertView;
    }

    public void redictToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private void editSosNumber(SettingSosNumber settingSosNumber) {

        if (device == null || TextUtils.isEmpty(device.mId)) {
            ActivityUtil.showToast(context, "请选择设备");
            return;
        }

        // showLoadingDialog("处理中...");
        DeviceApi.getInstance(context).editSosNumber(device.mId, settingSosNumber, new HttpCallback() {

            @Override
            public void onError(String error) {
                // dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                // dismissLoadingDialog();
                if (result.isSuccess()) {
                    // finish();
                    Toasty.success(context, "修改成功！").show();
                    notifyDataSetChanged();
                } else {
                    Toasty.error(context, result.getError_desc()).show();
                }

            }
        });

    }

    public static class ViewHolder {
        public TextView nameTv;
        public TextView numberTv;
        public ToggleButton toggleBtn;
    }

}

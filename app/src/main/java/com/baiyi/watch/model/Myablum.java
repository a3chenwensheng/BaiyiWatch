package com.baiyi.watch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Myablum implements Parcelable {
	public long time;
	public String url;
	public String subject;

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(time);
		dest.writeString(url);
		dest.writeString(subject);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<Myablum> CREATOR = new Creator<Myablum>() {
		// 重写Creator
		@Override
		public Myablum createFromParcel(Parcel source) {
			Myablum p = new Myablum();
			p.time = source.readLong();
			p.url = source.readString();
			p.subject = source.readString();
			return p;
		}

		@Override
		public Myablum[] newArray(int size) {
			return new Myablum[size];
		}
	};

}

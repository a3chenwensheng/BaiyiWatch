package com.baiyi.watch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VoiceInfo implements Parcelable{
	private String name;
	private String voice_url;
	private String time;
	private int    isOther;
	private String length;
	private String icon;
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public String getVoice_url() {
		return voice_url;
	}
	
	public void setVoice_url(String voice_url) {
		this.voice_url = voice_url;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public int isOther() {
		return isOther;
	}
	
	public void setOther(int isOther) {
		this.isOther = isOther;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(voice_url);
		dest.writeString(time);
		dest.writeInt(isOther);
		dest.writeString(length);
		dest.writeString(icon);
	}

	public static final Creator<VoiceInfo> CREATOR = new Creator<VoiceInfo>() {
		//重写Creator
        @Override
        public VoiceInfo createFromParcel(Parcel source) {   
            VoiceInfo p = new VoiceInfo();   

            p.name = source.readString();   
            p.voice_url = source.readString();
            p.time = source.readString();
            p.isOther = source.readInt();
            p.length = source.readString();
            p.icon=source.readString();
            return p;   

        }   

        @Override
        public VoiceInfo[] newArray(int size) {   
            return null;   
        }
    };   	
}
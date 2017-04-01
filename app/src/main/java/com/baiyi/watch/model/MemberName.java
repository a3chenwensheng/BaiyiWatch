package com.baiyi.watch.model;

import java.io.Serializable;

//import com.utils.GlobalValue;

public class MemberName implements Serializable {
	
	private static final long serialVersionUID = 5527473759221823217L;

	private String name;
	private String deviceID;
	private boolean isSelected;

	public MemberName(String name, String deviceID, boolean isSelected, String nickname) {
		this.deviceID = deviceID;
		this.isSelected = isSelected;

		if (nickname != null && nickname.length() > 0 && !nickname.equals("null")) {
			this.name = nickname;
		} else if (name != null && name.length() > 0) {
			this.name = name;
		} else {
//			this.name = GlobalValue.sAcount;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}

package com.baiyi.watch.model;

public class LocPersonData {
	// 称呼
	private String name;
	// 图片
	private String image;

	public LocPersonData(String name, String image) {
		super();
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
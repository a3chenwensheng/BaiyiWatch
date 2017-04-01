package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * 
 * @author cws
 *
 */
public class SettingsItem implements Serializable {
	private int id;
	private String name;
	private int imageId;

	public SettingsItem() {

	}

	public SettingsItem(int id, String name, int imageId) {
		this.id = id;
		this.name = name;
		this.imageId = imageId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

}

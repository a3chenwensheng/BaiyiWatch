package com.baiyi.watch.model;

import java.util.ArrayList;

/**
 * mxj
 * 
 * @author Administrator
 * 
 */
public class Cells {
	private String celltowers;
	private String mnctype;
	
	public ArrayList<Celltower> mCelltowers;

	public String getCelltowers() {
		return celltowers;
	}

	public void setCelltowers(String celltowers) {
		this.celltowers = celltowers;
	}

	public String getMnctype() {
		return mnctype;
	}

	public void setMnctype(String mnctype) {
		this.mnctype = mnctype;
	}

}

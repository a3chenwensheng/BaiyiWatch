package com.baiyi.watch.model;

/**
 * mxj
 * @author Administrator
 *
 */
public class Locationcache {
	private String _id;// id | String
	private String cells;// 地址 | String
	private String country;// 国家 | String
	private String region;// 省份 | String
	private String city;// 城市 | String
	private String county;// 区域 | String
	private String street;// 街道 | String
	private String street_number;// 街道号 | String
	private String address;// 地址 | String
	private String point;// 坐标 | Point
	private String accuracy;// 精度 | Int
	private String created_at;// 创建时间 | DateTime
	
	public String mId;
	public Point mPoint;
	public Cells mCells; //待解析
	public String mCreatedAt;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getCells() {
		return cells;
	}
	public void setCells(String cells) {
		this.cells = cells;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet_number() {
		return street_number;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}

package com.baiyi.watch.model;

import java.io.Serializable;

public class Goods implements Serializable {
	private String _id;
	private String name;
	private String on_sale;
	private String price;
	private String service_date;

	public String mId;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOn_sale() {
		return on_sale;
	}

	public void setOn_sale(String on_sale) {
		this.on_sale = on_sale;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getService_date() {
		return service_date;
	}

	public void setService_date(String service_date) {
		this.service_date = service_date;
	}

}

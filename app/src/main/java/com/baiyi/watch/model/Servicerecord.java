package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * 
 * @author mxj
 *
 */
public class Servicerecord implements Serializable {
	private String _id;// ObjectId
	private String device_id;
	private String service_date;
	private String created_at;
	private String end_at;
	private String $created_by;
	private String $modified_by;

	public String mId;
	public String mEndAt;
	public String mCreatedAt;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getService_date() {
		return service_date;
	}

	public void setService_date(String service_date) {
		this.service_date = service_date;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getEnd_at() {
		return end_at;
	}

	public void setEnd_at(String end_at) {
		this.end_at = end_at;
	}

	public String get$created_by() {
		return $created_by;
	}

	public void set$created_by(String $created_by) {
		this.$created_by = $created_by;
	}

	public String get$modified_by() {
		return $modified_by;
	}

	public void set$modified_by(String $modified_by) {
		this.$modified_by = $modified_by;
	}

}

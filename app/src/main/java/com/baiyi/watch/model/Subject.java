package com.baiyi.watch.model;

import java.io.Serializable;

public class Subject implements Serializable {

	private String Id;
	private int order;
	private String title;
	private String answer;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}

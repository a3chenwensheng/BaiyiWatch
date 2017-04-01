package com.baiyi.watch.net;

public interface HttpCallback {
	
	public void onComplete(BaseMessage result);
	
	public void onError(String error);

}
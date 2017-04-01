package com.baiyi.watch.model;

public class Watchchat
{

	public String time;
	public String message;
	public String min;
	public boolean isOther;
	public boolean isVoice;
	public int textType;
	public String icon;

	public boolean qun = false;
	public String name;

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMin()
	{
		return min;
	}

	public void setMin(String min)
	{
		this.min = min;
	}

	public boolean isOther()
	{
		return isOther;
	}

	public void setOther(boolean isOther)
	{
		this.isOther = isOther;
	}

	public boolean isVoice()
	{
		return isVoice;
	}

	public void setVoice(boolean isVoice)
	{
		this.isVoice = isVoice;
	}

}

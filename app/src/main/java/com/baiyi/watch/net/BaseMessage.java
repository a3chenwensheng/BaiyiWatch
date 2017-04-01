package com.baiyi.watch.net;

import android.util.Log;

import com.baiyi.watch.model.Pagemodel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseMessage {
	private String success;  //请求是否成功
	private String obj_name; //对应的实体类名
	private String error_code; // 错误码
	private String error_desc; // 错误描述 
	private String error_url;  // 错误URL 
	private Pagemodel page;  //页码信息
	private Map<String, Object> resultMap; //单个实体对象
	private Map<String, ArrayList<? extends Object>> resultList; //实体列表
	
	private String resultSrc;
	
	private String jsonStr;//
	
	public BaseMessage () {
		this.resultMap = new HashMap<String, Object>();
		this.resultList = new HashMap<String, ArrayList<? extends Object>>();
	}
	
	@Override
	public String toString () {
		return success + " | " + obj_name + " | " + resultSrc;
	}
	
	public boolean isSuccess () {
		if (success != null && success.equals("true")) {
			return true;
		}
		return false;
	}
	
	public String getSuccess () {
		if (success == null) {
			return "";
		}
		return success;
	}
	
	public void setSuccess (String success) {
		this.success = success;
	}
	
	public String getObjName () {
		return this.obj_name;
	}
	
	public void setObjName (String obj_name) {
		this.obj_name = obj_name;
	}
	
	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_desc() {
		return error_desc;
	}

	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}

	public String getError_url() {
		return error_url;
	}

	public void setError_url(String error_url) {
		this.error_url = error_url;
	}

	public Pagemodel getPage() {
		return page;
	}

	public void setPage(String pageStr) {
		if (pageStr != null && JsonUtil.isJson(pageStr)) {
			page = JsonUtil.fromJson(pageStr, Pagemodel.class);
		}
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getResultSrc () {
		return this.resultSrc;
	}
	
	public Object getResult (String modelName) {
		Object model = this.resultMap.get(modelName);
		// catch null exception
		if (model == null) {
			Log.i("", "==Message data is empty=");
		}
		return model;
	}
	
	public ArrayList<? extends Object> getResultList (String modelName) {
		ArrayList<? extends Object> modelList = this.resultList.get(modelName);
		// catch null exception
		if (modelList == null || modelList.size() == 0) {
			Log.i("", "===Message data list is empty");
		}
		return modelList;
	}
	
	public void setResult (String result) throws Exception {
		this.resultSrc = result;
		String jsonKey = getObjName ();
		if (result.length() > 0 && jsonKey != null) {
			String modelName = JsonUtil.getModelName(jsonKey);
			JSONObject modelJsonObject = new JSONObject(result);
			modelName = judgeClassName(modelName, modelJsonObject);
			String modelClassName = HttpUtil.model_package + modelName;
			Object obj = JsonUtil.json2model(modelClassName, modelJsonObject);
			Object obj2 = ParserServer.paserObjcet(modelName, obj);
			this.resultMap.put(modelName, obj2);
		}
	}
	
	public void setResults (String result) throws Exception {
		this.resultSrc = result;
		String jsonKey = getObjName ();
		if (result.length() > 0 && jsonKey != null) {
			JSONArray modelJsonArray = new JSONArray(result);
			ArrayList<Object> modelList = new ArrayList<Object>();
			
			for (int i = 0; i < modelJsonArray.length(); i++) {
				JSONObject modelJsonObject = modelJsonArray.optJSONObject(i);
				String modelName = JsonUtil.getModelName(jsonKey);
				modelName = judgeClassName(modelName, modelJsonObject);
				String modelClassName = HttpUtil.model_package + modelName;
				Object obj = JsonUtil.json2model(modelClassName, modelJsonObject);
				modelList.add(ParserServer.paserObjcet(modelName, obj));
			}
			this.resultList.put(JsonUtil.getModelName(jsonKey), modelList);
		}
	}
	
    public String judgeClassName(String name, JSONObject jo) throws Exception {
    	if (name.equals("Page") || name.equals("Message")) { 
    		if (jo.has("_cls") && !jo.isNull("_cls")) {
    			String type = jo.getString("_cls");
    			return getTypeName(type);
    		}
    	}
    	return name;
    }
	
	public String getTypeName(String name) {
	  String str = name.substring(name.lastIndexOf(".") + 1);
	  str = str.toLowerCase();
	  return AppUtil.ucfirst(str);
	}
    
}
package com.baiyi.watch.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Iterator;


public class JsonUtil {
    private static Gson gson ;
    
    /**
     * 将Json字符串根据对象类名转换为相应的实体类
     * @author WeiGanChao
     * @param <T>
     * @param jsonStr
     * @param classOfT
     * @return
     */
    public static<T> T fromJson(String jsonStr,Class<T> classOfT){
    	initGsonInstance();
    	return gson.fromJson(jsonStr, classOfT);
    }
    
    /**
     * 将Json字符串根据对象类型转换为相应的实体类
     * @author WeiGanChao
     * @param <T>
     * @param jsonStr
     * @param typeOfT
     * @return
     */
    public static<T> T fromJson(String jsonStr, Type typeOfT){
    	initGsonInstance();
    	return gson.fromJson(jsonStr, typeOfT);
    }
    
	
	/**
	 * 判断是否是json
	 * 
	 * @param json
	 * @return
	 */
    public static boolean isJson(String json) {
		if (!TextUtils.isEmpty(json) && json.contains("{")
				&& json.contains("}")) {
			return true;
		} else {
			return false;
		}
	}
    
    /**
     * 将实体类转换为Json字符串
     * @author WeiGanChao
     * @param responseVo
     * @return
     */
    public static String toJson(Object object){
    	initGsonInstance();
    	return gson.toJson(object);
    }
    
    /**
     * @author WeiGanChao
     * 初始化Gson实例
     */
    private static void initGsonInstance(){
    	if(gson==null){
    		GsonBuilder builder = new GsonBuilder();
//    		builder.setLongSerializationPolicy(LongSerializationPolicy.DEFAULT);  
//    		builder.serializeSpecialFloatingPointValues();
//    		builder.setDateFormat("yyyy-MM-dd HH:mm");
//    		gson = builder.serializeNulls().create();
    		
    		gson = builder.create();
    	}
    }   
    
	@SuppressWarnings("unchecked")
	public static Object json2model (String modelClassName, JSONObject modelJsonObject) throws Exception  {
		// auto-load model class
		Object modelObj = (Object) Class.forName(modelClassName).newInstance();
		Class<? extends Object> modelClass = modelObj.getClass();
		// auto-setting model fields
		
		Iterator<String> it = modelJsonObject.keys();
		while (it.hasNext()) {
			try {
				String varField = it.next();
				String varValue = modelJsonObject.getString(varField);
				Field field = modelClass.getDeclaredField(varField);
				field.setAccessible(true); // have private to be accessable
				field.set(modelObj, varValue);
			} catch (Exception e) {
				continue;
			}
		}
		return modelObj;
	}
	
	public static String getModelName (String str) {
//		String[] strArr = str.split("\\W");
//		if (strArr.length > 0) {
//			str = strArr[0];
//		}
		return AppUtil.ucfirst(str);
	}
}

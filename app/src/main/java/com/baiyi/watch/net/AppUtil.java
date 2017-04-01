package com.baiyi.watch.net;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ParseException;

import org.apache.http.HttpEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class AppUtil
{
	// 存储emali
	public static String EMAIL = "";
	public static String PHONE = "";

	/* md5 加密 */
	static public String md5(String str)
	{
		MessageDigest algorithm = null;
		try
		{
			algorithm = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		if (algorithm != null)
		{
			algorithm.reset();
			algorithm.update(str.getBytes());
			byte[] bytes = algorithm.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte b : bytes)
			{
				hexString.append(Integer.toHexString(0xFF & b));
			}
			return hexString.toString();
		}
		return "";

	}

	/* 首字母大写 */
	static public String ucfirst(String str)
	{
		if (str != null && str.length() > 0)
		{
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity, final String defaultCharset) throws IOException, ParseException
	{
		if (entity == null)
		{
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null)
		{
			return "";
		}
		// gzip logic start
		if (entity.getContentEncoding().getValue().contains("gzip"))
		{
			instream = new GZIPInputStream(instream);
		}
		// gzip logic end
		if (entity.getContentLength() > Integer.MAX_VALUE)
		{
			throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
		}
		int i = (int) entity.getContentLength();
		if (i < 0)
		{
			i = 4096;
		}
		String charset = EntityUtils.getContentCharSet(entity);
		if (charset == null)
		{
			charset = defaultCharset;
		}
		if (charset == null)
		{
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}
		Reader reader = new InputStreamReader(instream, charset);
		CharArrayBuffer buffer = new CharArrayBuffer(i);
		try
		{
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1)
			{
				buffer.append(tmp, 0, l);
			}
		} finally
		{
			reader.close();
		}
		return buffer.toString();
	}

	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity) throws IOException, ParseException
	{
		return gzipToString(entity, null);
	}

	public static SharedPreferences getSharedPreferences(Context ctx)
	{
		return ctx.getSharedPreferences("com.app.demos.sp.global", Context.MODE_PRIVATE);
	}

	public static SharedPreferences getSharedPreferences(Service service)
	{
		return service.getSharedPreferences("com.app.demos.sp.global", Context.MODE_PRIVATE);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// 业务逻辑

	/* 获取 Session Id */
	// static public String getSessionId () {
	// Customer customer = Customer.getInstance();
	// return customer.getSid();
	// }

	/* 获取 Message */
	static public BaseMessage getMessage2(String jsonStr) throws Exception
	{
		BaseMessage message = new BaseMessage();
		JSONObject jsonO = null;
		try
		{
			long time = System.currentTimeMillis();
			jsonO = new JSONObject(jsonStr);
			if (jsonO != null)
			{
				if (jsonO.has("message"))
				{
					message.setResult(jsonO.getString("message"));
				}
				// if (jsonO.has("objs"))
				// {
				// message.setResults(jsonO.getString("objs"));
				// }
			}
		} catch (JSONException e)
		{
			throw new Exception("Json format error");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return message;
	}

	/* 获取 Message */
	static public BaseMessage getMessage(String jsonStr, String url) throws Exception
	{
		BaseMessage message = new BaseMessage();
		JSONObject jsonO = null;
		try
		{
			long time = System.currentTimeMillis();
			jsonO = new JSONObject(jsonStr);
			message.setJsonStr(jsonStr);
			if (jsonO != null)
			{
				if (jsonO.has("success"))
				{
					message.setSuccess(jsonO.getString("success"));
				}
				if (jsonO.has("obj_name"))
				{
					message.setObjName(jsonO.getString("obj_name"));
				}
				if (jsonO.has("page"))
				{
					message.setPage(jsonO.getString("page"));
				}
				if (jsonO.has("error_code"))
				{
					message.setError_code(jsonO.getString("error_code"));
				}
				if (jsonO.has("error_desc"))
				{
					message.setError_desc(jsonO.getString("error_desc"));
				}
				if (jsonO.has("error_url"))
				{
					message.setError_url(jsonO.getString("error_url"));
				}
				if (jsonO.has("obj"))
				{
					message.setResult(jsonO.getString("obj"));
				}
				if (jsonO.has("objs"))
				{
					message.setResults(jsonO.getString("objs"));
				}
				
				
				
			}
		} catch (JSONException e)
		{
			throw new Exception("Json format error");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return message;
	}

	/* Model 数组转化成 Map 列表 */
	static public List<? extends Map<String, ?>> dataToList(List<? extends HttpCallback> data, String[] fields)
	{
		ArrayList<HashMap<String, ?>> list = new ArrayList<HashMap<String, ?>>();
		for (Object item : data)
		{
			list.add((HashMap<String, ?>) dataToMap(item, fields));
		}
		return list;
	}

	/* Model 转化成 Map */
	static public Map<String, ?> dataToMap(Object data, String[] fields)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		try
		{
			for (String fieldName : fields)
			{
				Field field = data.getClass().getDeclaredField(fieldName);
				field.setAccessible(true); // have private to be accessable
				map.put(fieldName, field.get(data));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}

	/* 判断int是否为空 */
	static public boolean isEmptyInt(int v)
	{
		Integer t = new Integer(v);
		return t == null ? true : false;
	}

	/* 获取毫秒数 */
	public static long getTimeMillis()
	{
		return System.currentTimeMillis();
	}

	/* 获取耗费内存 */
	public static long getUsedMemory()
	{
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return total - free;
	}
	
}
package com.baiyi.watch.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baiyi.watch.aqgs2.MyApplication;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@SuppressWarnings("rawtypes")
public class AppClient {
	
	// compress strategy
	final private static int CS_NONE = 0;
	final private static int CS_GZIP = 1;
	
	private String apiUrl;
	private HttpParams httpParams;
	private HttpClient httpClient;
	private int timeoutConnection = 30000;
	private int timeoutSocket = 30000;
	private int compress = CS_NONE;
	
	// charset default utf8
	private String charset = HTTP.UTF_8;
	
	public AppClient (String url) {
		initClient(url);
	}
	
	public AppClient (String url, String charset, int compress) {
		initClient(url);
		this.charset = charset; // set charset
		this.compress = compress; // set strategy
	}
	
	private void initClient (String url) {
		// initialize API URL
		if (url.startsWith("http")) {
			this.apiUrl = url;
		} else {
			this.apiUrl = BaseApi.BASE_Url + url;
		}
		
		// set client timeout
		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
		// init client
		httpClient = new DefaultHttpClient(httpParams);
	}
	
	public void useWap () {
		HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
	
	public String get () throws Exception {
		try {
			HttpGet httpGet = headerFilter(new HttpGet(this.apiUrl));
			Log.w("AppClient.get.url", this.apiUrl);
			// send get request
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String httpResult = resultFilter(httpResponse.getEntity());
				Log.w("AppClient.get.result", httpResult);
				return httpResult;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String post (HashMap urlParams) throws Exception {
		try {
			HttpPost httpPost = headerFilter(new HttpPost(this.apiUrl));
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			// get post parameters
			Iterator it = urlParams.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				postParams.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
			}
			// set data charset
			if (this.charset != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams, this.charset));
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams));
			}
			
			// send post request
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				String httpResult = resultFilter(httpResponse.getEntity());
				Log.w("AppClient.post.result", httpResult);
				
//				 //取得Cookie
				Header[] headers = httpResponse.getHeaders("Set-Cookie");
		        if (headers != null){
			        for(int i = 0; i < headers.length;i++)
			        {
			        	String cookie = headers[i].getValue();
			        	String[]cookievalues=cookie.split(";");
			        	for(int j=0;j<cookievalues.length;j++)
			        	{
			        		if (j == 0) {
			        			//HttpUtil.sCookie = cookievalues[j];
			        			//TODO
			        			MyApplication.getInstance().getSputil().setValue("sCookie", cookievalues[j]);
			        		}
			        	}
			        }
		        }
				return httpResult;
			} else {
				return null;
			}

		} catch (Exception e) {
			throw e;
		}
	}
	
	private HttpGet headerFilter (HttpGet httpGet) {
//		if (HttpUtil.sCookie != null && !HttpUtil.sCookie.equals("")) {
//			httpGet.addHeader("Cookie", HttpUtil.sCookie);
//		}
		//TODO
		String sCookie = MyApplication.getInstance().getSputil().getValue("sCookie", "");
		if (sCookie != null && !sCookie.equals("")) {
			httpGet.addHeader("Cookie", sCookie);
		}
		switch (this.compress) {
			case CS_GZIP:
				httpGet.addHeader("Accept-Encoding", "gzip");
				break;
			default :
				break;
		}
		return httpGet;
	}
	
	private HttpPost headerFilter (HttpPost httpPost) {
//		if (HttpUtil.sCookie != null && !HttpUtil.sCookie.equals("")) {
//			httpPost.addHeader("Cookie", HttpUtil.sCookie);
//		}
		//TODO
		String sCookie = MyApplication.getInstance().getSputil().getValue("sCookie", "");
		if (sCookie != null && !sCookie.equals("")) {
			httpPost.addHeader("Cookie", sCookie);
		}
		
		switch (this.compress) {
			case CS_GZIP:
				httpPost.addHeader("Accept-Encoding", "gzip");
				break;
			default :
				break;
		}
		return httpPost;
	}
	
	private String resultFilter(HttpEntity entity){
		String result = null;
		try {
			switch (this.compress) {
				case CS_GZIP:
					result = AppUtil.gzipToString(entity);
					break;
				default :
					result = EntityUtils.toString(entity);
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String postWithFile(String actionUrl, HashMap<String, String> params, HashMap<String, File> files,
									  HttpCallback hcb, Handler handler) throws Exception {
	  
	    String BOUNDARY = java.util.UUID.randomUUID().toString();  
	    String PREFIX = "--", LINEND = "\r\n";  
	    String MULTIPART_FROM_DATA = "multipart/form-data";  
	    String CHARSET = "UTF-8";  
	    URL uri = new URL(actionUrl);  
	    HttpURLConnection conn = (HttpURLConnection) uri.openConnection();  
	    conn.setReadTimeout(60 * 1000);  
	    conn.setDoInput(true);// 允许输入  
	    conn.setDoOutput(true);// 允许输出  
	    conn.setUseCaches(false);  
	    conn.setRequestMethod("POST"); // Post方式  
	    conn.setRequestProperty("connection", "keep-alive");  
	    conn.setRequestProperty("Charsert", "UTF-8");  
	    //conn.setRequestProperty("Cookie", HttpUtil.sCookie);
	    conn.setRequestProperty("Cookie", MyApplication.getInstance().getSputil().getValue("sCookie", ""));
	    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA  
	            + ";boundary=" + BOUNDARY);  
	  
	    // 首先组拼文本类型的参数  
	    StringBuilder sb = new StringBuilder();  
	    for (Map.Entry<String, String> entry : params.entrySet()) {  
	        sb.append(PREFIX);  
	        sb.append(BOUNDARY);  
	        sb.append(LINEND);  
	        sb.append("Content-Disposition: form-data; name=\""  
	                + entry.getKey() + "\"" + LINEND);  
	        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);  
	        sb.append("Content-Transfer-Encoding: 8bit" + LINEND);  
	        sb.append(LINEND);  
	        sb.append(entry.getValue());  
	        sb.append(LINEND);  
	    }  
	  
	    DataOutputStream outStream = new DataOutputStream(conn  
	            .getOutputStream());  
	    outStream.write(sb.toString().getBytes());  
	  
	    // 发送文件数据  
	    if (files != null)  
	        for (Map.Entry<String, File> file : files.entrySet()) {  
	            StringBuilder sb1 = new StringBuilder();  
	            sb1.append(PREFIX);  
	            sb1.append(BOUNDARY);  
	            sb1.append(LINEND);  
	            sb1.append("Content-Disposition: form-data; name=\""+file.getKey()+"\"; filename=\""  
	                            + file.getValue().getName() + "\"" + LINEND);  
	            sb1.append("Content-Type: application/octet-stream; charset="  
	                    + CHARSET + LINEND);  
	            sb1.append(LINEND);  
	            outStream.write(sb1.toString().getBytes());  
	            InputStream is = new FileInputStream(file.getValue());  
	            byte[] buffer = new byte[1024];  
	            int size = 0;
	            int len = 0;  
	            while ((len = is.read(buffer)) != -1) {  
	            	size += len;
	                outStream.write(buffer, 0, len);  
	            }  
	            sb1.append("Content-Length:" + size);
	            is.close();  
	            outStream.write(LINEND.getBytes());  
	        }  
	  
	    // 请求结束标志  
	    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();  
	    outStream.write(end_data);  
	    outStream.flush();  
	  
	    // 得到响应码  
	    int res = conn.getResponseCode();  
		InputStream in = conn.getInputStream();
		if (res == 200) {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = in.read(buff)) != -1) {
				buffer.write(buff, 0, len);
			}
			byte[] data = buffer.toByteArray();
			String jsonString = new String(data);
            Message message = new Message();  
			if (jsonString != null && JsonUtil.isJson(jsonString)) {
				BaseMessage baseMessage = AppUtil.getMessage(jsonString, actionUrl);
				message.what = 1;
				message.obj = baseMessage;
			} else {
				message.what = 2;
	            message.obj = jsonString;
			}
			handler.sendMessage(message);
		} else {
			handler.sendEmptyMessage(3);
		}
	    outStream.close();  
	    conn.disconnect();  
	    return in.toString();  
	}  
	
	
}
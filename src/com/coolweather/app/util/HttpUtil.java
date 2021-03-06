package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil {
	
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable() {
			public void run() {
				HttpURLConnection connection=null;
				try {
					HttpClient httpClient=new DefaultHttpClient();
					HttpGet httpGet=new HttpGet(address);
					HttpResponse httpResponse=httpClient.execute(httpGet);
					HttpEntity entity=httpResponse.getEntity();
					String response=EntityUtils.toString(entity, "utf-8");
//					URL url=new URL(address);
//					connection=(HttpURLConnection) url.openConnection();
//					connection.setRequestMethod("GET");
//					connection.setConnectTimeout(8000);
//					connection.setReadTimeout(8000);
//					InputStream in=connection.getInputStream();
//					BufferedReader reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
//					StringBuilder response=new StringBuilder();
//					String line;
//					while((line=reader.readLine()) != null){
//						response.append(line);
//					}
					if(listener != null){
						//回调onFinish()方法
//						listener.onFinish(response.toString());
						listener.onFinish(response);
					}
				} catch (Exception e) {
					if(listener !=null){
						//回调onError()方法
						listener.onError(e);
					}
				}finally{
					if(connection != null){
						connection.disconnect();
					}
				}
			}
			
		}).start();
	}
}

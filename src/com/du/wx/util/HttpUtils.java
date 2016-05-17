package com.du.wx.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpUtils {

	/**
	 * 处理请求
	 * 
	 * @param requestUrl
	 *            请求的url
	 * @param method
	 *            GET/POST
	 * @param outputStr
	 *            post请求下的输入字符串
	 * @return json对象
	 */
	public static JsonObject processReq(String requestUrl, String method, String outputStr) {
		JsonObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext context = SSLContext.getInstance("SSL", "SunJSSE");
			context.init(null, tm, new SecureRandom());

			SSLSocketFactory factory = context.getSocketFactory();

			URL url = new URL(requestUrl);
			// 获取网络连接对象
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			// 设置SSL套接字工厂
			httpUrlConn.setSSLSocketFactory(factory);

			httpUrlConn.setDoInput(true);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(method);
			// 设置请求方式（GET/POST）
			if ("GET".equalsIgnoreCase(method)) {
				httpUrlConn.connect();
			}

			if (outputStr != null) {
				OutputStream out = httpUrlConn.getOutputStream();
				out.write(outputStr.getBytes("UTF-8"));
				out.flush();
				out.close();
			}

			InputStream in = httpUrlConn.getInputStream();
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			reader.close();
			in.close();

			httpUrlConn.disconnect();
			jsonObject = (JsonObject) new JsonParser().parse(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * 传输文件
	 * @param iStream	文件输入流
	 * @param filename	文字名字
	 * @param requestUrl	请求的url
	 * @return
	 * @throws Exception
	 */
	public static JsonObject processReqFile(InputStream iStream, String filename, String requestUrl,String type) throws Exception {
		URL action = new URL(requestUrl);
		HttpsURLConnection con = (HttpsURLConnection) action.openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String Boundaty = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + Boundaty);
		// 请求正文信息
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(Boundaty);
		sb.append("\r\n");
		sb.append("Content-Disposition:form-data;name=\"" + type + "\";filename=\"" + filename + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文
		DataInputStream in = new DataInputStream(iStream);
		int len = 0;
		byte[] b = new byte[1024 * 2];
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + Boundaty + "--\r\n").getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();

		// 得到响应
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		String result = "";
		try {
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			result = builder.toString();
		} catch (Exception e) {
			System.out.println("post failure");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JsonObject jsonObject = (JsonObject) new JsonParser().parse(result);

		return jsonObject;
	}
	
	//springmvc下发数据给前台
	public static void springmvcWrite(HttpServletResponse response,String outputStr){
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter writer = null ;
		try {
			writer = response.getWriter() ;
			writer.write(outputStr);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class MyX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}

}
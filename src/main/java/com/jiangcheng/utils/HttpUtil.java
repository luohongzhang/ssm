package com.jiangcheng.utils;

import com.mysql.jdbc.log.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Slf4j
public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	private static final String http = "http";
	private static ThreadSafeClientConnManager cm = null;
	static {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme(http, 80, PlainSocketFactory
				.getSocketFactory()));
		cm = new ThreadSafeClientConnManager(schemeRegistry);
		cm.setMaxTotal(100);
		// 每条通道的并发连接数设置（连接池）
		cm.setDefaultMaxPerRoute(50);
		// 设置fastjson的子定义时间解析格式 全局的，如果有影响其他的需要再处理
	}

	public static String get(String url) throws Exception {
		long start = System.currentTimeMillis();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = null;
		HttpEntity httpEntity = null;
		String result = null;
		try {
			httpGet = new HttpGet(url);
			httpGet.addHeader("accept", "application/json");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity, HTTP.UTF_8);
				// EntityUtils.consume(httpEntity);
				return result;
			}
		} catch (Exception ex) {
			logger.error("http get接口出错:" + url, ex);
			ex.printStackTrace();
			throw ex;
		} finally {
			EntityUtils.consume(httpEntity);
			logger.info("http接口get方式,url:" + url + ",耗时："
					+ (System.currentTimeMillis() - start) + ";返回结果：" + result);
		}
		return "";
	}

	/**
	 * 获取http连接
	 * 
	 * @return
	 */
	private static HttpClient getHttpClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		// 连接是否成功超时时间
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
		// 读取返回值超时时间
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		return new DefaultHttpClient(cm, params);
	}

	/**
	 * 以post方式访问 z发送json数据 接收方需要通过inputstream来接收
	 * 
	 * @param url
	 *            接口url地址
	 * @param xmlBody
	 *            访问参数
	 * @return
	 */
	public static String post(String url, String params) throws Exception {
		long start = System.currentTimeMillis();
		HttpClient httpClient = getHttpClient();
		HttpEntity httpEntity = null;
		String result = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity input = new StringEntity(params, "UTF-8");
			input.setContentType("application/json");
			httpPost.setEntity(input);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity, HTTP.UTF_8);
				// EntityUtils.consume(httpEntity);
				return result;
			} else {
				throw new Exception("http status " + statusCode);
			}
		} catch (Exception ex) {
			logger.error("http post接口出错,url:" + url + ";params:"
					+ params, ex);
			ex.printStackTrace();
			throw ex;
		} finally {
			EntityUtils.consume(httpEntity);
			logger.info("http接口post方式,url:" + url + "，params:" + params
					+ ",耗时：" + (System.currentTimeMillis() - start) + ";返回结果："
					+ result);
		}
	}

	/**
	 * 通过key=value的方式进行参数传递
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params)
			throws Exception {
		long start = System.currentTimeMillis();
		/*
		 * HttpPost httpost = new HttpPost(url); List<NameValuePair> nvps = new
		 * ArrayList <NameValuePair>();
		 * 
		 * Set<String> keySet = params.keySet(); for(String key : keySet) {
		 * nvps.add(new BasicNameValuePair(key, params.get(key))); } try {
		 * httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); }
		 * catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		 * return "";
		 */
		HttpClient httpClient = new DefaultHttpClient();
		HttpEntity httpEntity = null;
		String result = null;
		try {
			HttpPost httpost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity, HTTP.UTF_8);
				// EntityUtils.consume(httpEntity);
				return result;
			} else {
				throw new Exception("http status " + statusCode);
			}
		} catch (Exception ex) {
			logger.error("http post接口出错,url:" + url + ";params:"
					+ params, ex);
			ex.printStackTrace();
			throw ex;
		} finally {
			EntityUtils.consume(httpEntity);
			logger.info("http接口post方式,url:" + url + "，params:" + params
					+ ",耗时：" + (System.currentTimeMillis() - start) + ";返回结果："
					+ result);
		}
	}

	public static String getRequestPostStr(HttpServletRequest request)
			throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		if (buffer == null)
			return null;
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}

	public static byte[] getRequestPostBytes(HttpServletRequest request)
			throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readlen = request.getInputStream().read(buffer, i,
					contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	public static void main(String[] args) throws Exception {
		/*AuditExpenseApplyVo vo = new AuditExpenseApplyVo();
		vo.setApplyNum("abcd");
		vo.setAuditNodeName("1");
		vo.setAuditStatus(4);
		vo.setAuditUserId("test");
		vo.setAuditUserName("夏体顺");
		String result = post(
				"http://chrcrmprj.union.vip.58.com/chrcrmprj/apply/audit",
				GsonUtil.toJson(vo));*/
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", "test");
		String reString = post("http://chrcrmprj.union.vip.58.com/chrcrmprj/apply/audit/abc", params);
		System.out.println(reString);
	}
}

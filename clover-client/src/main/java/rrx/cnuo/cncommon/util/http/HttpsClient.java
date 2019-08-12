package rrx.cnuo.cncommon.util.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * HttpsClient is for HTTPS connection, this connection used for getting message
 * from weixin server
 * 
 * @author 
 *
 */
@SuppressWarnings({  "unchecked", "rawtypes", "finally" })
public class HttpsClient {
	private static CloseableHttpClient instance = null;

	public HttpsClient() {
		instance = createSSLClientDefault();
	}

	private static CloseableHttpClient getInstance() {
		if (instance == null) {
			synchronized (HttpsClient.class) {
				if (instance == null) {
					new HttpsClient();
				}
			}
		}
		return instance;
	}

	private static CloseableHttpClient createSSLClientDefault() {
		try {
			javax.net.ssl.SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, new TrustStrategy() {
						// 信任所有
						@Override
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static String doGet(String url) {
		BufferedReader in = null;
		String respBody = null;
		// 实例化HTTP方法
		HttpGet request = new HttpGet();
		try {
			// 定义HttpClient
			CloseableHttpClient client = HttpsClient.getInstance();

			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			respBody = sb.toString();
		} catch (Exception e) {
			request.abort();
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return respBody;
		}

	}
	
	
	/**
	 * 添加post请求
	 * @param url
	 * @param params
	 * @return
	 * @author 
	 */
	public static String doPost(String url, Map params) {
		BufferedReader in = null;
		String respBody = null;
		try {
			// 定义HttpClient
			CloseableHttpClient client = HttpsClient.getInstance();
			// 实例化HTTP方法
			HttpPost request = postForm(url, params);  
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			respBody = sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return respBody;
		}

	}
	
	private static HttpPost postForm(String url, Map<String, String> params){  
        
        HttpPost httpost = new HttpPost(url); 
        StringEntity se = new StringEntity(JSON.toJSONString(params),"UTF-8");  
        httpost.setEntity(se);  
        return httpost;  
    }  
	
	public static InputStream doGetImg(String url) {
		try {
			// 定义HttpClient
			CloseableHttpClient client = HttpsClient.getInstance();
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			return response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	

	/**
	 * 添加post请求
	 * @param url
	 * @param params
	 * @return
	 * @author
	 */

	public static String doPost(String url, List<Map> params) {
		BufferedReader in = null;
		String respBody = null;
		try {
			// 定义HttpClient
			CloseableHttpClient client = HttpsClient.getInstance();
			// 实例化HTTP方法
			HttpPost request = postForm(url, params);
			request.setHeader("Content-Type","application/json;charset=utf-8");
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			respBody = sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return respBody;
		}

	}
	
	/**
	 * 添加post请求
	 * @param url
	 * @param msg
	 * @return
	 * @author
	 */
	public static String doPost(String url, String msg) throws Exception {
		BufferedReader in = null;
		String respBody = null;

		// 定义HttpClient
		CloseableHttpClient client = HttpsClient.getInstance();
		// 实例化HTTP方法
		HttpPost httpost = new HttpPost(url);
		StringEntity se = new StringEntity(msg,"UTF-8");
		httpost.setEntity(se);
		httpost.setHeader("Content-Type","application/json;charset=utf-8");
		HttpResponse response = client.execute(httpost);
		in = new BufferedReader(new InputStreamReader(response.getEntity()
				.getContent()));
		StringBuilder sb = new StringBuilder("");
		String line = "";
		String NL = System.getProperty("line.separator");
		while ((line = in.readLine()) != null) {
			sb.append(line + NL);
		}
		in.close();
		respBody = sb.toString();

		if (in != null) {
			try {
				in.close();// 最后要关闭BufferedReader
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return respBody;

	}
	

	private static HttpPost postForm(String url, List<Map> params){
		HttpPost httpost = new HttpPost(url);
		StringEntity se = new StringEntity(JSON.toJSONString(params),"UTF-8");
		httpost.setEntity(se);
		return httpost;
	}
	
}

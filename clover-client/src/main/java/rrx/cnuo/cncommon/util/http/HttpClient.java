package rrx.cnuo.cncommon.util.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.ConnectionPoolTimeoutException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import rrx.cnuo.cncommon.vo.JsonResult;

/**
 * http请求工具类
 *
 * @author
 */
public class HttpClient {
    private static AsyncHttpClient instance = null;
    private static Logger log = LoggerFactory.getLogger(HttpClient.class);

    public HttpClient() {
        instance = new AsyncHttpClient();
    }

    public static AsyncHttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    new HttpClient();
                }
            }
        }
        return instance;

    }

    /**
     * httppost请求，返回字符串
     *
     * @author xuhongyu
     */
    public static String doPost(String url, String requestJson) throws Exception {
        Response resp = null;
        AsyncHttpClient c = HttpClient.getInstance();
        if (requestJson != null) {
            resp = c.preparePost(url)
                    .setBody(requestJson.getBytes("utf-8"))
                    .setHeader("Content-Type", "application/json;charset=utf-8")
                    .execute().get();
        } else {
            resp = c.preparePost(url).setBody(requestJson).execute().get();
        }
        log.info("post request is:" + requestJson);
        String respBody = resp.getResponseBody();
        if (respBody == null || respBody.length() == 0) {
            log.error("requestJson is:" + requestJson);
            log.error("响应为空");
        } else {
            log.info("post response is:" + respBody);
        }
        return respBody;
    }

    public static String doAuthPost(String url, String requestJson, String Authorization) throws Exception {
        Response resp = null;
        AsyncHttpClient c = HttpClient.getInstance();
        if (requestJson != null) {
            resp = c.preparePost(url)
                    .setBody(requestJson.getBytes("utf-8"))
                    .setHeader("Authorization", Authorization)
                    .setHeader("Content-Type", "application/json;charset=utf-8")
                    .execute().get();
        } else {
            resp = c.preparePost(url).setBody(requestJson).execute().get();
        }
        String respBody = resp.getResponseBody();
        if (respBody == null || respBody.length() == 0) {
            log.error("requestJson is:" + requestJson);
            log.error("响应为空");
        } else {
//			log.info("post response is:" + respBody);
        }
        return respBody;
    }

    public static String doAuthPut(String url, String requestJson, String Authorization) throws Exception {
        Response resp = null;
        AsyncHttpClient c = HttpClient.getInstance();
        if (requestJson != null) {
            resp = c.preparePut(url)
                    .setBody(requestJson.getBytes("utf-8"))
                    .setHeader("Authorization", Authorization)
                    .setHeader("Content-Type", "application/json;charset=utf-8")
                    .execute().get();
        } else {
            resp = c.preparePost(url).setBody(requestJson).execute().get();
        }
        String respBody = resp.getResponseBody();
        if (respBody == null || respBody.length() == 0) {
            log.error("requestJson is:" + requestJson);
            log.error("响应为空");
        } else {
//			log.info("post response is:" + respBody);
        }
        return respBody;
    }

    public static Integer doAuthDelete(String url, String requestJson, String Authorization) throws Exception {
        Response resp = null;
        AsyncHttpClient c = HttpClient.getInstance();
        if (requestJson != null) {
            resp = c.prepareDelete(url)
                    .setBody(requestJson.getBytes("utf-8"))
                    .setHeader("Authorization", Authorization)
                    .setHeader("Content-Type", "application/json;charset=utf-8")
                    .execute().get();
        } else {
            resp = c.preparePost(url).setBody(requestJson).execute().get();
        }
        Integer respStatus = resp.getStatusCode();
        return respStatus;
    }

    public static String doAuthGet(String url, String requestJson, String Authorization) throws Exception {
        Response resp = null;
        AsyncHttpClient c = HttpClient.getInstance();
        if (requestJson != null) {
            resp = c.prepareGet(url)
                    .setBody(requestJson.getBytes("utf-8"))
                    .setHeader("Authorization", Authorization)
                    .setHeader("Content-Type", "application/json;charset=utf-8")
                    .execute().get();
        } else {
            resp = c.prepareGet(url).setBody(requestJson).execute().get();
        }
        String respBody = resp.getResponseBody();
        if (respBody == null || respBody.length() == 0) {
            log.error("requestJson is:" + requestJson);
            log.error("响应为空");
        } else {
//			log.info("post response is:" + respBody);
        }
        return respBody;
    }

    public static String doFormPost(String url, NameValuePair[] pair) throws Exception {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        PostMethod method = new PostMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "", false));
            method.setQueryString(pair);
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new RuntimeException("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }
    }

    public static String doFormGet(String url, NameValuePair[] pair) throws Exception {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "", false));
            method.setQueryString(pair);
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new RuntimeException("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }
    }

    /**
     * 发送httppost请求并对返回的json字符串进行封装<br>
     * JsonResult的data无值
     * @author xuhongyu
     */
    public static <T> JsonResult<T> doPostWrapResult(String url, String data) throws Exception {
    	return doPostWrapResult(url, data, null);
    }
    
    /**
     * 发送httppost请求并对返回的json字符串进行封装<br>
     * JsonResult的data有值
     * @author xuhongyu
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonResult<T> doPostWrapResult(String url, String data, Class<T> clazz) throws Exception {
        String ret = doPost(url, data);
//        log.info("-----doPostWrapResult()-----url:{},data:{},resp:{}", url, data, ret);
        JsonResult<JSONObject> jrjsonObj = JSONObject.parseObject(ret, JsonResult.class);
        if (clazz != null && jrjsonObj != null && jrjsonObj.getData() != null) {
            if (clazz.equals(JSONObject.class)) {
                return (JsonResult<T>) jrjsonObj;
            } else {
                JsonResult<T> result = new JsonResult<T>();
                T obj = JSON.toJavaObject(jrjsonObj.getData(), clazz);
                result.setData(obj);

                result.setStatus(jrjsonObj.getStatus());
                result.setMsg(jrjsonObj.getMsg());
                return result;
            }
        } else {
            return (JsonResult<T>) jrjsonObj;
        }
    }

    /**
     * get方法http请求，返回字符串
     *
     * @author xuhongyu
     */
    public static String doGet(String url) throws Exception {
        Response resp = null;
        AsyncHttpClient c = HttpClient.getInstance();
        resp = c.prepareGet(url)
                .setHeader("Content-Type", "application/json;charset=utf-8")
                .execute().get();

        String respBody = resp.getResponseBody();
        if (respBody == null || respBody.length() == 0) {
            log.info("request url is:" + url);
            log.error("响应为空");
        } else {
            log.info("post response is:" + respBody);
        }
        return respBody;
    }

    /**
     * get方法http请求，返回JsonResult
     *
     * @author xuhongyu
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonResult<T> doGetWrapResult(String url, Class<T> clazz) throws Exception {
        String ret = doGet(url);
        JsonResult<JSONObject> jrjsonObj = JSONObject.parseObject(ret, JsonResult.class);
        if (jrjsonObj == null) {
            return (JsonResult<T>) jrjsonObj;
        }
        if (clazz != null && jrjsonObj != null && jrjsonObj.getData() != null) {
            if (clazz.equals(JSONObject.class)) {
                return (JsonResult<T>) jrjsonObj;
            } else {
                JsonResult<T> result = new JsonResult<T>();
                T obj = JSON.toJavaObject(jrjsonObj.getData(), clazz);
                result.setData(obj);

                result.setStatus(jrjsonObj.getStatus());
                result.setMsg(jrjsonObj.getMsg());
                return result;
            }
        } else {
            return (JsonResult<T>) jrjsonObj;
        }
    }

    public static byte[] doImgPost(String url, Map<String, Object> map) {
        byte[] result = null;
        //使用HTTPPost方法访问获取二维码链接url
        HttpPost httpPost = new HttpPost(url);
        //创建http连接客户端
        CloseableHttpClient client = HttpClients.createDefault();
        //设置头响应类型
        httpPost.addHeader("Content-Type", "application/json");
        try {
            // 设置请求的参数
            JSONObject postData = new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                postData.put(entry.getKey(), entry.getValue());
            }
            httpPost.setEntity(new StringEntity(postData.toString(), "UTF-8"));
            log.info("微信获取微信二维码post数据 " + postData.toString());
            //返回的post请求结果
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toByteArray(entity);
        } catch (ConnectionPoolTimeoutException e) {
            log.error("http get throw ConnectionPoolTimeoutException(wait time out)", e);
        } catch (ConnectTimeoutException e) {
            log.error("http get throw ConnectTimeoutException", e);
        } catch (SocketTimeoutException e) {
            log.error("http get throw SocketTimeoutException", e);
        } catch (Exception e) {
            log.error("http get throw Exception", e);
        } finally {
            httpPost.releaseConnection();
        }
        //最后转成2进制图片流
        return result;
    }

}

package rrx.cnuo.cncommon.vo;


import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jayson
 */
public class JsonResult<T> {
    public static final int SUCCESS = 200;// 成功
    public static final int FAIL = 201;// 失败
    public static final int BUSINESS_FAIL = 202;// 根据业务不同，提示同步失败
    public static final int GET_FAIL_203 = 203;    //respCode返回 网络繁忙
    public static final int GET_FAIL_204 = 204;    //respCode返回 通道不可用
    public static final int REQUIRE_CREDIT_INFO = 205;//缺少信用认证
    public static final int GET_OLD_ACCOUNT = 206;//缺少信用认证

    public static final int GET_FAIL_301 = 301;    //internal_error:服务异常
    public static final int GET_FAIL_302 = 302;//not_found:资源不存在
    public static final int GET_FAIL_303 = 303;//bad_request:参数类型不正确
    public static final int GET_FAIL_304 = 304;//unauthorized:请授权后访问
    public static final int GET_FAIL_305 = 305;//unavailable:通道不可用

    public static final int EXPIRED = 401;    // 失效
    public static final int TOKEN_EXPIRED = 9001; //TOKEN失效
    public static final int LOGIN_BAN = 9002; //用户被封
    public static final int LEASE = 9999; //信用借还
    
    public static final int ERROR_CANT_SOLVE = 29032;    // 因程序自身bug或报错等造成mq消费失败且当时无法自动处理的情况，先落地mysql后续再做处理
    
    private int status;
    private String code;

    private String msg = "";

    private T data;

    public JsonResult() {

    }

    public JsonResult(Integer status, String message, T t) {
        this.status = status;
        this.msg = message;
        this.data = t;
    }

    public JsonResult(int status, String message) {
        this.status = status;
        this.msg = message;
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> result = new JsonResult<T>();
        result.setStatus(SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    @SuppressWarnings("rawtypes")
    public static JsonResult fail(int status, String msg) {
        if (status == SUCCESS) {
            throw new RuntimeException("ok is not fail");
        }
        JsonResult result = new JsonResult();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }

    @SuppressWarnings("rawtypes")
    public static JsonResult badRequest(String msg) {
        return fail(FAIL, msg);
    }

    @JsonIgnore
    public boolean isOk() {
        return SUCCESS == status;
    }

    @JsonGetter
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 只用于接收内部第三方(支付、信用或消息中心)返回状态吗用！返回到前段的状态不用这个！
     *
     * @param code
     * @author xuhongyu
     */
    public void setCode(String code) {
        this.code = code;
        if (StringUtils.isNoneBlank(this.code)) {
            switch (this.code) {
                case "ok":
                    this.status = JsonResult.SUCCESS;
                    break;
                case "internal_error":
                    this.status = JsonResult.GET_FAIL_301;
                    break;
                case "not_found":
                    this.status = JsonResult.GET_FAIL_302;
                    break;
                case "bad_request":
                    this.status = JsonResult.GET_FAIL_303;
                    break;
                case "unauthorized":
                    this.status = JsonResult.GET_FAIL_304;
                    break;
                case "unavailable":
                    this.status = JsonResult.GET_FAIL_305;
                    break;
                default:
                    this.status = JsonResult.FAIL;
                    break;
            }
        }
    }
    @JsonIgnore
	public String getCode() {
		return code;
	}
    
}

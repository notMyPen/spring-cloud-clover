package rrx.cnuo.cncommon.accessory.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.utils.UserPermissionUtil;
import rrx.cnuo.cncommon.vo.User;

/**
 *    该拦截器用于微服务内部调用时进行鉴权：请求进入服务之前先查出该用户的权限（从数据库或redis中）并放进User对象中，然后调用verify()校验<br>
 *    不通过返回没有权限错误信息，通过后将user信息放进ThreadLocal中<br>
 *     当前微服务请求结束后清除ThreadLocal中的上线文信息
 * @author xuhongyu
 * @date 2019年8月12日
 */
public class UserContextInterceptor extends HandlerInterceptorAdapter {
	    
//	private static final Logger log = LoggerFactory.getLogger(UserContextInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse respone, Object arg2) throws Exception {
		User user = getUser(request);
		UserPermissionUtil.permission(user);
		if(!UserPermissionUtil.verify(user,request)) {
			respone.setHeader("Content-Type", "application/json");
			String jsonstr = JSON.toJSONString("no permisson access service, please check");     
			respone.getWriter().write(jsonstr);
			respone.getWriter().flush();
			respone.getWriter().close();
			throw new PermissionException("no permisson access service, please check");
		}
		UserContextHolder.set(user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse respone, Object arg2, ModelAndView arg3)
			throws Exception {
		// DOING NOTHING
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse respone, Object arg2, Exception arg3) throws Exception {
		UserContextHolder.shutdown();
	}
	
	private User getUser(HttpServletRequest request){
		String userid = request.getHeader("x-user-id");
		String username = request.getHeader("x-user-name");
		String currentServiceId = request.getHeader("x-user-serviceName");
		User user = new User();
		user.setUserId(Long.parseLong(userid));
		user.setUserName(username);
		user.setCurrentServiceId(currentServiceId);
		return user;
	}
	
	static class PermissionException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public PermissionException(String msg) {
	        super(msg);
	    }
	}
}

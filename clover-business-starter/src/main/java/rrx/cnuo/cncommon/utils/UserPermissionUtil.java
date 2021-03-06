package rrx.cnuo.cncommon.utils;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import rrx.cnuo.cncommon.vo.LoginUser;

/**
 * 鉴权工具：权限获取(可以从数据库或redis中获取)和权限验证
 * @author xuhongyu
 * @date 2019年8月12日
 */
public class UserPermissionUtil {
	
	/**
	 * 模拟权限校验：这里采用给不同的用户可以访问的服务列表，在根据请求路由的服务名来校验该用户是否有权限<br>
	 * 可以根据自己项目需要定制不同的策略,如查询数据库获取具体的菜单url或者角色等等.
	 * @param user
	 */
	public static boolean verify(LoginUser user,HttpServletRequest request){
		if(StringUtils.isEmpty(user)) {
			return false;
		}else {
			if(!StringUtils.isEmpty(user.getCurrentServiceId())) {
				List<String> str = user.getAllowPermissionService();
				for (String permissionService : str) {
					if(user.getCurrentServiceId().equalsIgnoreCase(permissionService)) {
						return true;
					}
				}
				return false;
			}else {
				return true;
			}
		}
	}
	
	/**
	 * 模拟权限赋值(为每个用户赋权限路径), 可以根据自己项目需要定制不同的策略,如查询数据库获取具体的菜单url或者角色等等.<br>
	 * @param user
	 */
	public static void permission(LoginUser user){
		if(user.getMiniOpenId().equals("admin")) {
			List<String> allowPermissionService = new ArrayList<String>();
			allowPermissionService.add("user-service");
			allowPermissionService.add("order-service");
			allowPermissionService.add("biz-service");
			user.setAllowPermissionService(allowPermissionService);
		}else if(user.getMiniOpenId().equals("spring")) {
			List<String> allowPermissionService = new ArrayList<String>();
			allowPermissionService.add("user-service");
			user.setAllowPermissionService(allowPermissionService);
		} else {
			List<String> allowPermissionService = new ArrayList<String>();
			user.setAllowPermissionService(allowPermissionService);
		}
	}
		
}

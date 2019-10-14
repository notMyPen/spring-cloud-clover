package rrx.cnuo.cncommon.feignclient.callback;

import org.springframework.stereotype.Component;

import rrx.cnuo.cncommon.feignclient.UserCommonFeignService;
import rrx.cnuo.cncommon.vo.feign.UserPassportVo;

/**
 * 断路器回调方法<br>
 * 断路器要实现之前定义的UserFeignService接口，请求失败，进入断路器时，会回调这里的方法
 * @author xuhongyu
 * @date 2019年6月27日
 */
@Component
public class UserCommonHystrixFeignFallback implements UserCommonFeignService{


	@Override
	public UserPassportVo getUserPassportByUid(Long uid) {
		// TODO 这里是进入断路后的降级方法，返回一个降级数据
		return null;
	}

	@Override
	public void updateUserCardNum(Long uid) {
//		DTXUserControls.rollbackGroup(UserContextHolder.currentUser().getUserId()+"");
		throw new RuntimeException("updateUserCardNum失败");
	}
	
	@Override
	public String getUserWxAccount(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}
}

package rrx.cnuo.service.feignclient.callback;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.CLoverException;
import rrx.cnuo.cncommon.vo.feign.UserPassportVo;
import rrx.cnuo.service.feignclient.UserFeignService;

/**
 * 断路器回调方法<br>
 * 断路器要实现之前定义的UserFeignService接口，请求失败，进入断路器时，会回调这里的方法
 * @author xuhongyu
 * @date 2019年6月27日
 */
@Component
@Slf4j
public class UserHystrixFeignFallback implements UserFeignService{

	@Override
	public JSONObject getUserAccountByUid(Long uid) {
		// TODO 这里是进入断路后的降级方法，返回一个降级数据
		return new JSONObject();
	}

	@Override
	public void updateUserAccountByUidSelective(String userAccountJsonStr) {
		log.info("增删改操作的服务降级必须抛异常，不然分布式事务不会滚！");
		throw new CLoverException("updateUserAccountByUidSelective失败");
	}

	@Override
	public void updateUserAccountAccumulateAboutOrder(String userAccountListStatis, Byte updateType, Boolean rollBack) {
		log.info("增删改操作的服务降级必须抛异常，不然分布式事务不会滚！");
		throw new CLoverException("updateUserAccountAccumulateAboutOrder失败");
	}

	
	
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

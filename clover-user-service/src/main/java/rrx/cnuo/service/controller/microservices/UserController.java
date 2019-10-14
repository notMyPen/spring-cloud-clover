package rrx.cnuo.service.controller.microservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import rrx.cnuo.cncommon.util.CopyProperityUtils;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.feign.UserPassportVo;
import rrx.cnuo.service.po.UserAccount;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.service.MineService;
import rrx.cnuo.service.service.PassportService;
import rrx.cnuo.service.service.UserAccountService;
import rrx.cnuo.service.service.data.UserAccountDataService;
import rrx.cnuo.service.service.data.UserBasicInfoDataService;
import rrx.cnuo.service.service.data.UserPassportDataService;

/**
 * 用户模块对内提供的服务
 * @author xuhongyu
 * @date 2019年7月2日
 */
@Api
@RestController
public class UserController{
	
	@Autowired private UserAccountDataService userAccountDataService;
	@Autowired private UserPassportDataService userPassportDataService;
	@Autowired private UserBasicInfoDataService userBasicInfoDataService;
	@Autowired private PassportService passportService;
	@Autowired private UserAccountService userAccountService;
	@Autowired private MineService mineService;

	@GetMapping("/userAccount/{uid}")
	public JSONObject getUserAccountByUid(@PathVariable("uid") Long uid) throws Exception{
		UserAccount userAccount = userAccountDataService.selectByPrimaryKey(uid);
		return (JSONObject)JSON.toJSON(userAccount);
	}
	
	/**
	 * 更新用户是否注册信用中心状态为已注册
	 * @author xuhongyu
	 * @param uid
	 */
	@PostMapping("/userAccount/registCredit")
	public void updateUserRegistCredit(@RequestParam Long uid) throws Exception{
		userAccountService.updateUserRegistCredit(uid);
	}
	
	@GetMapping("/userPassport/{uid}")
	public UserPassportVo getUserPassportByUid(@PathVariable("uid") Long uid) throws Exception{
		UserPassport userPassport = userPassportDataService.selectByPrimaryKey(uid);
		UserPassportVo userPassportVo = new UserPassportVo();
		CopyProperityUtils.copyProperiesIgnoreNull(userPassport, userPassportVo);
		return userPassportVo;
	}
	
	@GetMapping("/info/wxAccount/{uid}")
	public String getUserWxAccount(@PathVariable("uid") Long uid) throws Exception{
		UserBasicInfo userBasicInfo = userBasicInfoDataService.selectByPrimaryKey(uid);
		return userBasicInfo.getWxAccount();
	}
	
	@PutMapping("/userAccount")
	public void updateUserAccountByUidSelective(@RequestParam String userAccountJsonStr) throws Exception{
		UserAccount userAccount = JSONObject.parseObject(userAccountJsonStr,UserAccount.class);
		userAccountDataService.updateByPrimaryKeySelective(userAccount);
	}
	
	/**
	 * 以累加的方式，更新在某订单中某些相关用户的余额和可提现余额
	 * @author xuhongyu
	 * @param userAccountListStatis 某订单下某些相关用户的账单统计情况(List<JSONObject>转换成的字符串)
	 * @param updateType 更新类型：0-同时更新余额和可提现余额；1-只更新余额；2-只更新可提现余额
	 * @param rollBack 是否是金额变动回滚
	 */
	@PutMapping("/updateUserAccountAccumulateAboutOrder")
	public void updateUserAccountAccumulateAboutOrder(@RequestParam String userAccountListStatis,
			@RequestParam Byte updateType,@RequestParam Boolean rollBack) throws Exception{
		List<JSONObject> list = JSON.parseArray(userAccountListStatis, JSONObject.class);
		userAccountDataService.updateUserAccountAccumulateAboutOrder(list,updateType,rollBack);
	}
	
	/**
	 * 更新用户剩余礼券个数
	 * @author xuhongyu
	 * @param uid
	 * @param leftCnt
	 */
	@PostMapping("/possport/userCardNum")
	public void updateUserCardNum(@RequestParam Long uid) throws Exception{
		passportService.updateUserCardNum(uid);
	}
	
	/**
	 * 人脸识别前的验证
	 * @author xuhongyu
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/info/faceVerifyCheck")
	public JsonResult getFaceVerifyCheck(@RequestParam("idCardNo") String idCardNo) throws Exception{
		return mineService.getFaceVerifyCheck(idCardNo);
	}
}

package rrx.cnuo.service.service.impl.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.SimplifyObjJsonUtil;
import rrx.cnuo.cncommon.util.StringUtil;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.service.dao.UserPassportMapper;
import rrx.cnuo.service.po.UserBasicInfo;
import rrx.cnuo.service.po.UserDetailInfo;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.po.UserSpouseSelection;
import rrx.cnuo.service.service.data.UserBasicInfoDataService;
import rrx.cnuo.service.service.data.UserDetailInfoDataService;
import rrx.cnuo.service.service.data.UserPassportDataService;
import rrx.cnuo.service.service.data.UserSpouseSelectionDataService;

@Service
@SuppressWarnings("unchecked")
public class UserPassportDataServiceImpl implements UserPassportDataService {
	
	@Autowired private RedisTool redis;
	@Autowired private UserPassportMapper userPassportMapper;
	@Autowired private UserBasicInfoDataService userBasicInfoDataService;
	@Autowired private UserDetailInfoDataService userDetailInfoDataService;
	@Autowired private UserSpouseSelectionDataService userSpouseSelectionDataService;

	@Override
	public UserPassport selectByPrimaryKey(long userId) throws Exception {
		return getUserPassportByKey(userId + "", (byte) 2);
	}
	
	@Override
	public UserPassport selectByMiniOpenid(String miniOpenId) throws Exception{
		return getUserPassportByKey(miniOpenId, (byte) 1);
	}
	
	/**
	 * 根据key获取UserPassport
	 * @author xuhongyu
	 * @param key
	 * @param keyType key类型：1-微信小程序openId；2--uid
	 * @return
	 */
	private UserPassport getUserPassportByKey(String key,byte keyType) throws Exception{
		String redisKey  = Const.REDIS_PREFIX.REDIS_USER_PASSPORT + key;
		String str = redis.getString(redisKey);
		UserPassport userPassport = null;
        if (StringUtils.isBlank(str)) {
        	if(keyType == 1) {
        		UserPassport param = new UserPassport();
        		param.setMiniOpenId(key);
        		List<UserPassport> userPassports = userPassportMapper.selectByParam(param);
        		if(userPassports != null && userPassports.size() > 0) {
        			userPassport = userPassports.get(0);
        		}
        	}else {
        		userPassport = userPassportMapper.selectByPrimaryKey(Long.parseLong(key));
        	}
            if (userPassport != null) {
            	//计算personalDataIntegrity和spouseConditionIntegrity放到缓存里
            	UserBasicInfo userBasicInfo = userBasicInfoDataService.selectByPrimaryKey(userPassport.getUid());
        		UserDetailInfo userDetailInfo = userDetailInfoDataService.selectByPrimaryKey(userPassport.getUid());
        		int personalDataIntegrity = getPersonalDataIntegrity(userBasicInfo, userDetailInfo);
        		userPassport.setPersonalDataIntegrity(personalDataIntegrity);
        		
        		UserSpouseSelection userSpouseSelection = userSpouseSelectionDataService.selectByPrimaryKey(userPassport.getUid());
        		int spouseConditionIntegrity = getSpouseConditionIntegrity(userSpouseSelection);
        		userPassport.setSpouseConditionIntegrity(spouseConditionIntegrity);
        		
            	JSONObject userJson = SimplifyObjJsonUtil.getSimplifyJsonObjFromOriginObj(userPassport, SimplifyObjJsonUtil.userPassportSimplifyTemplate);
                redis.set(redisKey, userJson.toJSONString(), Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }else{
            	//如果从mysql中查询的UserPassport为空，在redis中赋值“null”，防止缓存穿透
            	redis.set(redisKey, "null", Const.REDIS_PREFIX.USER_INFO_SECONDS);
            }
        } else {
        	if(!"null".equals(str)){
        		userPassport = SimplifyObjJsonUtil.getOriginObjFromSimplifyJsonStr(str, UserPassport.class, SimplifyObjJsonUtil.userPassportRestoreTemplate);
        	}
        }
        return userPassport;
	}

	@Override
	public void delUserAccountFromRedis(long uid) throws Exception {
		redis.delete(Const.REDIS_PREFIX.REDIS_USER_PASSPORT + uid);
	}

	@Override
	public void updateByPrimaryKeySelective(UserPassport param) throws Exception {
		userPassportMapper.updateByPrimaryKeySelective(param);
		delUserAccountFromRedis(param.getUid());
	}

	@Override
	public void insertSelective(UserPassport userPassport) {
		userPassportMapper.insertSelective(userPassport);
	}

	/**
	 * 个人资料完整度(%)：regist_tel + user_basic_info的部分 + user_detail_info全部
	 * @author xuhongyu
	 * @param userBasicInfo
	 * @param userDetailInfo
	 * @return
	 */
	private int getPersonalDataIntegrity(UserBasicInfo userBasicInfo, UserDetailInfo userDetailInfo) throws Exception{
		int personalDataTotalCnt = 1;//regist_tel是必填所以初始是1
		int personalDataImproveCnt = 1;
		
		Class<UserBasicInfo> userBasicInfoClazz = (Class<UserBasicInfo>) userBasicInfo.getClass();
		Field[] userBasicInfoFields = userBasicInfoClazz.getDeclaredFields();
		for(Field field : userBasicInfoFields) {
			if(!"uid".equals(field.getName()) && !"gender".equals(field.getName()) && !"birthday".equals(field.getName()) 
					&& !"createTime".equals(field.getName()) && !"updateTime".equals(field.getName())) {
				personalDataTotalCnt++;
				
				Method method = userBasicInfoClazz.getDeclaredMethod("get" + StringUtil.toUpperCaseFirstOne(field.getName()));
				Object value = method.invoke(userBasicInfo);
				renewValidCnt(field.getType(), value, personalDataImproveCnt);
			}
		}
		
		Class<UserDetailInfo> userDetailInfoClazz = (Class<UserDetailInfo>) userDetailInfo.getClass();
		Field[] userDetailInfoFields = userDetailInfoClazz.getDeclaredFields();
		for(Field field : userDetailInfoFields) {
			if(!"haveBrother".equals(field.getName()) && !"haveYoungerBrother".equals(field.getName()) && !"haveSister".equals(field.getName()) && !"haveYoungerSister".equals(field.getName()) 
					&& !"uid".equals(field.getName()) && !"createTime".equals(field.getName()) && !"updateTime".equals(field.getName())) {
				personalDataTotalCnt++;
				
				Method method = userDetailInfoClazz.getDeclaredMethod("get" + StringUtil.toUpperCaseFirstOne(field.getName()));
				Object value = method.invoke(userDetailInfo);
				renewValidCnt(field.getType(), value, personalDataImproveCnt);
			}
		}
		return new BigDecimal(personalDataImproveCnt).multiply(new BigDecimal(100)).divide(new BigDecimal(personalDataTotalCnt),0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	/**
	 * 择偶条件完整度(%)：user_spouse_selection全部
	 * @author xuhongyu
	 * @param userSpouseSelection
	 * @return
	 * @throws Exception
	 */
	private int getSpouseConditionIntegrity(UserSpouseSelection userSpouseSelection) throws Exception{
		int spouseConditionTotalCnt = 0;
		int spouseConditionImproveCnt = 0;
		
		Class<UserSpouseSelection> userSpouseSelectionClazz = (Class<UserSpouseSelection>) userSpouseSelection.getClass();
		Field[] userSpouseSelectionFields = userSpouseSelectionClazz.getDeclaredFields();
		for(Field field : userSpouseSelectionFields) {
			if(!"uid".equals(field.getName()) && !"createTime".equals(field.getName()) && !"updateTime".equals(field.getName())) {
				spouseConditionTotalCnt++;
				
				Method method = userSpouseSelectionClazz.getDeclaredMethod("get" + StringUtil.toUpperCaseFirstOne(field.getName()));
				Object value = method.invoke(userSpouseSelection);
				renewValidCnt(field.getType(), value, spouseConditionImproveCnt);
			}
		}
		return new BigDecimal(spouseConditionImproveCnt).multiply(new BigDecimal(100)).divide(new BigDecimal(spouseConditionTotalCnt),0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	/**
	 * 根据某个属性有没有设置值，更新资料完成次数
	 * @author xuhongyu
	 * @param fieldType
	 * @param value
	 * @param dataImproveCnt
	 */
	private void renewValidCnt(Class<?> fieldType,Object value,int dataImproveCnt) {
		if(Byte.class.equals(fieldType)) {
			Byte byteVal = (Byte) value;
			if(byteVal != null && byteVal != 0) {
				dataImproveCnt++;
			}
		}else if(Short.class.equals(fieldType)) {
			Short shortVal = (Short) value;
			if(shortVal != null && shortVal != 0) {
				dataImproveCnt++;
			}
		}else if(Integer.class.equals(fieldType)) {
			Integer intVal = (Integer) value;
			if(intVal != null && intVal != 0) {
				dataImproveCnt++;
			}
		}else {
			if(value != null) {
				dataImproveCnt++;
			}
		}
	}
}

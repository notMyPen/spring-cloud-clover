package rrx.cnuo.cncommon.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 简化java对象中各字段名，并和Json相互转换工具类(转换模板需根据业务要求自行定义)
 * @author xuhongyu
 * @date 2018年12月19日
 */
public class SimplifyObjJsonUtil {
	
	/**
	 * 用户类(user_account)简化模板Map<原字段名,简化字段名>
	 */
	public static final Map<String,Integer> userAccountSimplifyTemplate = new HashMap<>();
	
	/**
	 * 用户类(user_account)还原模板Map<简化字段名,原字段名>
	 */
	public static final Map<Integer,String> userAccountRestoreTemplate = new HashMap<>();
	
	/**
	 * 用户类(user_passport)简化模板Map<原字段名,简化字段名>
	 */
	public static final Map<String,Integer> userPassportSimplifyTemplate = new HashMap<>();
	
	/**
	 * 用户类(user_passport)还原模板Map<简化字段名,原字段名>
	 */
	public static final Map<Integer,String> userPassportRestoreTemplate = new HashMap<>();
	
	/**
	 * 用户类(user_basic_info)简化模板Map<原字段名,简化字段名>
	 */
	public static final Map<String,Integer> userBasicInfoSimplifyTemplate = new HashMap<>();
	
	/**
	 * 用户类(user_basic_info)还原模板Map<简化字段名,原字段名>
	 */
	public static final Map<Integer,String> userBasicInfoRestoreTemplate = new HashMap<>();
	
	/**
	 * 用户类(user_detail_info)简化模板Map<原字段名,简化字段名>
	 */
	public static final Map<String,Integer> userDetailInfoSimplifyTemplate = new HashMap<>();
	
	/**
	 * 用户类(user_detail_info)还原模板Map<简化字段名,原字段名>
	 */
	public static final Map<Integer,String> userDetailInfoRestoreTemplate = new HashMap<>();
	
	static{
		userAccountSimplifyTemplate.put("uid",0);
		userAccountSimplifyTemplate.put("paySalt",1);
		userAccountSimplifyTemplate.put("payPassword",2);
		userAccountSimplifyTemplate.put("balance",3);
		userAccountSimplifyTemplate.put("withdrawBalance",4);
		userAccountSimplifyTemplate.put("openAccount",5);
		
		userAccountRestoreTemplate.put(0,"uid");
		userAccountRestoreTemplate.put(1,"paySalt");
		userAccountRestoreTemplate.put(2,"payPassword");
		userAccountRestoreTemplate.put(3,"balance");
		userAccountRestoreTemplate.put(4,"withdrawBalance");
		userAccountRestoreTemplate.put(5,"openAccount");
		
		userPassportSimplifyTemplate.put("uid", 0);
		userPassportSimplifyTemplate.put("registTel", 1);
		userPassportSimplifyTemplate.put("openId", 2);
		userPassportSimplifyTemplate.put("miniOpenId", 3);
		userPassportSimplifyTemplate.put("nickName", 4);
		userPassportSimplifyTemplate.put("avatarUrl", 5);
		userPassportSimplifyTemplate.put("subscribe", 6);
		
		userPassportRestoreTemplate.put(0,"uid");
		userPassportRestoreTemplate.put(1,"registTel");
		userPassportRestoreTemplate.put(2,"openId");
		userPassportRestoreTemplate.put(3,"miniOpenId");
		userPassportRestoreTemplate.put(4,"nickName");
		userPassportRestoreTemplate.put(5,"avatarUrl");
		userPassportRestoreTemplate.put(6,"subscribe");
		
		
		userBasicInfoSimplifyTemplate.put("uid",0);
		userBasicInfoSimplifyTemplate.put("idCardNo",1);
		userBasicInfoSimplifyTemplate.put("gender",2);
		userBasicInfoSimplifyTemplate.put("wxAccount",3);
		userBasicInfoSimplifyTemplate.put("birthday",4);
		userBasicInfoSimplifyTemplate.put("zodiac",5);
		userBasicInfoSimplifyTemplate.put("constellation",6);
		userBasicInfoSimplifyTemplate.put("height",7);
		userBasicInfoSimplifyTemplate.put("figureList",8);
		userBasicInfoSimplifyTemplate.put("homeProvinceId",9);
		userBasicInfoSimplifyTemplate.put("homeCityId",10);
		userBasicInfoSimplifyTemplate.put("homeProvinceCity",11);
		userBasicInfoSimplifyTemplate.put("nowProvinceId",12);
		userBasicInfoSimplifyTemplate.put("nowCityId",13);
		userBasicInfoSimplifyTemplate.put("nowProvinceCity",14);
		userBasicInfoSimplifyTemplate.put("maritalStatus",15);
		userBasicInfoSimplifyTemplate.put("haveChildren",16);
		userBasicInfoSimplifyTemplate.put("characterList",17);
		userBasicInfoSimplifyTemplate.put("academic",18);
		userBasicInfoSimplifyTemplate.put("collegeId",19);
		userBasicInfoSimplifyTemplate.put("college",20);
		userBasicInfoSimplifyTemplate.put("schoolType",21);
		userBasicInfoSimplifyTemplate.put("yearIncome",22);
		userBasicInfoSimplifyTemplate.put("houseStatus",23);
		userBasicInfoSimplifyTemplate.put("carStatus",24);
		userBasicInfoSimplifyTemplate.put("assetLevel",25);
		
		userBasicInfoRestoreTemplate.put(0,"uid");
		userBasicInfoRestoreTemplate.put(1,"idCardNo");
		userBasicInfoRestoreTemplate.put(2,"gender");
		userBasicInfoRestoreTemplate.put(3,"wxAccount");
		userBasicInfoRestoreTemplate.put(4,"birthday");
		userBasicInfoRestoreTemplate.put(5,"zodiac");
		userBasicInfoRestoreTemplate.put(6,"constellation");
		userBasicInfoRestoreTemplate.put(7,"height");
		userBasicInfoRestoreTemplate.put(8,"figureList");
		userBasicInfoRestoreTemplate.put(9,"homeProvinceId");
		userBasicInfoRestoreTemplate.put(10,"homeCityId");
		userBasicInfoRestoreTemplate.put(11,"homeProvinceCity");
		userBasicInfoRestoreTemplate.put(12,"nowProvinceId");
		userBasicInfoRestoreTemplate.put(13,"nowCityId");
		userBasicInfoRestoreTemplate.put(14,"nowProvinceCity");
		userBasicInfoRestoreTemplate.put(15,"maritalStatus");
		userBasicInfoRestoreTemplate.put(16,"haveChildren");
		userBasicInfoRestoreTemplate.put(17,"characterList");
		userBasicInfoRestoreTemplate.put(18,"academic");
		userBasicInfoRestoreTemplate.put(19,"collegeId");
		userBasicInfoRestoreTemplate.put(20,"college");
		userBasicInfoRestoreTemplate.put(21,"schoolType");
		userBasicInfoRestoreTemplate.put(22,"yearIncome");
		userBasicInfoRestoreTemplate.put(23,"houseStatus");
		userBasicInfoRestoreTemplate.put(24,"carStatus");
		userBasicInfoRestoreTemplate.put(25,"assetLevel");
		
		userDetailInfoSimplifyTemplate.put("uid",0);
		userDetailInfoSimplifyTemplate.put("companyName",1);
		userDetailInfoSimplifyTemplate.put("companyType",2);
		userDetailInfoSimplifyTemplate.put("industryType",3);
		userDetailInfoSimplifyTemplate.put("rankType",4);
		userDetailInfoSimplifyTemplate.put("dishonestCreditResult",5);
		userDetailInfoSimplifyTemplate.put("duotouLendResult",6);
		userDetailInfoSimplifyTemplate.put("parentalSituation",7);
		userDetailInfoSimplifyTemplate.put("relationWithParentsList",8);
		userDetailInfoSimplifyTemplate.put("onlyChild",9);
		userDetailInfoSimplifyTemplate.put("haveBrother",10);
		userDetailInfoSimplifyTemplate.put("haveYoungerBrother",11);
		userDetailInfoSimplifyTemplate.put("haveSister",12);
		userDetailInfoSimplifyTemplate.put("haveYoungerSister",13);
		userDetailInfoSimplifyTemplate.put("interestList",14);
		userDetailInfoSimplifyTemplate.put("likeEatList",15);
		userDetailInfoSimplifyTemplate.put("marryPlan",16);
		userDetailInfoSimplifyTemplate.put("childPlan",17);
		userDetailInfoSimplifyTemplate.put("idealPartnerTypeList",18);
		userDetailInfoSimplifyTemplate.put("consumeAttitude",19);
		userDetailInfoSimplifyTemplate.put("activePursuit",20);
		userDetailInfoSimplifyTemplate.put("mindEmotionExperiences",21);
		userDetailInfoSimplifyTemplate.put("singleReasonList",22);
		userDetailInfoSimplifyTemplate.put("faith",23);
		userDetailInfoSimplifyTemplate.put("smokDrink",24);
		userDetailInfoSimplifyTemplate.put("housework",25);
		
		userDetailInfoRestoreTemplate.put(0,"uid");
		userDetailInfoRestoreTemplate.put(1,"companyName");
		userDetailInfoRestoreTemplate.put(2,"companyType");
		userDetailInfoRestoreTemplate.put(3,"industryType");
		userDetailInfoRestoreTemplate.put(4,"rankType");
		userDetailInfoRestoreTemplate.put(5,"dishonestCreditResult");
		userDetailInfoRestoreTemplate.put(6,"duotouLendResult");
		userDetailInfoRestoreTemplate.put(7,"parentalSituation");
		userDetailInfoRestoreTemplate.put(8,"relationWithParentsList");
		userDetailInfoRestoreTemplate.put(9,"onlyChild");
		userDetailInfoRestoreTemplate.put(10,"haveBrother");
		userDetailInfoRestoreTemplate.put(11,"haveYoungerBrother");
		userDetailInfoRestoreTemplate.put(12,"haveSister");
		userDetailInfoRestoreTemplate.put(13,"haveYoungerSister");
		userDetailInfoRestoreTemplate.put(14,"interestList");
		userDetailInfoRestoreTemplate.put(15,"likeEatList");
		userDetailInfoRestoreTemplate.put(16,"marryPlan");
		userDetailInfoRestoreTemplate.put(17,"childPlan");
		userDetailInfoRestoreTemplate.put(18,"idealPartnerTypeList");
		userDetailInfoRestoreTemplate.put(19,"consumeAttitude");
		userDetailInfoRestoreTemplate.put(20,"activePursuit");
		userDetailInfoRestoreTemplate.put(21,"mindEmotionExperiences");
		userDetailInfoRestoreTemplate.put(22,"singleReasonList");
		userDetailInfoRestoreTemplate.put(23,"faith");
		userDetailInfoRestoreTemplate.put(24,"smokDrink");
		userDetailInfoRestoreTemplate.put(25,"housework");
	}

	/**
	 * 根据原始java对象获取该对象的字段名称简化版的Map
	 * @param @param obj java对象
	 * @param @param simplifyTemplate 简化模板Map<原字段名,简化字段名>
	 * @return Map<Integer,String>
	 */
	public static <T> void getSimplifyMapFromOriginObj(Map<Integer,Object> restoreTemplate,Object obj,Class<T> clazz,
				Map<String,Integer> simplifyTemplate) throws Exception{
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);//使能获取到对象私有变量值
			String oldFieldName = field.getName();//原对象字段名
			if(simplifyTemplate.containsKey(oldFieldName) && field.get(obj) != null){
				Integer newFieldName = simplifyTemplate.get(oldFieldName);
				restoreTemplate.put(newFieldName, field.get(obj));
			}
		}
		if(clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)){
			getSimplifyMapFromOriginObj(restoreTemplate, obj, clazz.getSuperclass(), simplifyTemplate);
		}
	}
	
	/**
	 * 根据原始java对象获取该对象的字段名称简化版的JSONObject
	 * @param obj java对象
	 * @param simplifyTemplate 简化模板Map<原字段名,简化字段名>
	 * @throws Exception 
	 */
	public static <T> JSONObject getSimplifyJsonObjFromOriginObj(T obj,Map<String,Integer> simplifyTemplate) throws Exception{
		if(obj != null){
			Map<Integer,Object> restoreTemplate = new HashMap<>();
			getSimplifyMapFromOriginObj(restoreTemplate,obj,obj.getClass(), simplifyTemplate);
			if(restoreTemplate != null){
				return JSONObject.parseObject(JSONObject.toJSONString(restoreTemplate));
			}
		}
		return null;
	}
	
	/**
	 * 根据原始java对象集合获取该对象的字段名称简化版的JSONArray
	 * @param @param objList java对象集合
	 * @param @param simplifyTemplate 简化模板Map<原字段名,简化字段名>
	 * @return JSONArray
	 */
	public static <T> JSONArray getSimplifyJsonArrFromOriginObjList(List<T> objList,Map<String,Integer> simplifyTemplate) throws Exception{
		if(objList != null && objList.size() > 0){
			List<Map<Integer,Object>> restoreTemplates = new ArrayList<>();
			Map<Integer,Object> restoreTemplate = null;
			for(T obj : objList){
				restoreTemplate = new HashMap<>();
				getSimplifyMapFromOriginObj(restoreTemplate,obj,obj.getClass(), simplifyTemplate);
				if(restoreTemplate != null){
					restoreTemplates.add(restoreTemplate);
				}
			}
			return JSONArray.parseArray(JSONObject.toJSONString(restoreTemplates));
		}
		return null;
	}
	
	
	/**
	 * 根据某对象集合的字段名称简化版的json字符串获取该对象集合
	 * @param @param simplifyJsonStr 对象集合字段名简化后json字符串
	 * @param @param clazz java类型
	 * @param @param restoreTemplate 还原模板Map<简化字段名,原字段名>
	 * @return List<T>
	 */
	public static <T> List<T> getOriginObjListFromSimplifyJsonStr(String simplifyJsonStr,Class<T> clazz,Map<Integer,String> restoreTemplate) throws Exception{
		if(StringUtils.isNotBlank(simplifyJsonStr)){
			Map<String,Method> methodMap = new HashMap<>();
			getFieldNameMethodMap(methodMap,clazz);
			JSONArray jsonArr = JSONArray.parseArray(simplifyJsonStr);
			List<T> objList = new ArrayList<>();
			T obj = null;
			for(int i=0;i<jsonArr.size();i++){//多个实体对象
				JSONObject jsonObj = jsonArr.getJSONObject(i);//简化的字段名和value存在这里
				obj = getOriginObjFromSimplifyJson(jsonObj, clazz, restoreTemplate, methodMap);
				if(obj != null)
					objList.add(obj);
			}
			return objList;
		}
		return null;
	}
	
	/**
	 * 根据某对象的字段名称简化版的json字符串获取该对象
	 * @param simplifyJsonStr 对象字段名简化后json字符串
	 * @param clazz
	 * @param restoreTemplate 还原模板Map<简化字段名,原字段名>
	 * @return
	 * @throws Exception
	 * T
	 */
	public static <T> T getOriginObjFromSimplifyJsonStr(String simplifyJsonStr,Class<T> clazz,Map<Integer,String> restoreTemplate) throws Exception{
		if(StringUtils.isNotBlank(simplifyJsonStr)){
			Map<String,Method> methodMap = new HashMap<>();
			getFieldNameMethodMap(methodMap,clazz);
			JSONObject jsonObj = JSONObject.parseObject(simplifyJsonStr);
			return getOriginObjFromSimplifyJson(jsonObj, clazz, restoreTemplate, methodMap);
		}
		return null;
	}
	
	private static <T> T getOriginObjFromSimplifyJson(JSONObject jsonObj,Class<T> clazz,Map<Integer,String> restoreTemplate,Map<String,Method> methodMap) throws Exception{
		if(jsonObj != null){
			T obj = clazz.newInstance();
			for(Entry<Integer, String> entry : restoreTemplate.entrySet()){
				Integer simplifyFieldName = entry.getKey();//简化的字段名
				String originFieldName = entry.getValue();//原字段名
				Method method = methodMap.get(originFieldName);//根据原字段名，得到该字段对应的Method
				String parameterTypeName = method.getParameterTypes()[0].getName();//set方法参数类型
				Object value = null;//根据简化的字段名获取字段的值
				if(parameterTypeName.endsWith("int") || parameterTypeName.endsWith("Integer")){
					value = jsonObj.getInteger(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("long") || parameterTypeName.endsWith("Long")){
					value = jsonObj.getLong(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("short") || parameterTypeName.endsWith("Short")){
					value = jsonObj.getShort(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("boolean") || parameterTypeName.endsWith("Boolean")){
					value = jsonObj.getBoolean(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("double") || parameterTypeName.endsWith("Double")){
					value = jsonObj.getDouble(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("float") || parameterTypeName.endsWith("Float")){
					value = jsonObj.getFloat(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("byte") || parameterTypeName.endsWith("Byte")){
					value = jsonObj.getByte(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("Date")){
					value = jsonObj.getDate(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("String")){
					value = jsonObj.getString(simplifyFieldName.toString());
				}else if(parameterTypeName.endsWith("BigDecimal")){
					value = jsonObj.getBigDecimal(simplifyFieldName.toString());
				}else{
					value = jsonObj.get(simplifyFieldName.toString());
				}
				method.invoke(obj, value);//给当前实体对象的某字段赋值
			}
			return obj;
		}
		return null;
	}
	
	/**
	 * 把类(包括非Object类的父类)中的字段名和该字段对应的set方法(Method)以Map的形势返回
	 * @param clazz
	 * @return
	 * @throws Exception
	 * Map<String,Method>
	 */
	private static <T> void getFieldNameMethodMap(Map<String,Method> methodMap,Class<T> clazz) throws Exception{
		Field[] fields = clazz.getDeclaredFields();
		String fieldName = null;
		String methodStr = null;
		Method method = null;
		for(Field field: fields){
			fieldName = field.getName();
			methodStr = "set"+fieldName.toUpperCase().substring(0, 1)+fieldName.substring(1);
			method = clazz.getMethod(methodStr, field.getType());
			methodMap.put(fieldName, method);
		}
		if(clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)){
			getFieldNameMethodMap(methodMap, clazz.getSuperclass());
		}
	}
}

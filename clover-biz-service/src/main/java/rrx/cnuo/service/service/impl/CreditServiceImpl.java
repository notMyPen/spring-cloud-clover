package rrx.cnuo.service.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.CreditService;
import rrx.cnuo.service.vo.creditCenter.CreditVo;

@Slf4j
@Service
@SuppressWarnings("rawtypes")
public class CreditServiceImpl implements CreditService {

	@Override
	public JsonResult updateResultNotify(CreditVo vo) {
		log.info("------------- 信用中心认证通知今借到 updateCreditNotify ------------" + JSON.toJSONString(vo));
		int type = vo.getType();
		if (type == GlobalConstants.CREDIT_TYPE.N_XUEXIN_INFO) {// 学信
			CreditVo vo = JSONObject.parseObject(jsonStr, CreditVo.class);
            insertNotExists(vo.getUserId());

            UserCreditStatusDo userCreditStatus = new UserCreditStatusDo();
            userCreditStatus.setUserId(vo.getUserId());
            userCreditStatus.setStudentStatus(vo.getStudent());
            userCreditStatus.setXuexinCreditStatus(vo.getCreditStatus());
            userCreditStatus.setXuexinUpdTm(vo.getCreditTime());
            userCreditStatusMapper.updateByPrimaryKeySelective(userCreditStatus);
            
            if (vo.getCreditStatus() == GlobalConstants.CREDIT_STATUS.IS_SUCCESS) {
            	DegreeVerificationDo degreeVerificationDo = degreeVerificationDoMapper.selectLatestByUserId(vo.getUserId());
            	if(degreeVerificationDo != null && degreeVerificationDo.getVerified() != 3){
            		DegreeVerificationDo record = new DegreeVerificationDo();
            		record.setId(degreeVerificationDo.getId());
            		record.setVerified(3);
            		degreeVerificationDoMapper.updateByPrimaryKeySelective(record);
            	}
            	
            	UserDo userRecord = new UserDo();
                userRecord.setUserId(vo.getUserId());
                userRecord.setGmtModified(new Date());
                
            	Optional<BoardDo> boardOp = this.boardDataAccess.selectLastedBoardByUserId(vo.getUserId());
                BoardDo boardRecord = new BoardDo();
                boardRecord.setId(boardOp.get().getId());
                boardRecord.setGmtModified(new Date());
                
            	//使用通过认证学校学历覆盖用户信息
                String url = creditBaseUrl + getStudentInfo;
                TreeMap<String, String> map = new TreeMap<>();
                map.put("userId", vo.getUserId());
                map.put("systemType", systemName);
                Optional<String> responseOp = HttpClientUtil.get(url, map);
                if(responseOp.isPresent()){
                	log.info("query StudentInfo result " + responseOp.get());
                    JsonResult<List<LinkedTreeMap>> jsonResult = new Gson().fromJson(responseOp.get(), JsonResult.class);
                    if(jsonResult.getData() != null && jsonResult.getData().size() > 0){
                    	String schoolName = (String) jsonResult.getData().get(0).get("c_university");//XX大学
                        String academicName = (String) jsonResult.getData().get(0).get("c_student_level");//本科

                        List<SchoolDo> schoolDoList = this.schoolDoMapper.listSchoolBySearchKey(schoolName);
                        Long schoolId = schoolDoList.get(0).getId();//获取学校编码
                        Integer academicId = 2;//获取学历编码，默认本科
                        switch (academicName) {
                            case "专科":
                                academicId = 3;
                                break;
                            case "本科":
                                academicId = 2;
                                break;
                            case "硕士研究生":
                                academicId = 1;
                                break;
                            case "博士研究生":
                                academicId = 0;
                                break;

                        }
                        userRecord.setCollege(schoolName);
                        userRecord.setCollegeId(schoolId);
                        userRecord.setAcademic(academicId);

                        boardRecord.setCollege(schoolName);
                        boardRecord.setCollegeId(schoolId);
                        boardRecord.setAcademic(academicId);
                    }
                }
                
                UserCreditStatusVo creditStatusVo = getShowXuexinCreditStatus(vo.getUserId());
                if (isAllCredit(creditStatusVo)) {
                	userRecord.setIsVerified(true);
                	boardRecord.setIsVerified(true);
                }
                userDataAccess.updateByPrimaryKeySelective(userRecord);
                boardDataAccess.updateByPrimaryKeySelective(boardRecord);
            }
        } else if (type == GlobalConstants.CREDIT_TYPE.FACE_INFO) {// 实名认证+人脸认证
            insertNotExists(vo.getUserId());

            UserCreditStatusDo userCreditStatus = new UserCreditStatusDo();
            userCreditStatus.setUserId(vo.getUserId());
            if (vo.getCreditStatus() == GlobalConstants.CREDIT_STATUS.IS_SUCCESS) {
                JsonResult<JSONObject> result1 = userVerifyCheck(vo.getCard_no());
                if (!result1.isOk()) {
                    return result1;
                }
                //使用市名认证的信息覆盖用户相关信息
                String cardNo = vo.getCard_no();
                String birthYear = IDCardUtils.GetYear(cardNo);
                String birthMonth = IDCardUtils.GetMonth(cardNo);
                String birthDay = IDCardUtils.GetDay(cardNo);
                String constellation = IDCardUtils.GetConstellation(cardNo);

                UserDo userDo = new UserDo();
                BoardDo boardDo = new BoardDo();
                userDo.setUserId(vo.getUserId());
                userDo.setIdCardNo(vo.getCard_no());
                userDo.setFullName(vo.getUser_name());
                userDo.setBirthYear(Integer.valueOf(birthYear));
                userDo.setBirthMonth(Integer.valueOf(birthMonth));
                userDo.setBirthDay(Integer.valueOf(birthDay));
                userDo.setConstellation(constellation);
                boardDo.setBirthYear(Integer.valueOf(birthYear));
                boardDo.setBirthMonth(Integer.valueOf(birthMonth));
                boardDo.setBirthDay(Integer.valueOf(birthDay));
                boardDo.setConstellation(constellation);
                UserCreditStatusVo creditStatusVo = getShowXuexinCreditStatus(vo.getUserId());
                if (isAllCredit(creditStatusVo)) {
                    userDo.setGmtModified(new Date());
                    userDo.setIsVerified(true);
                    
                    Optional<BoardDo> boardOp = this.boardDataAccess.selectLastedBoardByUserId(vo.getUserId());
                    boardDo.setId(boardOp.get().getId());
                    boardDo.setIsVerified(true);
                    boardDo.setGmtModified(new Date());

                }
                boardDataAccess.updateByPrimaryKeySelective(boardDo);
                userDataAccess.updateByPrimaryKeySelective(userDo);

                userCreditStatus.setBaseinfoCreditStatus((byte) GlobalConstants.CREDIT_STATUS.IS_SUCCESS);
                userCreditStatus.setBaseUpdTm(vo.getCreditTime());
            }
            userCreditStatus.setFaceVerifyStatus(vo.getCreditStatus());
            userCreditStatus.setFaceVerifyUpdTm(vo.getCreditTime());
            userCreditStatusMapper.updateByPrimaryKeySelective(userCreditStatus);
        }  else if (type == GlobalConstants.CREDIT_TYPE.SHEBAO_INFO) {//社保
        	insertNotExists(vo.getUserId());
        	
        	UserCreditStatusDo userCreditStatus = new UserCreditStatusDo();
            userCreditStatus.setUserId(vo.getUserId());
        	userCreditStatus.setShebaoCreditStatus(vo.getCreditStatus());
            userCreditStatus.setShebaoUpdTm(vo.getCreditTime());
            userCreditStatusMapper.updateByPrimaryKeySelective(userCreditStatus);
            
            if (vo.getCreditStatus() == GlobalConstants.CREDIT_STATUS.IS_SUCCESS) {
            	UserDo userDo = new UserDo();
            	userDo.setUserId(vo.getUserId());
            	userDo.setGmtModified(new Date());
            	//查询社保信息，覆盖公司信息
            	ReturnAccreditInfo accreditInfo = getAccreditInfo(vo.getUserId(), "sb");
            	if(accreditInfo != null && accreditInfo.getShebaoBaseInfo() != null){
            		if(StringUtils.isNotBlank(accreditInfo.getShebaoBaseInfo().getCompany_name())){
                        userDo.setCompany(accreditInfo.getShebaoBaseInfo().getCompany_name());
            		}
            	}
            	UserCreditStatusVo creditStatusVo = getShowXuexinCreditStatus(vo.getUserId());
            	if(isAllCredit(creditStatusVo)){
            		userDo.setIsVerified(true);
            	}
            	userDataAccess.updateByPrimaryKeySelective(userDo);
            }
        }  else if (type == GlobalConstants.CREDIT_TYPE.GJJ_INFO) {//公积金
        	insertNotExists(vo.getUserId());
        	
        	UserCreditStatusDo userCreditStatus = new UserCreditStatusDo();
            userCreditStatus.setUserId(vo.getUserId());
        	userCreditStatus.setGjjCreditStatus(vo.getCreditStatus());
            userCreditStatus.setGjjUpdTm(vo.getCreditTime());
            userCreditStatusMapper.updateByPrimaryKeySelective(userCreditStatus);
            
            if (vo.getCreditStatus() == GlobalConstants.CREDIT_STATUS.IS_SUCCESS) {
            	UserDo userDo = new UserDo();
            	userDo.setUserId(vo.getUserId());
            	userDo.setGmtModified(new Date());
            	//查询公积金信息，覆盖公司信息
            	ReturnAccreditInfo accreditInfo = getAccreditInfo(vo.getUserId(), "gjj");
            	if(accreditInfo != null && accreditInfo.getGjjBaseInfo() != null){
            		if(StringUtils.isNotBlank(accreditInfo.getGjjBaseInfo().getCorp_name())){
                        userDo.setCompany(accreditInfo.getGjjBaseInfo().getCorp_name());
            		}
            	}
            	UserCreditStatusVo creditStatusVo = getShowXuexinCreditStatus(vo.getUserId());
            	if(isAllCredit(creditStatusVo)){
            		userDo.setIsVerified(true);
            	}
            	userDataAccess.updateByPrimaryKeySelective(userDo);
            }
        }
        return result;
	}
}

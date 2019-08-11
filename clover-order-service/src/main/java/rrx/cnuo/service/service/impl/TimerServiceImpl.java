package rrx.cnuo.service.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.MqSendTool;
import rrx.cnuo.service.dao.SystemTaskMapper;
import rrx.cnuo.service.po.SystemTask;
import rrx.cnuo.service.service.TimerService;

@Slf4j
@Service
public class TimerServiceImpl implements TimerService {

    /*@Autowired
    private SystemCapitalMapper systemCapitalMapper;*/

    /*@Autowired
    private SystemCapitalListMapper systemCapitalListMapper;

    @Autowired
    private TradeMapper tradeMapper;
	
	@Autowired
	private UserAccountListMapper userAccountListMapper;*/
	
	@Autowired
	private SystemTaskMapper systemTaskMapper;
	
	@Autowired
	private MqSendTool mqSender;
	
	/*@Override
    public void updateCapital() throws Exception {
        //今借到使用的所有代付通道:依次是：0-掌上汇通、5-易联、7-合利宝、8-易宝
        //1，插入今天的垫资汇总
        SystemCapital systemCapitalParam = new SystemCapital();
        String[] paymentList = configBean.getPaymentChannels() == null ? null : configBean.getPaymentChannels().split(",");
        if (paymentList != null && paymentList.length > 0) {
            for (String tradeType : paymentList) {
                Long capicalTotalAmount = userAccountListMapper.getSysWithdrawAmount(Byte.parseByte(tradeType));
                systemCapitalParam.setPayType(Byte.parseByte(tradeType));
                systemCapitalParam.setAmt(capicalTotalAmount);
                systemCapitalMapper.insert(systemCapitalParam);
                systemCapitalParam.clear();
            }
        }

        //2，计算昨天的垫资统计
        String yesterday = DateUtil.format(DateUtil.getDate(new Date(), -1), "yyyy-MM-dd");
        List<SystemCapital> systemCapitals = systemCapitalMapper.getSystemCapitalByDate(yesterday);
        for (SystemCapital systemCapital : systemCapitals) {
        	systemCapitalParam.setDate(systemCapital.getDate());
        	systemCapitalParam.setPayType(systemCapital.getPayType());
            Long capitalAmount = systemCapitalListMapper.getCapitalAmount(systemCapital.getPayType().intValue());//通道已垫资未回款金额
            systemCapitalParam.setCapitalAmt(capitalAmount);
            Long capitalLeftAmount = systemCapital.getAmt() - capitalAmount;//通道可垫资余额
            systemCapitalParam.setLeftAmt(capitalLeftAmount > 0 ? capitalLeftAmount : 0);
            Long userNotCashAmt = userAccountService.getUserNotCashTotalAmt();//用户未提现金额
            systemCapitalParam.setNoCashAmt(userNotCashAmt);
            systemCapitalMapper.updateByPrimaryKey(systemCapitalParam);
        }
    }

    @Override
    public void updateCapitalAmt(String todayStr) throws Exception {
        Date today = DateUtil.format(todayStr, "yyyy-MM-dd");
        SystemCapital systemCapitalParam = new SystemCapital();
        systemCapitalParam.setDate(today);
        String[] paymentList = configBean.getPaymentChannels() == null ? null : configBean.getPaymentChannels().split(",");
        if (paymentList != null && paymentList.length > 0) {
            for (String tradeTypeStr : paymentList) {
                byte tradeType = Byte.parseByte(tradeTypeStr);
                //更新可垫资总额
                Long capicalTotalAmount = userAccountListMapper.getSysWithdrawAmount(tradeType);
                systemCapitalParam.setPayType(tradeType);
                systemCapitalParam.setAmt(capicalTotalAmount);
                systemCapitalMapper.updateByPrimaryKey(systemCapitalParam);
            }
        }
    }*/

    /*@Override
    public void deleteUnTradeRecords() {
        tradeMapper.deleteUnTradeYAYRecords();
        tradeMapper.deleteUnTradeWAYRecords();
    }*/
    
	public int addTaskLog(String functionName){
		try{
			log.info("TimerController " + functionName + " ++++++++++++");
			SystemTask task = new SystemTask();
			task.setFunctionName(functionName);
			systemTaskMapper.insertSelective(task);
			return task.getId();
		}catch(Exception e){
			log.error("addTaskLog", e);
		}
		return 0;
	}
	
	public void updateTaskLog(String functionName, int id, byte result, Exception e1){
		try{
			log.info("TimerController " + functionName + " ------------");
			if(result==0){
				log.error("TimerController " + functionName, e1);
				//发送系统微信消息
		        JSONObject msgObj = new JSONObject();
				msgObj.put(Const.MQ.MQ_HANDLER_TYPE_KEY, Const.MqHandleType.SEND_WX_MSG.getCode());
				msgObj.put("type", Const.WeChatMsgEnum.ERROR_WARNING.getCode());
				msgObj.put("taskStatus", "失败"); //业务状态
				msgObj.put("msg", "TimerController " + functionName + "接口报错，请查看具体error日志");//信息内容
				mqSender.normalMqSender(UUID.randomUUID().toString(),msgObj);
			}
			SystemTask task = new SystemTask();
			task.setId(id);
			task.setResult(result);
			systemTaskMapper.updateByPrimaryKeySelective(task);
		}catch(Exception e){
			log.error("updateTaskLog", e);
		}
	}
}

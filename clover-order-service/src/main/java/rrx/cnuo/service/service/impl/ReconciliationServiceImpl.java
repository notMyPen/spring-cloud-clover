package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.utils.MqSendTool;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;
import rrx.cnuo.service.accessory.consts.TradeConst;
import rrx.cnuo.service.accessory.consts.TradeConst.TradeStatus;
import rrx.cnuo.service.dao.TradeAbnormalMapper;
import rrx.cnuo.service.dao.TradeMapper;
import rrx.cnuo.service.dao.TradeReconciliationMapper;
import rrx.cnuo.service.dao.UserAccountListMapper;
import rrx.cnuo.service.feignclient.UserFeignService;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.TradeReconciliation;
import rrx.cnuo.service.po.UserAccountList;
import rrx.cnuo.service.service.PayProcessService;
import rrx.cnuo.service.service.ReconciliationService;
import rrx.cnuo.service.vo.AbnormalTradeGroupVo;
import rrx.cnuo.service.vo.paycenter.PayServiceVo;
import rrx.cnuo.service.vo.paycenter.Record;
import rrx.cnuo.service.vo.paycenter.ReturnReconciliationVo;

@Slf4j
@Service
public class ReconciliationServiceImpl implements ReconciliationService {

	@Autowired private TradeReconciliationMapper tradeReconciliationMapper;
	@Autowired private UserAccountListMapper userAccountListMapper;
	@Autowired private TradeAbnormalMapper tradeAbnormalMapper;
	@Autowired private TradeMapper tradeMapper;
	@Autowired private PayProcessService payProcessService;
	@Autowired private UserFeignService userFeignService;
	@Autowired private RedisTool redis;
	@Autowired private MqSendTool mqSender;
	@Autowired private PayCenterConfigBean payCenterConfigBean;
	
	public void updateReconBusinessProcess(Long tradeId, Boolean result,Boolean isRecvBank) throws Exception {
		if(isRecvBank){
			//支付中心的订单状态（1:成功, 2:失败）
			int orderStatus = result ? 1 : 2;
			payProcessService.updateReceiveBank(tradeId, orderStatus, "");
		}
		if(result){
			//如果支付成功
			Trade trade = tradeMapper.selectByPrimaryKey(tradeId);
			List<JSONObject> list = null;
			if(!trade.getWithdrawType()){//代收
				//对账时，如果是充值相关业务，更新可提现余额
				if(trade.getTradeType() != TradeConst.TradeType.BALANCE_PAY.getCode()){
					//非余额支付时，所有用户更新可提现余额
					list = userAccountListMapper.getAccountListAmtNotSysAndBankByTradeId(tradeId);
				}else{
					//余额支付时，除支付人以外的用户更新可提现余额
					UserAccountList userAccountList = new UserAccountList();
                	userAccountList.setTradeId(trade.getId());
                	userAccountList.setUid(trade.getUid());
                    list = userAccountListMapper.getAccountListAmtNotSysBankAndUidByTradeId(userAccountList);
				}
			}else{//代付
				if(trade.getBusinessType() != TradeConst.PayBusinessType.WITHDRAW.getCode()){
					//对账时，代付的如果是非提现（即退款业务）需要更新可提现余额
					list = userAccountListMapper.getAccountListAmtNotSysAndBankByTradeId(tradeId);
				}else{
					//对账时，代付的如果是提现业务，不需要更新可提现余额(因为提现业务的可提现余额是在报回盘时计算的)
					payProcessService.checkEnd(tradeId,trade.getBusinessType());
				}
			}
			if(list != null && list.size() > 0){
				String userAccountListStatis = JSON.toJSONString(list);
				userFeignService.updateUserAccountAccumulateAboutOrder(userAccountListStatis,(byte) 2,false);
			}
			
			//做trade、userAccountList表对账状态变更
			Trade tradeParam = new Trade();
			tradeParam.setId(tradeId);
			tradeParam.setReconciliationStatus(true);
			tradeParam.setReconciliationTime(DateUtil.getSecond(new Date()));
			tradeMapper.updateByPrimaryKeySelective(tradeParam);
			
			UserAccountList userAccountListParam = new UserAccountList();
			userAccountListParam.setTradeId(tradeId);
			userAccountListParam.setReconciliationStatus(true);
			userAccountListParam.setReconciliationTime(DateUtil.getSecond(new Date()));
			userAccountListMapper.updateByPrimaryKeySelective(userAccountListParam);
		}
	}

	@Override
	public void updateSendMsgByTradeAbnormal(String curDateStr) throws Exception {
		Date curDate = DateUtil.format(curDateStr,DateUtil.DATE_YYYY_MM_DD);
		String fourDayAgoStr = DateUtil.format(DateUtil.getDate(curDate, -4),DateUtil.DATE_YYYY_MM_DD);
		List<AbnormalTradeGroupVo> list = tradeAbnormalMapper.getTradeAbnormalList(curDateStr);
		Map<Byte,StringBuffer> map = new HashMap<Byte,StringBuffer>();
		for(AbnormalTradeGroupVo vo : list){
			if(map.get(vo.getTradeType()) == null){
				StringBuffer sb = new StringBuffer();
				sb.append(TradeConst.TradeType.getTradeType(vo.getTradeType()).getMessage()+"，四天前("+fourDayAgoStr+")账单情况：\n");
				sb.append(getAbnormalTypeName(vo.getAbnormalType()) + vo.getAbTypeCount()+"个\n");
				map.put(vo.getTradeType(), sb);
			}else{
				StringBuffer sb = map.get(vo.getTradeType());
				sb.append(getAbnormalTypeName(vo.getAbnormalType()) + vo.getAbTypeCount()+"个\n");
			}
		}
		StringBuffer msgBuffer = new StringBuffer();
		for(Entry<Byte, StringBuffer> entry : map.entrySet()){
			msgBuffer.append(entry.getValue().toString());
		}
		if(StringUtils.isBlank(msgBuffer.toString())){
			msgBuffer.append("无异常记录");
		}
		tradeAbnormalMapper.updateTradeAbnormalSendMsgStatus(curDateStr);
		
		//发送系统微信消息
        JSONObject msgObj = new JSONObject();
		msgObj.put(Const.MQ.MQ_HANDLER_TYPE_KEY, Const.MqHandleType.SEND_WX_MSG.getCode());
//		msgObj.put("uid", uid);
		msgObj.put("type", Const.WeChatMsgEnum.RECENCILIATION_RESULT.getCode());
		msgObj.put("taskStatus", "失败"); //业务状态
		msgObj.put("msg", msgBuffer.toString());//信息内容
		mqSender.normalMqSender(UUID.randomUUID().toString(),msgObj);
	}
	
	private String getAbnormalTypeName(byte abnormalType){
		String result = "";
		//abnormalType：1-该订单我们有第三方没有；2-该订单我们没有第三方有；3，状态不一致；4-金额不一致；5-状态和金额都不一致
		if(abnormalType == 1){
			result = "我们有第三方没有：";
		}else if(abnormalType == 2){
			result = "我们没有第三方有：";
		}else if(abnormalType == 3){
			result = "状态不一致：";
		}else if(abnormalType == 4){
			result = "金额不一致：";
		}else{
			result = "状态和金额都不一致：";
		}
		return result;
	}
	
	@Override
	public List<Trade> getUnReconciliationTrades(String reconDate,int dayCnt) throws Exception {
		Date reconciliationDate = DateUtil.format(reconDate,DateUtil.DATE_YYYY_MM_DD);//对账日(到日)
		Date preWorkDate = DateUtil.getDate(reconciliationDate, -dayCnt);//上一个工作日(到日)
		
		// 对上一个工作日凌晨开始到当天凌晨之间的所有支付成功订对账
		Trade tradeParam = new Trade();
		tradeParam.setTradeStatus(TradeConst.TradeStatus.SUCCESS.getCode());
		tradeParam.setReconciliationStatus(false);
		tradeParam.setSendBeginTime(DateUtil.getSecond(preWorkDate));
		tradeParam.setSendEndTime(DateUtil.getSecond(reconciliationDate));
		return tradeMapper.getTradeByParam(tradeParam);
	}

	public void updateDownloadAndSaveBill(String reconDateStr) throws Exception{
		//去支付中心申请对账日期内的账单信息
		List<Byte> channels = new ArrayList<Byte>();
		channels.add(TradeConst.TradeType.MINI_WX_PAY.getCode());
		channels.add(TradeConst.TradeType.WX_WITHDRAW.getCode());
		channels.add(TradeConst.TradeType.BALANCE_PAY.getCode());
		PayServiceVo payServiceVo = new PayServiceVo(payCenterConfigBean.getApiKey(),payCenterConfigBean.getApiSecret(),payCenterConfigBean.getMerchantSign());
		payServiceVo.setReconciliationDate(reconDateStr);
		payServiceVo.setChannels(channels);
		JsonResult<ReturnReconciliationVo> returnReconciliationResult = 
				HttpClient.doPostWrapResult(payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayReconciliation(),
						payServiceVo.toReconciliationApplyJSONString(),ReturnReconciliationVo.class);
		if(returnReconciliationResult.getStatus() == JsonResult.SUCCESS && returnReconciliationResult.getData() != null){
			ReturnReconciliationVo returnReconciliationVo = returnReconciliationResult.getData();
			if(returnReconciliationVo.getRespCode() == JsonResult.SUCCESS){
				List<Record> records = returnReconciliationVo.getRecords();
				//1，将账单日内的所有账单存到数据库中(便于后续分类查询)
				if(records != null && records.size() > 0){
					tradeReconciliationMapper.insertBatch(records);
				}
			}else{
				log.info("对账失败："+returnReconciliationVo.getRespMsg());
			}
		}else{
			log.info("对账请求失败："+returnReconciliationResult.getMsg());
		}
	}
	
	public JSONObject getRrealReconciliationInfo(Date reconDate,Date compareDate,Date compareDateBefore) throws Exception{
		//获取账单日是4天前(那一天)的对账单信息
		TradeReconciliation radeReconciliationParam = new TradeReconciliation();
		radeReconciliationParam.setBillDate(DateUtil.getSecond(compareDate));
//		radeReconciliationParam.setBillStatus(false);//未对账的
		List<TradeReconciliation> thisTimeGroupBills = tradeReconciliationMapper.getByParam(radeReconciliationParam);
		
		//5，获取4天前(那一天)的已报盘成功的交易订单信息
		Trade tradeParam = new Trade();
		tradeParam.setSendBeginTime(DateUtil.getSecond(compareDate));
		tradeParam.setSendEndTime(DateUtil.getSecond(compareDateBefore));//取4天前（那1天内）已报盘未对账的订单
		tradeParam.setTradeStatusBegin(TradeStatus.APPLY.getCode());//交易状态大于这个值
		tradeParam.setTradeStatusEnd(TradeStatus.FAIL.getCode());//交易状态小于这个值
//		tradeParam.setReconciliationStatus(false);
		List<Trade> thisTimeGroupTrades = tradeMapper.getTradeByParam(tradeParam);
		
		//6，对比第三方该对账日内的账单和今借到在该对账日内对应的账单时间范围内的订单
		List<TradeReconciliation> tradeAbnormalList = new ArrayList<TradeReconciliation>();//所有异常订单
		List<TradeReconciliation> tradeNormalList = new ArrayList<TradeReconciliation>();//所有正常订单
		List<TradeReconciliation> unRecvBankTradeList = new ArrayList<TradeReconciliation>();//所有我们还没回盘，对账文件就有了的订单
		boolean exists = false;//是否存在
		for(Trade trade : thisTimeGroupTrades){
			exists = false;
			for(TradeReconciliation reconciliation : thisTimeGroupBills){
				if(trade.getId().equals(reconciliation.getTradeId())){//双方都有的订单
					exists = true;
					//比较订单状态和金额是否相同
					if(!trade.getAmount().equals(reconciliation.getAmount()) && 
							trade.getTradeStatus().intValue() == (reconciliation.getResult() ? 3 : 4)){
						//金额不相等,状态相等
						reconciliation.setAbnormalType((byte)4);
						tradeAbnormalList.add(reconciliation);
					}else if(trade.getAmount().equals(reconciliation.getAmount()) && 
							trade.getTradeStatus().intValue() != (reconciliation.getResult() ? 3 : 4)){
						//金额相等,状态不相等
						if(trade.getTradeStatus().intValue() == TradeConst.TradeStatus.COMFIRM.getCode()){
							//如果订单未回盘，则置为回盘
							unRecvBankTradeList.add(reconciliation);
						}else{
							reconciliation.setAbnormalType((byte)3);
							tradeAbnormalList.add(reconciliation);
						}
					}else if(!trade.getAmount().equals(reconciliation.getAmount()) && 
							trade.getTradeStatus().intValue() != (reconciliation.getResult() ? 3 : 4)){
						//金额不相等,状态也不相等
						reconciliation.setAbnormalType((byte)5);
						tradeAbnormalList.add(reconciliation);
					}else{
						//金额和状态都相等的正常订单
						tradeNormalList.add(reconciliation);
					}
					break;
				}
			}
			if(!exists){
				//第三方的都遍历完了还没有，这个订单是我们有第三方没有的异常订单
				tradeAbnormalList.add(new TradeReconciliation(trade.getId(), DateUtil.getSecond(reconDate),DateUtil.getSecond(compareDate),
						trade.getWithdrawType() ? (byte)1 : 0, (trade.getTradeStatus().intValue() == 3) ? true : false, 
								trade.getAmount(), trade.getTradeType(),(byte)1));
			}
		}
		for(TradeReconciliation reconciliation : thisTimeGroupBills){
			exists = false;
			for(Trade trade : thisTimeGroupTrades){
				if(trade.getId().equals(reconciliation.getTradeId())){//双方都有的订单
					exists = true;
					break;
				}
			}
			//我们的订单都遍历完了还没有，这个订单是我们没有有第三方有的异常订单
			if(!exists){
				reconciliation.setAbnormalType((byte)2);
				tradeAbnormalList.add(reconciliation);
			}
		}
		JSONObject result = new JSONObject();
		result.put("unRecvBankTradeList", unRecvBankTradeList);
		result.put("tradeNormalList", tradeNormalList);
		result.put("tradeAbnormalList", tradeAbnormalList);
		return result;
	}
	
	@Override
	public List<Date> insertAbnormalDatesBillAgain() throws Exception {
		List<Date> tradeAbnormalDates = tradeAbnormalMapper.getTradeAbnormalDates();
		Set<Date> downloadAgainDates = new HashSet<>();
		//每个异常对账日的前三天，加上该对账日一共四天都要重新下载(以此保证该异常对账日下的账单必被下载到)
		String mapStr = redis.getString(Const.REDIS_PREFIX.BILL_LIMIT_DOWNLOAD_CNT);
		JSONObject map = null;
		if(StringUtils.isNotBlank(mapStr)){
			map = JSONObject.parseObject(mapStr);
		}
		for(Date abnormalDate : tradeAbnormalDates){
			String key = DateUtil.format(abnormalDate, DateUtil.DATE_YYYY_MM_DD);
			//同一个异常对账日，最多重新下载三次对账单，超过三次不会重新下载重新对账(运维可以通过redis让该异常对账日再次重新下载)
			if(map == null){
				map = new JSONObject();
				map.put(key, 1);
			}else{
				if(map.containsKey(key)){
					int cnt = map.getInteger(key);
					if(cnt >= 3){
						continue;
					}else{
						map.put(key, cnt + 1);
					}
				}else{
					map.put(key, 1);
				}
			}
			downloadAgainDates.add(abnormalDate);
			downloadAgainDates.add(DateUtil.getDate(abnormalDate, -1));//一天前
			downloadAgainDates.add(DateUtil.getDate(abnormalDate, -2));//二天前
			downloadAgainDates.add(DateUtil.getDate(abnormalDate, -3));//三天前
		}
		if(map != null){
			redis.set(Const.REDIS_PREFIX.BILL_LIMIT_DOWNLOAD_CNT, map.toJSONString());
		}
		//重新下载对账单
		for(Date downloadAgainDate : downloadAgainDates){
			updateDownloadAndSaveBill(DateUtil.format(downloadAgainDate, DateUtil.DATE_YYYY_MM_DD));
		}
		return tradeAbnormalDates;
	}

	@Override
	public void updateHandlerAbnormalBatch(List<Long> tradeReconciliationIdList,
			List<TradeReconciliation> tradeAbnormalList,Date reconDate) throws Exception{
		if(tradeReconciliationIdList.size() > 0){
			//如果补对账后将不准确的异常订单去除
			tradeAbnormalMapper.daleteTradeAbnormalBatch(tradeReconciliationIdList);
		}
		//对账异常的allTradeAbnormalList，插入到异常订单表中
		if(tradeAbnormalList.size() > 0){
			tradeAbnormalMapper.insertTradeAbnormalBatch(tradeAbnormalList);
		}else{
			//如果该对账日reconDate中没有异常对账记录，将异常限制次数总的该对账日删除
			String mapStr = redis.getString(Const.REDIS_PREFIX.BILL_LIMIT_DOWNLOAD_CNT);
			if(StringUtils.isNotBlank(mapStr)){
				JSONObject map = JSONObject.parseObject(mapStr);
				String key = DateUtil.format(reconDate, DateUtil.DATE_YYYY_MM_DD);
				if(map.containsKey(key)){
					map.remove(key);
					redis.set(Const.REDIS_PREFIX.BILL_LIMIT_DOWNLOAD_CNT, map.toJSONString());
				}
			}
		}
	}
}

package rrx.cnuo.service.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.TradeReconciliation;
import rrx.cnuo.service.service.PayProcessService;
import rrx.cnuo.service.service.ReconciliationService;
import rrx.cnuo.service.service.TimerService;
import rrx.cnuo.service.vo.paycenter.PayServiceVo;
import rrx.cnuo.service.vo.paycenter.ReturnOrderQueryVo;
import rrx.cnuo.service.vo.paycenter.ReturnReconciliationDateVo;


@Slf4j
@RestController
@RequestMapping("/time_abcdefg")
@SuppressWarnings("rawtypes")
public class TimerController {

	@Autowired private TimerService timerService;
	@Autowired private PayProcessService payProcessService;
	@Autowired private ReconciliationService reconciliationService;
	@Autowired private RedisTool redisService;
	@Autowired private BasicConfig basicConfig;
	@Autowired private PayCenterConfigBean payCenterConfigBean;
	
	private static final String UPDATE_CAPITAL = "updateCapital";
	private static final String UPDATE_STATIS = "updateStatis";
	private static final String RECONCILIATION_MAKE_BELIEVE = "reconciliationMakeBelieve";
	private static final String RECONCILIATIONS_TIMER = "reconciliationsTimer";
	private static final String REMOVE_EXPIRED_FORMID = "removeExpiredFormid";

	/**
	 * 配置定时任务名字，执行的时间，执行的函数，定时任务执行的时候，是按照配置的顺序执行，因此要注意配置顺序
	 * @return
	 */
	private List<FunctionTimer> getlistFunc() {
		return Arrays.asList(/*new FunctionTimer(UPDATE_CAPITAL, "00:00", this::updateCapital, true),
				new FunctionTimer(UPDATE_STATIS, "00:00", this::updateStatis, true),*/
				new FunctionTimer(REMOVE_EXPIRED_FORMID, "00:00", this::removeExpiredFormid, true),
				new FunctionTimer(RECONCILIATION_MAKE_BELIEVE, "12:00", this::reconciliationMakeBelieve, true),
				new FunctionTimer(RECONCILIATIONS_TIMER, "19:00", this::reconciliationsTimer, true));
	}

	public interface Func {
		void func(String n);
	}

	public class FunctionTimer {
		private Func func; // 执行任务的方法
		private String funcName;// 执行任务的方法字符串
		private String time;// 执行任务的时间
		private boolean createThread;// 执行任务的时候，是否起线程
		public FunctionTimer(String funcName, String time, Func func, boolean createThread) {
			this.func = func;
			this.funcName = funcName;
			this.time = time;
			this.setCreateThread(createThread);
		}

		public Func getFunc() {
			return func;
		}

		public void setFunc(Func func) {
			this.func = func;
		}

		public String getFuncName() {
			return funcName;
		}

		public void setFuncName(String funcName) {
			this.funcName = funcName;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public boolean isCreateThread() {
			return createThread;
		}

		public void setCreateThread(boolean createThread) {
			this.createThread = createThread;
		}

	}

	/**
	 * 五分钟一次定时任务总入口
	 * @author xuhongyu
	 * @param request
	 * @param runSource
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fiveMiniteTask", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult fiveMiniteTask(HttpServletRequest request, @RequestParam(required = false) String runSource)
			throws Exception {
		/*if (basicConfig.isProdEnvironment() && !ToolUtil.checkIPValid(request)) {
			log.error("非法IP调用TimerController minite5Task");
			return JsonResult.fail(JsonResult.FAIL, "非法IP调用TimerController minite5Task");
		}*/
		// 在新线程中调用耗时操作
		new Thread() {
			public void run() {
				//回盘查询
				processPay();
			}
		}.start();
		/*new Thread() {
			public void run() {
				//自动成交
				autoMakeDeal();
			}
		}.start();*/
		return JsonResult.ok();
	}

	/**
	 * 一小时一次或整点定时任务总入口
	 * @author xuhongyu
	 * @param request
	 * @param runSource
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hourTask", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult hourTask(HttpServletRequest request, @RequestParam(required = false) String runSource,
			@RequestParam(required = false) String param) throws Exception {
		/*if (basicConfig.isProdEnvironment() && !ToolUtil.checkIPValid(request)) {
			log.error("非法IP调用TimerController hourTask");
			return JsonResult.fail(JsonResult.FAIL, "非法IP调用TimerController hourTask");
		}*/
		// 在新线程中调用耗时操作
		new Thread() {
			public void run() {
				String param1 = param;
				List<FunctionTimer> listfunc = getlistFunc();
				try {
					if (StringUtils.isEmpty(runSource)) {
						Date now = new Date();
						for (FunctionTimer func : listfunc) {
							exeMethod(now, param1, func);
						}
					} else {
						switch (runSource) {
						case UPDATE_CAPITAL:
//							updateCapital(param1);
							break;
						case UPDATE_STATIS:
//							updateStatis(param1);
							break;
						case RECONCILIATION_MAKE_BELIEVE:
							reconciliationMakeBelieve(param1);
							break;
						case RECONCILIATIONS_TIMER:
							reconciliationsTimer(param1);
							break;
						default:
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		return JsonResult.ok();
	}

	private void exeMethod(Date now, String param, FunctionTimer sf) {
		try{
			String day = DateUtil.format(now, "yyyyMMdd") + ":";
			String minute = DateUtil.format(now, "HH:mm");
			String configStr = redisService.getString(Const.REDIS_PREFIX.REDIS_TIMER + "config:" + sf.getFuncName());
			if(StringUtils.isEmpty(configStr)){
				redisService.set(Const.REDIS_PREFIX.REDIS_TIMER + "config:" + sf.getFuncName(), "1");
			}else{
				if(Integer.parseInt(configStr)==0){
					return;
				}
			}
			if (!redisService.checkKeyExisted(Const.REDIS_PREFIX.REDIS_TIMER + day + sf.getFuncName())
					&& minute.equals(sf.getTime())) {
				redisService.set(Const.REDIS_PREFIX.REDIS_TIMER + day + sf.getFuncName(), "1", DateUtil.getMiao());
				if(sf.isCreateThread()){
					// 在新线程中调用耗时操作
					new Thread() {
						public void run() {
							sf.getFunc().func(param);
						}
					}.start();
				}else{
					sf.getFunc().func(param);
				}
			}
		}catch(Exception e){
			log.error("TimerController exeMethod", e);
		}

	}

	/**
	 * 定时处理所有充值和提现的回盘(测开发测试准生产环境一分钟一次，生产环境五分钟一次)
	 * @param request
	 * @return
	 * @throws Exception
	 * @author xuhongyu
	 */
	private void processPay() {
		int id = timerService.addTaskLog("processPay");
		try {
			// 获取一分钟以前的待处理订单 t_trade（未回盘的）
			List<Trade> list = payProcessService.getOrderToDealwith();
			if (!basicConfig.isPayByService()) {
				for (Trade order : list) {
					try {
						// 不真的走支付通道的话，则直接回盘成功（订单状态|0:处理中, 1:成功, 2:失败, 3:过期, 4:关闭, 5:待商户确认 9:其他）
						payProcessService.updateReceiveBank(order.getId(), 1, "");
					} catch (Exception e) {
						log.error("TimerController processPay 模拟回盘：" + order.getId(), e);
					}
				}
			} else {
				for (Trade order : list) {
					try {
						receiveTradeByPayChannel(order);
					} catch (Exception e) {
						log.error("TimerController processPay 真实回盘：" + order.getId(), e);
					}
				}
			}
		} catch (Exception e) {
			timerService.updateTaskLog("processPay", id, (byte) 0, e);
		}
		timerService.updateTaskLog("processPay", id, (byte) 1, null);
	}
	
	/**
	 * 根据支付通道查询结果，处理回盘成功or失败
	 * @author xuhongyu
	 * @param order
	 * @throws Exception
	 */
	private void receiveTradeByPayChannel(Trade order) throws Exception {
        PayServiceVo payServiceVo = new PayServiceVo(payCenterConfigBean.getApiKey(),payCenterConfigBean.getApiSecret(),payCenterConfigBean.getMerchantSign());
        payServiceVo.setPayChannelType(order.getTradeType());
        payServiceVo.setOrderNo(order.getId());
        payServiceVo.setCashFlowType((byte) (order.getWithdrawType() ? 2 : 1));
        JsonResult<ReturnOrderQueryVo> result = HttpClient.doPostWrapResult(payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayOrderQuery(),
                payServiceVo.toCollectionApplyJSONString(), ReturnOrderQueryVo.class);
        if (result.getStatus() == JsonResult.SUCCESS) {
            if (result.getData() != null && result.getData().getRespCode() == JsonResult.SUCCESS) {
                //查询回盘：订单状态|0:处理中, 1:成功, 2:失败, 3:过期, 4:关闭, 5:待商户确认 9:其他
                int orderStatus = result.getData().getOrderStatus();
                log.info("===============订单："+order.getId()+"，查询回盘状态：" + orderStatus + "，msg" + result.getData().getRespMsg()+ "==================");
                if (orderStatus != 0 && orderStatus != 5 && orderStatus != 9) {
                	payProcessService.updateReceiveBank(order.getId(), orderStatus, result.getData().getRespMsg());
                }
            }
        }
    }
	
	/**
	 * 每天凌晨0点更新垫资额度：插入今天的垫资汇总，计算昨天的垫资统计
	 *
	 * @param request
	 * @return
	 * @author xuhongyu
	 */
	/*private void updateCapital(String param) {
		int id = timerService.addTaskLog("updateCapital");
		try {
			timerService.updateCapital();
		} catch (Exception e) {
			timerService.updateTaskLog("updateCapital", id, (byte) 0, e);
		}
		timerService.updateTaskLog("updateCapital", id, (byte) 1, null);
	}*/

	/**
	 * 每天凌晨0点更新垫资额度：插入今天的垫资汇总，计算昨天的垫资统计
	 * @param request
	 * @return
	 * @author xuhongyu
	 */
	/*private void updateStatis(String param) {
		int id = timerService.addTaskLog("updateStatis");
		try {
			timerService.updateSystemStatis();
		} catch (Exception e) {
			timerService.updateTaskLog("updateStatis", id, (byte) 0, e);
		}
		timerService.updateTaskLog("updateStatis", id, (byte) 1, null);
	}*/

	/**
	 * 每天中午12点执行假对账： 1，假装上一个工作日的订单金额全部回款，对上一个工作日凌晨开始到当天凌晨之间的所有支付成功订对账 2，更新可垫资总额
	 * 
	 * @param reconDate
	 *            做账日(格式yyyy-MM-dd)如果为空默认当天，如果不为空就是补那天的做账
	 * @return
	 * @throws Exception
	 * @author xuhongyu
	 */
	private void reconciliationMakeBelieve(String reconDate) {
		int id = timerService.addTaskLog("reconciliationMakeBelieve");
		try {
			if (StringUtils.isNotBlank(reconDate)) {
				Date curDate = DateUtil.format(reconDate, "yyyy-MM-dd");
				if (curDate == null) {
					log.error("reconDate参数格式错误，应为yyyy-MM-dd");
				}
			} else {
				reconDate = DateUtil.format(new Date(), "yyyy-MM-dd");// 对账日，精确到天
			}

//			String returnStr = "{\"respCode\":200,\"dates\":[\"2018-11-09\",\"2019-05-05\",\"2019-05-06\"]}";
			String returnStr = HttpClient.doGet(payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getReconciliationDate()
					+ "?reconciliationDate=" + reconDate);
			ReturnReconciliationDateVo reconciliationDateVo = JSONObject.parseObject(returnStr,
					ReturnReconciliationDateVo.class);
			// 返回200才有账单日，返回205表示当前对账日为法定节假日
			if (reconciliationDateVo != null && reconciliationDateVo.getRespCode() == JsonResult.SUCCESS) {
				List<String> dates = reconciliationDateVo.getDates();
				if (dates != null && dates.size() > 0) {
					List<Trade> allTradeNormalList = reconciliationService.getUnReconciliationTrades(reconDate,dates.size());
					for (Trade reconciliation : allTradeNormalList) {
						reconciliationService.updateReconBusinessProcess(reconciliation.getId(), true, false);
					}
				}
			}
			//TODO 更新所有代付通道的可垫资总额
//			timerService.updateCapitalAmt(reconDate);
		} catch (Exception e) {
			timerService.updateTaskLog("reconciliationMakeBelieve", id, (byte) 0, e);
		}
		timerService.updateTaskLog("reconciliationMakeBelieve", id, (byte) 1, null);
	}

	/**
	 * 每天18点执行真实对账： 1，记录所有支付通道在该对账日的所有账单信息
	 * 2，账单日是四天前的账单和报盘时间是四天前的订单进行对账并将对账异常记录保存起来 3，异常对账信息预警 4，更新可垫资总额
	 * 
	 * @param reconDate
	 *            做账日(格式yyyy-MM-dd)如果为空默认当天，如果不为空就是补那天的做账
	 * @param saveNewBill
	 *            是否下载保存对账单(如果是true，reconDate传订单报盘日的下1或2个工作日；如果是false，
	 *            reconDate传订单报盘日的4天后)，默认为true
	 * @param realReconciliate
	 *            是否真实对账，默认为true
	 * @throws Exception
	 * @author xuhongyu
	 */
	@SuppressWarnings("unchecked")
	private void reconciliationsTimer(String param) {
		int id = timerService.addTaskLog("reconciliationsTimer");
		try {
			String reconDateStr = null;
			Boolean saveNewBill = null;
			Boolean realReconciliate = null;
			if (StringUtils.isNotEmpty(param)) {
				JSONObject vo = JSONObject.parseObject(param);
				reconDateStr = vo.getString("reconDate");
				saveNewBill = vo.getBoolean("saveNewBill");
				realReconciliate = vo.getBoolean("realReconciliate");
			}

			if (StringUtils.isNotBlank(reconDateStr)) {
				Date curDate = DateUtil.format(reconDateStr, "yyyy-MM-dd");
				if (curDate == null) {
					log.error("reconDate参数格式错误，应为yyyy-MM-dd");
					return;
				}
			} else {
				reconDateStr = DateUtil.format(new Date(), "yyyy-MM-dd");// 对账日，精确到天
			}
			if (saveNewBill == null) {
				saveNewBill = true;
			}
			if (realReconciliate == null) {
				realReconciliate = true;
			}
			
			if(saveNewBill){
				//记录所有支付通道在该对账日的所有账单信息
				reconciliationService.updateDownloadAndSaveBill(reconDateStr);
			}
			if(realReconciliate){
				//对四天之前进行真实对账
				Date reconDate = DateUtil.format(reconDateStr,DateUtil.DATE_YYYY_MM_DD);//对账日(到日)
				Date fourDayAgo = DateUtil.getDate(reconDate, -4);//对账日四天前(到日)
				Date threeDayAgo = DateUtil.getDate(reconDate, -3);//对账日三天前(到日)
				//对账日四天前的订单和账单日是四天前的账单进行对比
				JSONObject realReconciliationInfo = reconciliationService.getRrealReconciliationInfo(reconDate,fourDayAgo, threeDayAgo);
				//所有异常订单
				List<TradeReconciliation> tradeAbnormalList = (List<TradeReconciliation>) realReconciliationInfo.get("tradeAbnormalList");
				//所有正常订单
				List<TradeReconciliation> tradeNormalList = (List<TradeReconciliation>) realReconciliationInfo.get("tradeNormalList");
				//所有我们还没回盘，对账文件就有了的订单
				List<TradeReconciliation> unRecvBankTradeList = (List<TradeReconciliation>) realReconciliationInfo.get("unRecvBankTradeList");
				
				List<Long> tradeReconciliationIdList = new ArrayList<Long>();//需要做tradeReconciliation表对账状态变更的交易id
				//我们还没回盘 对账单中就有了的订单
				for(TradeReconciliation reconciliation : unRecvBankTradeList){
					tradeReconciliationIdList.add(reconciliation.getTradeId());
					reconciliationService.updateReconBusinessProcess(reconciliation.getTradeId(),reconciliation.getResult(),true);
				}
				// 对账正常的allTradeNormalList
				for(TradeReconciliation reconciliation : tradeNormalList){
					tradeReconciliationIdList.add(reconciliation.getTradeId());
				}
				reconciliationService.updateHandlerAbnormalBatch(tradeReconciliationIdList,tradeAbnormalList,reconDate);
			}
			// 对账异常表预警
			reconciliationService.updateSendMsgByTradeAbnormal(reconDateStr);
			
			//TODO 更新所有代付通道的可垫资总额
//			timerService.updateCapitalAmt(reconDateStr);
			
			//历史对账异常的再次下载对账单
			reconciliationService.insertAbnormalDatesBillAgain();
		} catch (Exception e) {
			timerService.updateTaskLog("reconciliationsTimer", id, (byte) 0, e);
		}
		timerService.updateTaskLog("reconciliationsTimer", id, (byte) 1, null);
	}

	/**
	 * 每天凌晨 清除八小时前且三天内没有报盘的交易表记录
	 */
	// private void deleteUnTradeRecords(String param) {
	// int id = timerService.addTaskLog("deleteUnTradeRecords");
	// try {
	// timerService.deleteUnTradeRecords();
	// } catch (Exception e) {
	// timerService.updateTaskLog("updateFailLoanEcloudAgreementData", id,
	// (byte) 0, e);
	// }
	// timerService.updateTaskLog("updateFailLoanEcloudAgreementData", id,
	// (byte) 1, null);
	// }
	
	/**
	 * 清除过期的formId
	 * @author xuhongyu
	 * @param param
	 */
	private void removeExpiredFormid(String param){
		/*int id = timerService.addTaskLog("removeExpiredFormid");
		try {
			msgFormIdService.removeExpiredFormid();
		} catch (Exception e) {
			timerService.updateTaskLog("removeExpiredFormid", id, (byte) 0, e);
		}
		timerService.updateTaskLog("removeExpiredFormid", id, (byte) 1, null);*/
	}
}

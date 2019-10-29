package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.vo.DataGridResult;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PageVo;
import rrx.cnuo.cncommon.vo.feign.UserPassportVo;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;
import rrx.cnuo.service.accessory.consts.TradeConst;
import rrx.cnuo.service.dao.TradeBuycardMapper;
import rrx.cnuo.service.dao.TradeMapper;
import rrx.cnuo.service.dao.UserAccountListMapper;
import rrx.cnuo.service.feignclient.UserFeignService;
import rrx.cnuo.service.po.UserAccountList;
import rrx.cnuo.service.service.OrderInfoService;
import rrx.cnuo.service.vo.AccountListVo;
import rrx.cnuo.service.vo.WalletInfoVo;
import rrx.cnuo.service.vo.paycenter.PayServiceVo;
import rrx.cnuo.service.vo.paycenter.ReturnPayBase;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired private TradeMapper tradeMapper;
	@Autowired private UserAccountListMapper userAccountListMapper;
	@Autowired private UserFeignService userFeignService;
	@Autowired private PayCenterConfigBean payCenterConfigBean;
	@Autowired private TradeBuycardMapper tradeBuycardMapper;
	
	@Override
	public JsonResult<WalletInfoVo> getMyWalletInfo(PageVo pageVo) throws Exception {
		JsonResult<WalletInfoVo> result = new JsonResult<>();
        result.setStatus(JsonResult.SUCCESS);
        
        //从这里开始自动拦截分页
        PageHelper.startPage(pageVo.getPageNum(), pageVo.getPageSize());
        
		UserAccountList userAccountListParam = new UserAccountList();
		userAccountListParam.setUid(UserContextHolder.currentUser().getUserId());
		userAccountListParam.setValidStatus(true);
		List<UserAccountList> list = userAccountListMapper.getUserAccountListByParam(userAccountListParam);
		
		List<AccountListVo> rowsList = new ArrayList<>();
		AccountListVo accountListVo = null;
		UserPassportVo fUser = null;
		Map<Long,UserPassportVo> userPassportMap = new HashMap<Long, UserPassportVo>();
        for (UserAccountList userAccountList : list) {
        	accountListVo = new AccountListVo();
        	accountListVo.setAcountTypeStr(TradeConst.AccountType.getMsgByCode(userAccountList.getAcountType()));
        	accountListVo.setAmount(userAccountList.getAmount());
        	if(userPassportMap.containsKey(userAccountList.getFuid())) {
        		fUser = userPassportMap.get(userAccountList.getFuid());
        	}else {
        		fUser = userFeignService.getUserPassportByUid(userAccountList.getFuid());
        		userPassportMap.put(userAccountList.getFuid(), fUser);
        	}
        	if(fUser != null){
        		accountListVo.setPartnerName(fUser.getNickName());
        	}
        	accountListVo.setCreateTime(DateUtil.format(userAccountList.getCreateTime()));
        	rowsList.add(accountListVo);
        }
        
        DataGridResult<UserAccountList> realResult = new DataGridResult<>(list);
        long total = realResult.getTotal();
        
        WalletInfoVo walletInfoVo = new WalletInfoVo(rowsList);
        walletInfoVo.setTotal(total);
        JSONObject userAccountJson = userFeignService.getUserAccountByUid(UserContextHolder.currentUser().getUserId());
        walletInfoVo.setBalance(userAccountJson.getInteger("balance"));
        
        result.setData(walletInfoVo);
        return result;
	}
	
	@Override
	public boolean addAccountBalanceInfo(Long userId) throws Exception {
		boolean flag = false;
		JsonResult<ReturnPayBase> result = new JsonResult<ReturnPayBase>();
		String url = payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayAddAccountBalanceInfo();
		PayServiceVo payServiceVo = new PayServiceVo(payCenterConfigBean.getApiKey(),payCenterConfigBean.getApiSecret(),payCenterConfigBean.getMerchantSign());
		payServiceVo.setUid(userId);
		result = HttpClient.doPostWrapResult(url, payServiceVo.toCollectionApplyJSONString(), ReturnPayBase.class);
		if (result != null && result.getStatus() == JsonResult.SUCCESS) {
			if (result.getData().getRespCode() == JsonResult.SUCCESS) {
				flag = true;
			}
		}
		return flag;
	}
	
	@Override
	public Integer getBalanceByUid(Long uid) throws Exception {
		int receivedAmt = userAccountListMapper.getReceivedAmt(uid);
		int unReceivedToSysAmt = userAccountListMapper.getUnReceivedToSysAmt(uid);
		return receivedAmt + unReceivedToSysAmt;
	}

	@Override
	public Integer getWithdrawAmtByUid(Long uid) throws Exception {
		int reconciledAmt = userAccountListMapper.getReconciledAmt(uid);
		int unReconciledToSysAmt = userAccountListMapper.getUnReconciledToSysAmt(uid);
		int balancePaySuccUnReconciledAmt = tradeMapper.getBalancePaySuccUnReconciledAmt(uid);
		return reconciledAmt + unReconciledToSysAmt - balancePaySuccUnReconciledAmt;
	}

	@Override
	public int getBuycardCnt(Long uid) {
		return tradeBuycardMapper.getBuycardCnt(uid);
	}
}

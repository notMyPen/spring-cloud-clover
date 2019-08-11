package rrx.cnuo.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.service.po.UserAccountList;

public interface UserAccountListMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserAccountList record);

    UserAccountList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAccountList record);

    List<UserAccountList> getUserAccountListByParam(UserAccountList record);
    
    long getAccountListCntByParam(UserAccountList record);

    /**
     * 获取某订单下某人的金额做账情况
     * @author xuhongyu
     * @param userAccountList
     * @return
     */
    List<JSONObject> getAccountListAmtByTradeIdAndUid(UserAccountList userAccountList);
	
	/**
     * 获取在tradeId条件下的以uid分组的每个用户该订单下的资金情况
     * @author xuhongyu
     * @param tradeId
     * @return
     */
	List<JSONObject> assembleAccountZoneByTradeId(Long tradeId);
	
	List<JSONObject> assembleTempAccountZoneByTradeId(Long tradeId);
	
	/**
     * 获取用户已回盘的所有支付明细之和
     * @author xuhongyu
     */
    Integer getReceivedAmt(@Param("userId") Long userId);
    
    /**
     * 获取用户未回盘但支付给系统的手续费明细之和
     * @author xuhongyu
     * @param userId
     * @return
     */
    Integer getUnReceivedToSysAmt(@Param("userId") Long userId);

    /**
     * 获取用户已对过账的所有支付明细之和
     * @author xuhongyu
     * @param userId
     * @return
     */
    Integer getReconciledAmt(@Param("userId") Long userId);
    
    /**
     * 获取用户未对过账但支付给系统的手续费明细之和
     * @author xuhongyu
     * @param userId
     * @return
     */
    Integer getUnReconciledToSysAmt(@Param("userId") Long userId);
    
    /**
	 * 根据订单id获取非系统用户非银行用户的各个用户账单总金额
	 * @author xuhongyu
	 * @param tradeId
	 * @return
	 */
	List<JSONObject> getAccountListAmtNotSysAndBankByTradeId(Long tradeId);
	
	/**
	 * 获取因为此次交易除系统、银行以及支付人以外的相关用户账户变动情况
	 * @author xuhongyu
	 * @param userAccountList
	 * @return
	 */
	List<JSONObject> getAccountListAmtNotSysBankAndUidByTradeId(UserAccountList userAccountList);
	
	void insertTemp(UserAccountList record);
	
	/**
	 * 根据交易id删除临时做账表数据
	 * @author xuhongyu
	 * @param orderId
	 */
	void deleteTempUserAccountList(Long tradeId);
}
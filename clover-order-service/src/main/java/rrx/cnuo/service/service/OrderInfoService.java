package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PageVo;
import rrx.cnuo.service.vo.WalletInfoVo;

/**
 * 用户订单/交易相关信息
 * @author Administrator
 *
 */
public interface OrderInfoService {

	/**
	 * 获取我的钱包信息
	 * @author xuhongyu
	 * @param pageVo
	 * @return
	 * @throws Exception
	 */
	public JsonResult<WalletInfoVo> getMyWalletInfo(PageVo pageVo) throws Exception;
	
	/**
	 * 开通支付中心账户
	 * @author xuhongyu
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean addAccountBalanceInfo(Long userId) throws Exception;
	
	/**
	 * 获取某人的余额
	 * @author xuhongyu
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	Integer getBalanceByUid(Long uid) throws Exception;
	
	/**
	 * 获取某人的可提现余额
	 * @author xuhongyu
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	Integer getWithdrawAmtByUid(Long uid) throws Exception;

	/**
	 * 计算某用户一共购买的礼券个数
	 * @author xuhongyu
	 * @param uid
	 * @return
	 */
	public int getBuycardCnt(Long uid);
}

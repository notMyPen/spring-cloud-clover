package rrx.cnuo.service.vo;

import java.util.List;

import rrx.cnuo.cncommon.vo.DataGridResult;

/**
 * 用户钱包信息Vo
 * @author xuhongyu
 * @date 2019年4月1日
 */
public class WalletInfoVo extends DataGridResult<AccountListVo>{

	public WalletInfoVo() {
		super();
	}

	public WalletInfoVo(List<AccountListVo> rowsList) {
		super(rowsList);
	}

	/**
	 * 余额
	 */
	private Integer balance;

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
}

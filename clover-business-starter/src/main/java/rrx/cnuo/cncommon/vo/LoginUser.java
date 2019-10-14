package rrx.cnuo.cncommon.vo;


import java.io.Serializable;
import java.util.List;

public class LoginUser implements Serializable {

	private static final long serialVersionUID = -4083327605430665846L;

	public final static String CONTEXT_KEY_USERID = "x-user-id";

	private Long userId;
	
	private String miniOpenId;
	
//	private String openId;
//	private String telephone;
	
	private String currentServiceId;
	
	private List<String> allowPermissionService;


	public List<String> getAllowPermissionService() {
		return allowPermissionService;
	}

	public void setAllowPermissionService(List<String> allowPermissionService) {
		this.allowPermissionService = allowPermissionService;
	}

	public String getMiniOpenId() {
		return miniOpenId;
	}

	public void setMiniOpenId(String miniOpenId) {
		this.miniOpenId = miniOpenId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrentServiceId() {
		return currentServiceId;
	}

	public void setCurrentServiceId(String currentServiceId) {
		this.currentServiceId = currentServiceId;
	}
	
}

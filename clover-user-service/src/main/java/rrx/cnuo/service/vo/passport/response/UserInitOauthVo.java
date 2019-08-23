package rrx.cnuo.service.vo.passport.response;

import lombok.Data;

@Data
public class UserInitOauthVo{

	private Long uid;
	private String miniOpenId;
	private String ticket;
	private Integer expireTime;

}

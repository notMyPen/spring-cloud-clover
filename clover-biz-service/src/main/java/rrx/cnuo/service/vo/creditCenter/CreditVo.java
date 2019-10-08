package rrx.cnuo.service.vo.creditCenter;

import lombok.Data;

/**
 * 用户各项认证状态（状态分类：0.未认证 1.待完善 2.认证中 3.认证成功 4.认证失败 5.已过期）
 * @author xuhongyu
 * @date 2019年5月10日
 */
@Data
public class CreditVo {
	private Integer  type;//认证类型
	private Byte creditStatus; //认证状态
	private Integer creditTime; //认证时间
    private String userId;
    private Integer code;
    private Short zhimaScore;
    private Byte student;//是否在校大学生，1：是， 0：否， -1：未知（用户没填学信）
    private String card_no; //身份证号
    private String  user_name; //用户名
    private Integer  baseStatus; //基础信息状态
    private Integer  faceStatus; //人脸认证状态
    private Integer  mobileStatus; //运营商认证状态
    private String telephone; //手机号
    
}

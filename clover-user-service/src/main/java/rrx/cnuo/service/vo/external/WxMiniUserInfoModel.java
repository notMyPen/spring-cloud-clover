package rrx.cnuo.service.vo.external;

import java.util.List;

import lombok.Data;

/**
 * 用户的微信小程序信息
 * @author xuhongyu
 * @date 2019年8月23日
 */
@Data
public class WxMiniUserInfoModel {

	private String unionId;
    private String openId;
    private String nickName;
    private Integer gender;
    private String province;
    private String city;
    private String country;
    private String avatarUrl;
    private List<String> privilege;     //用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）

    private String errcode;
    private String errmsg;
}

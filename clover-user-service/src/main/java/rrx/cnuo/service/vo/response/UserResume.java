package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户履历")
public class UserResume {

	@ApiModelProperty(value = "学历：0-未知、1-博士、2-硕士、3-本科、4-大专、5高中及以下")
	private Byte academic;

	@ApiModelProperty(value = "毕业院校名称")
    private String college;

	@ApiModelProperty(value = "学校类型：0-未知、1-985，2-211，3-985且211、4-一般全日制大学，5-海外院校")
    private Byte schoolType;
    
	@ApiModelProperty(value = "所在单位名称")
    private String companyName;

	@ApiModelProperty(value = "所在单位类型：0-未知、1-央企、2-国企、3-事业单位、4-私企、5-外企、6-其他")
    private Byte companyType;

	@ApiModelProperty(value = "所在行业")
    private String industryType;

	@ApiModelProperty(value = "所处职级：0-未知、1-企业主或单位负责人、2-高层管理、3-中层管理、4-普通职员、5-其他")
    private Byte rankType;
}

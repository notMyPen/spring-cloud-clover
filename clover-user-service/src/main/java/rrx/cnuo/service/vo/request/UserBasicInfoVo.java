package rrx.cnuo.service.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户基本信息vo")
public class UserBasicInfoVo {

//    private String wxAccount;

	@ApiModelProperty(value = "生日(yyyy-MM-dd格式的字符串)")
    private String birthday;

    @ApiModelProperty(value = "身高(单位cm)")
    private Short height;

    @ApiModelProperty(value = "身材（字符串类型的数组）")
    private String figureList;

    @ApiModelProperty(value = "故乡省份id")
    private Integer homeProvinceId;

    @ApiModelProperty(value = "故乡地级市id")
    private Integer homeCityId;

    @ApiModelProperty(value = "当前工作生活地省份id")
    private Integer nowProvinceId;

    @ApiModelProperty(value = "当前工作生活地级市id")
    private Integer nowCityId;

    @ApiModelProperty(value = "婚姻状态：0-未知；1-未婚；2-离异；3-丧偶")
    private Byte maritalStatus;

    @ApiModelProperty(value = "有无子女：0-未知；1-没有孩子、2-有孩子且住在一起、3-有孩子偶尔一起住、4-有孩子但不在身边")
    private Byte haveChildren;

    @ApiModelProperty(value = "性格（字符串类型的数组）")
    private String characterList;

    @ApiModelProperty(value = "学历：0-未知、1-博士、2-硕士、3-本科、4-大专、5高中及以下")
    private Byte academic;

    @ApiModelProperty(value = "毕业院校id")
    private Integer collegeId;

    @ApiModelProperty(value = "年收入：0-未知；1-10W以下、2-10W-20W、3-20W-30W、4-30W-50W、5-50W-100W、6-100W以上")
    private Byte yearIncome;

    @ApiModelProperty(value = "住房情况：0-空；1-和家人同住、2-已购房有贷款、3-已购房无贷款、4-租房、5-打算婚后买房、6-住单位宿舍")
    private Byte houseStatus;

    @ApiModelProperty(value = "购车情况：0-空；1 - 已购车;2 - 未购车;")
    private Byte carStatus;

    @ApiModelProperty(value = "资产水平：0-未知、1-所在城市基本生活水平、2-所在城市小康生活水平、3-所在城市家境优渥")
    private Byte assetLevel;
}
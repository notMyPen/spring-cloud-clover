package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("地级市或区信息")
public class CityVo {

	@ApiModelProperty("地级市或区id")
	private Integer id;

	@ApiModelProperty("国家代码")
    private String country;

	@ApiModelProperty("省或直辖市id")
    private Integer provinceId;

	@ApiModelProperty("省或直辖市名称")
    private String province;

	@ApiModelProperty("地级市或区名称")
    private String city;
}

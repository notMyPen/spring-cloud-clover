package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("省或直辖市信息")
public class ProvinceVo {

	@ApiModelProperty("省或直辖市Id")
	private Integer id;

	@ApiModelProperty("省或直辖市名称")
    private String name;
}

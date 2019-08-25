package rrx.cnuo.cncommon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "分页参数")
@Data
public class PageVo {

	@ApiModelProperty(value = "当前页数，从1开始",required = true)
    private Integer pageNum = 1;

	@ApiModelProperty(value = "每页数据条数",required = true)
    private Integer pageSize = 10;
}

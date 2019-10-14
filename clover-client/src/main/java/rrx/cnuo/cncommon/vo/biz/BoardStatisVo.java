package rrx.cnuo.cncommon.vo.biz;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户桃花牌相关统计Vo")
public class BoardStatisVo implements Serializable{

	private static final long serialVersionUID = 4449962086020119610L;

//	private Long uid;

	@ApiModelProperty("比心/喜欢了多少人")
    private Integer likeCnt;

	@ApiModelProperty("被多少人比心/喜欢")
    private Integer likedCnt;

	@ApiModelProperty("翻了多少人的牌子")
    private Integer turnCnt;

	@ApiModelProperty("被多少人翻了牌子")
    private Integer turnedCnt;

	@ApiModelProperty("浏览过多少人")
    private Integer viewCnt;

	@ApiModelProperty("被多少人浏览过")
    private Integer viewedCnt;
}

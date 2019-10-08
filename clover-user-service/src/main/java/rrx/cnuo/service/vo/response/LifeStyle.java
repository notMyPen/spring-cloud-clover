package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户的生活方式")
public class LifeStyle {

	@ApiModelProperty(value = "吸烟喝酒状况：0-未知、1-不吸烟不饮酒、2-不吸烟饮酒、3-吸烟不饮酒、4-烟酒不离手")
	private Byte smokDrink;

	@ApiModelProperty(value = "对家务活的态度：0-未知、1-家务小能手、2-分工合作、3-不太会但愿意为了对方学习、4-对方能承包家务就太好了")
    private Byte housework;
}

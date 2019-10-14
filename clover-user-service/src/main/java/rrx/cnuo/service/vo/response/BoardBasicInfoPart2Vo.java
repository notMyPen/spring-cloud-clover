package rrx.cnuo.service.vo.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rrx.cnuo.service.accessory.consts.UserConst;

@ApiModel("用户基本信息")
public class BoardBasicInfoPart2Vo {

	@ApiModelProperty("属相：0-未知；按顺序1-12分别表示鼠、牛、虎、兔、龙、蛇、马、羊、猴、鸡、狗、猪")
	private String zodiacShow;
	
	@JsonIgnore
	private Byte zodiac;
	
	@ApiModelProperty("身材(JsonArray转成的字符串)：0-一般、11-瘦长(男)、12-运动员型(男)、13-较胖(男)、14-体格魁梧(男)、15-壮实(男)、21-瘦长(女)、22-苗条(女)、23-高大美丽(女)、24-丰满(女)、25-富线条美(女)")
	private List<String> figureShowList = new ArrayList<String>();
	
	@JsonIgnore
	private String figureList;
	
	@ApiModelProperty("当前工作生活地名称(省-市)")
	private String nowProvinceCity;
	
	@ApiModelProperty("故乡地名称(省-市)，冗余存储便于显示")
	private String homeProvinceCity;
	
	@ApiModelProperty("婚姻状态：0-未知；1-未婚；2-离异；3-丧偶")
	private Byte maritalStatus;
	
	@ApiModelProperty("有无子女：0-未知；1-没有孩子、2-有孩子且住在一起、3-有孩子偶尔一起住、4-有孩子但不在身边")
	private Byte haveChildren;

	public String getNowProvinceCity() {
		return nowProvinceCity;
	}

	public void setNowProvinceCity(String nowProvinceCity) {
		this.nowProvinceCity = nowProvinceCity;
	}

	public String getHomeProvinceCity() {
		return homeProvinceCity;
	}

	public void setHomeProvinceCity(String homeProvinceCity) {
		this.homeProvinceCity = homeProvinceCity;
	}

	public Byte getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Byte maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Byte getHaveChildren() {
		return haveChildren;
	}

	public void setHaveChildren(Byte haveChildren) {
		this.haveChildren = haveChildren;
	}

	public String getZodiacShow() {
		return zodiacShow;
	}

	public void setZodiacShow(String zodiacShow) {
		this.zodiacShow = zodiacShow;
	}

	public Byte getZodiac() {
		return zodiac;
	}

	public void setZodiac(Byte zodiac) {
		this.zodiac = zodiac;
		if(zodiac != null) {
			this.zodiacShow = UserConst.Zodiac.getZodiac(zodiac).getMessage();
		}
	}

	public List<String> getFigureShowList() {
		return figureShowList;
	}

	public void setFigureShowList(List<String> figureShowList) {
		this.figureShowList = figureShowList;
	}

	public String getFigureList() {
		return figureList;
	}

	public void setFigureList(String figureList) {
		this.figureList = figureList;
		if(StringUtils.isNotBlank(figureList)) {
			JSONArray jsonArr = JSON.parseArray(figureList);
			for(int i=0;i<jsonArr.size();i++) {
				this.figureShowList.add(UserConst.Figure.getFigure(jsonArr.getByte(i)).getMessage());
			}
		}
	}
}
